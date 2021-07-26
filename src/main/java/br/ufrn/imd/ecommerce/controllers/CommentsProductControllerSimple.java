package br.ufrn.imd.ecommerce.controllers;


import br.ufrn.imd.ecommerce.interfaces.ControllerSimpleInterface;
import br.ufrn.imd.ecommerce.models.ComentsProduct;
import br.ufrn.imd.ecommerce.repositories.ComentsProductRepository;
import br.ufrn.imd.ecommerce.services.ComentsProductService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products/coments")
@AllArgsConstructor
public class CommentsProductControllerSimple implements ControllerSimpleInterface<ComentsProduct, ComentsProductRepository
        , ComentsProductService> {

    @Getter
    private final ComentsProductService service;

    @GetMapping("/search/{productId}")
    List<ComentsProduct> getComentsByProduct(@PathVariable Long productId){
        return this.getService().getRepository().getComentsByProductId(productId);
    }
}
