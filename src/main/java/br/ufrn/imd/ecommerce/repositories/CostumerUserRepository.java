package br.ufrn.imd.ecommerce.repositories;

import br.ufrn.imd.ecommerce.models.CostumerUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CostumerUserRepository extends JpaRepository<CostumerUser, Long> {

    Optional<CostumerUser> findByEmail(String email);

}
