package br.ufrn.imd.ecommerce.services;

import br.ufrn.imd.ecommerce.exception.EmailConflictException;
import br.ufrn.imd.ecommerce.models.CostumerUser;
import br.ufrn.imd.ecommerce.models.VendorUser;
import br.ufrn.imd.ecommerce.repositories.CostumerUserRepository;
import br.ufrn.imd.ecommerce.repositories.VendorUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SuppressWarnings("SameReturnValue")
@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final CostumerUserRepository costumerUserRepository;

    private final VendorUserRepository vendorUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<CostumerUser> costumerUser = costumerUserRepository.findByEmail(email);
        Optional<VendorUser> vendorUser = vendorUserRepository.findByEmail(email);

        if (costumerUser.isPresent())
            return costumerUser.get();

        if (vendorUser.isPresent())
            return vendorUser.get();

        String USER_NOT_FOUND_MSG = "User with email %s not found";
        throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email));

    }


    @Transactional
    public String signUpCustomer(CostumerUser costumerUser) {

        boolean userExists = this.existUser(costumerUser.getEmail());

        if (userExists)
            throw new EmailConflictException("Email already taken");

        String encodedPassword = bCryptPasswordEncoder.encode(costumerUser.getPassword());
        costumerUser.setPassword(encodedPassword);
        costumerUser.setEnabled(true);
        costumerUserRepository.save(costumerUser);

        return "Success";
    }

    @Transactional
    public String signUpVendor(VendorUser vendorUser){

        boolean userExists = this.existUser(vendorUser.getEmail());

        if (userExists)
            throw new EmailConflictException("Email already taken");

        String encodedPassword = bCryptPasswordEncoder.encode(vendorUser.getPassword());
        vendorUser.setPassword(encodedPassword);
        vendorUser.setEnabled(true);
        vendorUserRepository.save(vendorUser);

        return "Success";

    }

    private Boolean existUser(String email){
        return costumerUserRepository.findByEmail(email).isPresent() || vendorUserRepository.findByEmail(email).isPresent();
    }

    public void enableAppUser(String email) {
        CostumerUser costumerUser = costumerUserRepository.findByEmail(email).orElseThrow(() -> new IllegalStateException("Email don't exist"));
        costumerUser.setEnabled(true);
        costumerUserRepository.save(costumerUser);
    }
}
