package br.ufrn.imd.ecommerce.services;

import br.ufrn.imd.ecommerce.interfaces.ServiceInterface;
import br.ufrn.imd.ecommerce.models.CommentsProduct;
import br.ufrn.imd.ecommerce.repositories.CommentsProductRepository;
import br.ufrn.imd.ecommerce.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentsProductService implements ServiceInterface<CommentsProduct, CommentsProductRepository> {

    @Getter
    private final CommentsProductRepository repository;

    @Getter
    private final AppUserService appUserService;

    @Getter
    private final ProductRepository productRepository;


    @Override
    public void posPost(CommentsProduct entity) {

    }

    @Override
    public void prePost(CommentsProduct entity) {
        if(entity == null){
            throw new IllegalStateException();
        }
        else
            if ( checkProductIDIsValid(entity) ){ }
            else
                throw new IllegalStateException();
    }

    @Override
    public void posPut(CommentsProduct entity) {

    }

    @Override
    public void prePut(CommentsProduct entity) {

    }

    public boolean checkProductIDIsValid(CommentsProduct entity){
        return getProductRepository().findById( entity.getProduct().getId()).isPresent();
    }

}
