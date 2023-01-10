package com.toniclecb.springbootregistration.domain.mysql.repo;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.toniclecb.springbootregistration.domain.mysql.domain.Register;

@Repository
public interface RegisterRepository extends PagingAndSortingRepository<Register,UUID>{
    
}
