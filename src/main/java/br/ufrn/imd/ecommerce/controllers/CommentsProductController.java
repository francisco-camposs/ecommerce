package br.ufrn.imd.ecommerce.controllers;


import br.ufrn.imd.ecommerce.interfaces.ControllerSimpleInterface;
import br.ufrn.imd.ecommerce.models.CommentsProduct;
import br.ufrn.imd.ecommerce.repositories.CommentsProductRepository;
import br.ufrn.imd.ecommerce.services.CommentsProductServiceVendor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products/comments")
@AllArgsConstructor
public class CommentsProductController implements
        ControllerSimpleInterface<CommentsProduct, CommentsProductRepository, CommentsProductServiceVendor> {

    @Getter
    private final CommentsProductServiceVendor service;

    @GetMapping("/search/{productId}")
    List<CommentsProduct> getCommentsByProduct(@PathVariable Long productId){
        return this.getService().getRepository().getCommentsByProductId(productId);
    }

}
