package br.ufrn.imd.ecommerce.services;

import br.ufrn.imd.ecommerce.exception.InvalidInputException;
import br.ufrn.imd.ecommerce.interfaces.ServiceInterface;
import br.ufrn.imd.ecommerce.models.CommentsProduct;
import br.ufrn.imd.ecommerce.repositories.CommentsProductRepository;
import br.ufrn.imd.ecommerce.repositories.ProductRepository;
import br.ufrn.imd.ecommerce.validators.CommentsProductValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentsProductServiceVendor implements ServiceInterface<CommentsProduct, CommentsProductRepository> {

    @Getter
    private final CommentsProductRepository repository;

    @Getter
    private final AppUserService appUserService;

    @Getter
    private final ProductRepository productRepository;

    @Override
    public void prePost(CommentsProduct entity) {
        validateComments(entity);
    }

    @Override
    public void prePut(CommentsProduct entity) {
        validateComments(entity);
    }



    public boolean checkProductIDIsValid(CommentsProduct entity){
        return getProductRepository().findById( entity.getProduct().getId()).isPresent();
    }

    private void validateComments(CommentsProduct entity) {

        String errors = "";

        if(!CommentsProductValidator.isValidComment(entity.getComment()))
            errors = errors.concat(" Character limit reached ");

        if(CommentsProductValidator.isBlankComment(entity.getComment()))
            errors = errors.concat(" Empty comment field ");

        if(!checkProductIDIsValid(entity))
            errors = errors.concat(" Product ID is invalid");

        if ( !errors.isBlank() )
            throw new InvalidInputException(errors);
    }

}
