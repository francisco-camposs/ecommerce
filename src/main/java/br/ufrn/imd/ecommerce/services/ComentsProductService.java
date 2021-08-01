package br.ufrn.imd.ecommerce.services;

import br.ufrn.imd.ecommerce.interfaces.ServiceInterface;
import br.ufrn.imd.ecommerce.models.ComentsProduct;
import br.ufrn.imd.ecommerce.repositories.ComentsProductRepository;
import br.ufrn.imd.ecommerce.repositories.ProductRepository;
import br.ufrn.imd.ecommerce.validators.CommentsProductValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
public class ComentsProductService implements ServiceInterface<ComentsProduct, ComentsProductRepository> {

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
        validateComments(entity);
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

    private void validateComments(ComentsProduct entity) {

        String errors = "";

        if(!CommentsProductValidator.isValidComment(entity.getComent())){
            errors = errors.concat(" Character limit reached ");
        }
        if(CommentsProductValidator.isBlankComment(entity.getComent())){
            errors = errors.concat(" Empty comment field ");
        }
        if(!checkProductIDIsValid(entity)){
            errors = errors.concat(" Product ID is invalid");
        }

        if ( !errors.isBlank() ){

        }
    }

}
