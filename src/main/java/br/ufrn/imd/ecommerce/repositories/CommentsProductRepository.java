package br.ufrn.imd.ecommerce.repositories;

import br.ufrn.imd.ecommerce.models.ComentsProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsProductRepository extends JpaRepository<ComentsProduct, Long> {

    @Query("select c from ComentsProduct c where c.product.id = ?1" )
    List<ComentsProduct> getComentsByProductId(Long productId);
}
