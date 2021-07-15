package br.ufrn.imd.ecommerce.controllers;

import br.ufrn.imd.ecommerce.interfaces.ControllerInterface;
import br.ufrn.imd.ecommerce.models.Product;
import br.ufrn.imd.ecommerce.repositories.ProductRepository;
import br.ufrn.imd.ecommerce.services.ProductService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController implements ControllerInterface<Product, ProductRepository, ProductService> {
    @Getter
    private final ProductService service;

}
