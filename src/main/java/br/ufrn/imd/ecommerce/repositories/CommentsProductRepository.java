package br.ufrn.imd.ecommerce.repositories;

import br.ufrn.imd.ecommerce.models.CommentsProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsProductRepository extends JpaRepository<CommentsProduct, Long> {

    @Query("select c from CommentsProduct c where c.product.id = ?1" )
    List<CommentsProduct> getCommentsByProductId(Long productId);
}
