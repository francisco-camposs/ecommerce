package br.ufrn.imd.ecommerce.services;

import br.ufrn.imd.ecommerce.interfaces.ServiceInterface;
import br.ufrn.imd.ecommerce.models.ComentsProduct;
import br.ufrn.imd.ecommerce.repositories.ComentsProductRepository;
import br.ufrn.imd.ecommerce.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentsProductService implements ServiceInterface<ComentsProduct, ComentsProductRepository> {

    @Getter
    private final ComentsProductRepository repository;

    @Getter
    private final AppUserService appUserService;

    @Getter
    private final ProductRepository productRepository;


    @Override
    public void posPost(ComentsProduct entity) {

    }

    @Override
    public void prePost(ComentsProduct entity) {
        if(entity == null){
            throw new IllegalStateException();
        }
        else
            if ( checkProductIDIsValid(entity) ){ }
            else
                throw new IllegalStateException();
    }

    @Override
    public void posPut(ComentsProduct entity) {

    }

    @Override
    public void prePut(ComentsProduct entity) {

    }

    public boolean checkProductIDIsValid(ComentsProduct entity){
        return getProductRepository().findById( entity.getProduct().getId()).isPresent();
    }

}
