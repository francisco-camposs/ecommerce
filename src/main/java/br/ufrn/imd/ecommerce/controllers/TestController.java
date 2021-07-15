package br.ufrn.imd.ecommerce.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"/api/v1/test"})
@AllArgsConstructor
public class TestController {

    @GetMapping
    public String header(){
        return "";
    }
}
