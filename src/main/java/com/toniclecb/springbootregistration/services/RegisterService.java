package com.toniclecb.springbootregistration.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.toniclecb.springbootregistration.domain.h2.domain.RegisterH2;
import com.toniclecb.springbootregistration.domain.h2.repo.RegisterH2Repository;
import com.toniclecb.springbootregistration.domain.mysql.domain.Register;
import com.toniclecb.springbootregistration.domain.mysql.repo.RegisterRepository;
import com.toniclecb.springbootregistration.exceptions.ResourceNotFoundException;

// Service Marks a Java class that performs some service, such as execute business logic, perform calculations and call external APIs. This annotation is a specialized form of the @Component annotation intended to be used in the service layer.
@Service
public class RegisterService {
    @Autowired
    RegisterRepository registerRepository;
    @Autowired
    RegisterH2Repository h2Repository;
    
    private static final Logger log = LoggerFactory.getLogger(RegisterService.class);

    /**
     * 
     * @param register
     * @return
     */
    public Register insert(Register register){
        log.info("RegisterService.insert()");

        Register newRegister = registerRepository.save(register);
        
        RegisterH2 registerH2 = new RegisterH2();
        registerH2.setId(newRegister.getId());
        registerH2.setName(newRegister.getName());
        registerH2 = h2Repository.save(registerH2);

        log.info("RegisterService.insert(), register inserted in H2: " + registerH2.getId());

        return newRegister;
    }

    public Register update(Register register) {
        log.info("RegisterService.update()");

        Register savedRegister = registerRepository.findById(register.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this id: " + register.getId()));

        savedRegister.setName(register.getName());
        savedRegister.setDescription(register.getDescription());

        Register updatedRegister = registerRepository.save(savedRegister);
        
        RegisterH2 savedRegisterH2 = h2Repository.findById(register.getId()).orElse(null);

        if (savedRegisterH2 != null){
            savedRegisterH2.setName(register.getName());
            savedRegisterH2 = h2Repository.save(savedRegisterH2);
        } else {
            log.info("H2: No record found for this id: " + register.getId());
        }

        log.info("RegisterService.update(), register updated in H2: " + savedRegisterH2.getId());

        return updatedRegister;
    }

    public void remove(UUID id) {
        log.info("RegisterService.remove()");
        Register savedRegister = registerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this id: " + id));
        
        registerRepository.delete(savedRegister);

        RegisterH2 savedRegisterH2 = h2Repository.findById(id).orElse(null);

        if (savedRegisterH2 != null){
            h2Repository.delete(savedRegisterH2);
        } else {
            log.info("H2: No record found for this id: " + id);
        }
    }

    public List<Register> findAll() {
        return registerRepository.findAll();
    }
}
