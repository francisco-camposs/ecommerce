package br.ufrn.imd.ecommerce.services;

import br.ufrn.imd.ecommerce.dtos.RegistrationRequest;
import br.ufrn.imd.ecommerce.models.AppUser;
import br.ufrn.imd.ecommerce.models.ConfirmationToken;
import br.ufrn.imd.ecommerce.validators.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;


    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail)
            throw new IllegalStateException("Email not valid");

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
