package com.toniclecb.springbootregistration.domain.mysql.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toniclecb.springbootregistration.domain.mysql.domain.Register;

@Repository
public interface RegisterRepository extends JpaRepository<Register,UUID>{
    
}
