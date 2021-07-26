package br.ufrn.imd.ecommerce.services;

import br.ufrn.imd.ecommerce.dtos.RegistrationRequest;
import br.ufrn.imd.ecommerce.models.AppUser;
import br.ufrn.imd.ecommerce.models.ConfirmationToken;
import br.ufrn.imd.ecommerce.repositories.AppUserRepository;
import br.ufrn.imd.ecommerce.validators.EmailValidator;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.ValidatorResources;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final ConfirmationTokenService confirmationTokenService;
    private final AppUserRepository appUserRepository;


    public String register(RegistrationRequest request) {

        String errors = "";

        if ( EmailValidator.isValidEmailAddress(request.getEmail()) )
            errors = errors.concat("Email is not valid. \n");

        if (appUserRepository.findByEmail(request.getEmail()).isPresent())
            errors = errors.concat("This email is already registered. \n");

        if (StringUtils.isAllBlank(request.getFirstName()))
            errors = errors.concat("The first name can't be empty. \n");

        if (StringUtils.isAllBlank(request.getLastName()))
            errors = errors.concat("The last name can't be empty. \n");

        if (StringUtils.isAllBlank(request.getPassword()))
            errors = errors.concat("The password can't be empty. \n");

        if (request.getPassword() != null && request.getPassword().length() >= 8 && request.getPassword().length() <= 25)
            errors = errors.concat("The password must be greater than 8 characters and less than 25 characters. \n");

        if (errors.length() > 0)
            throw new IllegalStateException(errors);

        return appUserService.signUpUser(new AppUser(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword()));
    }

    @Transactional
    public void confirmToken(String token) {

        ConfirmationToken confirmationToken = confirmationTokenService.getConfirmationToken(token).orElseThrow(() ->
            new IllegalStateException("Token not found")
        );

        if (confirmationToken.getConfirmedAt() != null)
            throw new IllegalStateException("Email already confirmed");

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now()))
            throw new IllegalStateException("Token already expired");

        confirmationTokenService.setConfirmedAt(confirmationToken);
        appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());
    }
}
