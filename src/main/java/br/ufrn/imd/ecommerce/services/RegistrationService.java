package br.ufrn.imd.ecommerce.services;

import br.ufrn.imd.ecommerce.dtos.RegistrationClientRequest;
import br.ufrn.imd.ecommerce.dtos.RegistrationVendorRequest;
import br.ufrn.imd.ecommerce.enums.UserRole;
import br.ufrn.imd.ecommerce.exception.ConfirmationTokenException;
import br.ufrn.imd.ecommerce.exception.InvalidInputException;
import br.ufrn.imd.ecommerce.exception.UnauthorizedAccessException;
import br.ufrn.imd.ecommerce.models.CostumerUser;
import br.ufrn.imd.ecommerce.models.ConfirmationToken;
import br.ufrn.imd.ecommerce.models.VendorUser;
import br.ufrn.imd.ecommerce.repositories.CostumerUserRepository;
import br.ufrn.imd.ecommerce.repositories.VendorUserRepository;
import br.ufrn.imd.ecommerce.validators.EmailValidator;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final ConfirmationTokenService confirmationTokenService;

    private final CostumerUserRepository costumerUserRepository;
    private final VendorUserRepository vendorUserRepository;

    @Transactional
    public void confirmToken(String token) {

        ConfirmationToken confirmationToken = confirmationTokenService.getConfirmationToken(token).orElseThrow(() ->
            new UnauthorizedAccessException("Token not found")
        );

        if (confirmationToken.getConfirmedAt() != null)
            throw new ConfirmationTokenException("Email already confirmed");

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now()))
            throw new UnauthorizedAccessException("Token already expired");

        confirmationTokenService.setConfirmedAt(confirmationToken);
        appUserService.enableAppUser(confirmationToken.getCostumerUser().getEmail());
    }

    public String registerCostumer(RegistrationClientRequest request) {

        this.validateCostumer(request);

        return appUserService.signUpCustomer(new CostumerUser(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                UserRole.COSTUMER));
    }

    public String registerVendor(RegistrationVendorRequest request) {

        this.validateVendor(request);

        return appUserService.signUpVendor(
                VendorUser.builder().userRole(UserRole.VENDOR)
                    .cnpj(request.getCnpj())
                    .email(request.getEmail())
                    .complement(request.getComplement())
                    .imgLink(request.getImgLink())
                    .number(request.getNumber())
                    .postalCode(request.getPostalCode())
                    .street(request.getStreet())
                    .password(request.getPassword())
                    .name(request.getName())
                    .build());

    }



    private void validateVendor(RegistrationVendorRequest vendorRequest) {

        String errors = "";

        if (vendorRequest.getEmail() == null)
            errors = errors.concat("Email is empty. \n");

        if ( !EmailValidator.isValidEmailAddress(vendorRequest.getEmail()))
            errors = errors.concat("Email is not valid. \n");

        errors = validatePassword(errors, vendorRequest.getPassword());

        if (StringUtils.isAllBlank(vendorRequest.getName()))
            errors = errors.concat("Name is not empty. \n");

        if (vendorRequest.getName() != null && vendorRequest.getName().length() > 256)
            errors = errors.concat("Name is too long. \n");

        if ( StringUtils.isAllBlank(vendorRequest.getImgLink()) )
            errors = errors.concat("Image link is empty. \n");

        if ( !UrlValidator.getInstance().isValid(vendorRequest.getImgLink()) )
            errors = errors.concat("Image link is invalid. \n");

        if (StringUtils.isAllBlank(vendorRequest.getCnpj()))
            errors = errors.concat("CNPJ is empty. \n");

        if (vendorRequest.getCnpj() != null && !StringUtils.isNumeric(vendorRequest.getCnpj()))
            errors = errors.concat("CNPJ is not a number. \n");

        if (vendorRequest.getCnpj() != null && vendorRequest.getCnpj().length() < 14)
            errors = errors.concat("CNPJ is too short. \n");

        if (vendorRequest.getCnpj() != null && vendorRequest.getCnpj().length() > 14)
            errors = errors.concat("CNPJ is too long. \n");

        if ( !errors.isBlank() )
            throw new InvalidInputException(errors);

    }

    public void validateCostumer(RegistrationClientRequest request) {

        String errors = "";

        if (request.getEmail() == null)
            errors = errors.concat("Email is empty. \n");

        if ( !EmailValidator.isValidEmailAddress(request.getEmail()) )
            errors = errors.concat("Email is not valid. \n");

        if (costumerUserRepository.findByEmail(request.getEmail()).isPresent())
            errors = errors.concat("This email is already registered. \n");

        if (StringUtils.isAllBlank(request.getFirstName()))
            errors = errors.concat("The first name can't be empty. \n");

        if (StringUtils.isAllBlank(request.getLastName()))
            errors = errors.concat("The last name can't be empty. \n");

        errors = validatePassword(errors, request.getPassword());

        if ( !errors.isBlank() )
            throw new InvalidInputException(errors);

    }

    private String validatePassword(String errors, String password) {
        if ( StringUtils.isAllBlank(password) )
            errors = errors.concat("The password can't be empty. \n");

        if (password != null && (password.length() < 8 || password.length() >= 25))
            errors = errors.concat("The password must be greater than 8 characters and less than 25 characters. \n");

        return errors;
    }

}
