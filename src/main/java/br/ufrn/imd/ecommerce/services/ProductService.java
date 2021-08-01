package br.ufrn.imd.ecommerce.services;

import br.ufrn.imd.ecommerce.interfaces.ServiceInterface;
import br.ufrn.imd.ecommerce.models.Product;
import br.ufrn.imd.ecommerce.repositories.ProductRepository;
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
    public void prePost(Product entity) {

    }

    @Override
    public void prePut(Product entity) {

    }

}
