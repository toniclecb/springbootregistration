package com.toniclecb.springbootregistration.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.toniclecb.springbootregistration.domain.mysql.domain.Register;
import com.toniclecb.springbootregistration.services.RegisterService;

// RestController marks the class as a controller where every method returns a domain object instead of a view.
@RestController
// RequestMapping is used to map web requests onto specific handler
@RequestMapping("/api/v1/register")
public class RegisterController {
    @Autowired
    RegisterService registerService;

    private static final Logger log = LoggerFactory.getLogger(RegisterController.class);
    
    // PostMapping is used for mapping HTTP POST requests onto specific handler methods
    @PostMapping
    // @RequestBody indicates that a method parameter should be bound to the value of the HTTP request body
    public ResponseEntity<Register> insertRegister(@RequestBody Register register){
        log.info("RegisterController.insertRegister()");
        
        register = registerService.insert(register);

        log.info("RegisterController.insertRegister(), register inserted: " + register.getId());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(register.getId()).toUri();

        return ResponseEntity.created(location).body(register);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> get(@PathVariable("id") UUID id){
        Register r = registerService.find(id);
        return ResponseEntity.ok().body(r);
    }

    @PutMapping
	public ResponseEntity<Register> update(@RequestBody Register register) {
		Register savedRegister = registerService.update(register);
		
		return ResponseEntity.ok().body(savedRegister);
	}

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id){
        registerService.remove(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<Register>> find(@RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "10") Integer size,
        @RequestParam(value = "sort", defaultValue = "asc") String sort){

        // only if the value of sort is 'desc' will use descending
        Pageable pageable = PageRequest.of(page, size, "desc".equalsIgnoreCase(sort) ? Sort.by("name").descending() : Sort.by("name").ascending());
        
        return ResponseEntity.ok().body(registerService.findAll(pageable));
    }
}
