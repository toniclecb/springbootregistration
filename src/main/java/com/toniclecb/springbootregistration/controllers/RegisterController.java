package com.toniclecb.springbootregistration.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.toniclecb.springbootregistration.domain.mysql.domain.Register;
import com.toniclecb.springbootregistration.services.RegisterService;

// RestController marks the class as a controller where every method returns a domain object instead of a view.
@RestController
// RequestMapping is used to map web requests onto specific handler
@RequestMapping("/api/v1")
public class RegisterController {
    @Autowired
    RegisterService registerService;

    private static final Logger log = LoggerFactory.getLogger(RegisterController.class);
    
    // PostMapping is used for mapping HTTP POST requests onto specific handler methods
    @PostMapping(value = "/register")
    // @RequestBody indicates that a method parameter should be bound to the value of the HTTP request body
    public ResponseEntity<Register> insertRegister(@RequestBody Register register){
        log.info("RegisterController.insertRegister()");
        
        register = registerService.insert(register);

        log.info("RegisterController.insertRegister(), register inserted: " + register.getId());

        return ResponseEntity.ok().body(register);
    }

    @PutMapping(value = "/register")
	public ResponseEntity<Register> update(@RequestBody Register register) {
		Register savedRegister = registerService.update(register);
		
		return ResponseEntity.ok().body(savedRegister);
	}

    @DeleteMapping(value = "/register/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id){
        registerService.remove(id);
        return ResponseEntity.ok().build();
    }
}
