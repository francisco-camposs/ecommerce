package br.ufrn.imd.ecommerce.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Home {

    @SuppressWarnings("SameReturnValue")
    @GetMapping
    public String home(){
        return "Hello user";
    }

}
