package com.toniclecb.springbootregistration.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.toniclecb.springbootregistration.domain.h2.domain.RegisterH2;
import com.toniclecb.springbootregistration.domain.h2.repo.RegisterH2Repository;
import com.toniclecb.springbootregistration.domain.mysql.domain.Register;
import com.toniclecb.springbootregistration.domain.mysql.repo.RegisterRepository;

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
}
