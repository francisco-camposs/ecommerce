package br.ufrn.imd.ecommerce.services;

import br.ufrn.imd.ecommerce.dtos.RegistrationRequest;
import br.ufrn.imd.ecommerce.enums.UserRole;
import br.ufrn.imd.ecommerce.models.AppUser;
import br.ufrn.imd.ecommerce.models.ConfirmationToken;
import br.ufrn.imd.ecommerce.validators.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;

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

    public String registerCostumer(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail)
            throw new IllegalStateException("Email not valid");

        AppUser appUser = AppUser.builder()
                                    .appUserRole(UserRole.COSTUMER)
                                    .firstName(request.getFirstName())
                                    .lastName(request.getLastName())
                                    .email(request.getEmail())
                                    .password(request.getPassword())
                                    .build();

        return appUserService.signUpUser(appUser);
    }

    public String registerVendor(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail)
            throw new IllegalStateException("Email not valid");

        AppUser appUser = AppUser.builder()
                                    .appUserRole(UserRole.VENDOR)
                                    .firstName(request.getFirstName())
                                    .lastName(request.getLastName())
                                    .email(request.getEmail())
                                    .password(request.getPassword())
                                    .build();

        return appUserService.signUpUser(appUser);
    }

}
