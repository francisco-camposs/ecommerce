package br.ufrn.imd.ecommerce.controllers;

import br.ufrn.imd.ecommerce.dtos.RegistrationRequest;
import br.ufrn.imd.ecommerce.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = {"/api/v1/registration"})
@AllArgsConstructor
public class RegistrationController {

    @Autowired
    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }

    @GetMapping
    public void confirm(@RequestParam("toke") String token ){
        registrationService.confirmToken(token);
    }

}
