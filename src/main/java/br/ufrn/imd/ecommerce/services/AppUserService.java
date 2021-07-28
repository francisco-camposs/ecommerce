package br.ufrn.imd.ecommerce.services;

import br.ufrn.imd.ecommerce.models.CostumerUser;
import br.ufrn.imd.ecommerce.models.ConfirmationToken;
import br.ufrn.imd.ecommerce.repositories.CostumerUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final String USER_NOT_FOUND_MSG = "User with email %s not found";

    private final CostumerUserRepository costumerUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return costumerUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email))
                );
    }


    @Transactional
    public String signUpUser(CostumerUser costumerUser) {

        boolean userExists = costumerUserRepository.findByEmail(costumerUser.getEmail()).isPresent();

        if (userExists)
            throw new IllegalStateException("Email already taken");

        String encodedPassword = bCryptPasswordEncoder.encode(costumerUser.getPassword());
        costumerUser.setPassword(encodedPassword);
        costumerUser.setEnabled(true);
        costumerUserRepository.save(costumerUser);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                costumerUser
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    public void enableAppUser(String email) {
        CostumerUser costumerUser = costumerUserRepository.findByEmail(email).orElseThrow(() -> new IllegalStateException("Email don't exist"));
        costumerUser.setEnabled(true);
        costumerUserRepository.save(costumerUser);
    }
}
