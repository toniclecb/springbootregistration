package com.toniclecb.springbootregistration.domain.h2.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toniclecb.springbootregistration.domain.h2.domain.RegisterH2;

@Repository
public interface RegisterH2Repository extends JpaRepository<RegisterH2, UUID>{
    
}
