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

    @PostMapping(value = "register-costumer")
    public String registerCostumer(@RequestBody RegistrationRequest request){
        return registrationService.registerCostumer(request);
    }

    @PostMapping(value = "register-vendor")
    public String registerVendor(@RequestBody RegistrationRequest request){
        return registrationService.registerVendor(request);
    }

    @GetMapping
    public void confirm(@RequestParam("toke") String token ){
        registrationService.confirmToken(token);
    }

}
