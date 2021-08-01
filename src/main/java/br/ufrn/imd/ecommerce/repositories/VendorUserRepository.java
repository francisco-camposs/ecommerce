package br.ufrn.imd.ecommerce.repositories;

import br.ufrn.imd.ecommerce.models.VendorUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorUserRepository extends JpaRepository<VendorUser, Long> {

    Optional<VendorUser> findByEmail(String email);

}
