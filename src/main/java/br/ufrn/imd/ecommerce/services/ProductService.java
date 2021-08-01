package br.ufrn.imd.ecommerce.services;

import br.ufrn.imd.ecommerce.interfaces.ServiceInterface;
import br.ufrn.imd.ecommerce.models.Product;
import br.ufrn.imd.ecommerce.repositories.ProductRepository;
import br.ufrn.imd.ecommerce.validators.ProductValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService implements ServiceInterface<Product, ProductRepository> {

    @Getter
    private final ProductRepository repository;

    @Getter
    private final AppUserService appUserService;

    @Override
    public void posPost(Product entity) {
    }

    @Override
    public void prePost(Product entity) {
        validateProduct(entity);
    }

    @Override
    public void posPut(Product entity) {
    }
    @Override
    public void prePut(Product entity) {
    }

    private void validateProduct(Product entity) {

        String errors = "";

        if(!ProductValidator.isValidName(entity.getName())){
            errors = errors.concat(" Invalid name, Character limit reached or product name shouldn't just numbers  ");
        }

        if(!ProductValidator.isValidImgLink(entity.getImgLink())){
            errors = errors.concat(" Invalid Link, Character limit reached ");
        }

        if(!ProductValidator.isValidDescription(entity.getDescription())){
            errors = errors.concat(" Invalid description, Character limit reached or description shouldn't just numbers ");
        }
        if(!ProductValidator.isValidPrice(entity.getPrice())){
            errors = errors.concat(" Invalid price, Number of price is zero or negative ");
        }

        if ( !errors.isBlank() ){

        }


    }
}
