package com.toniclecb.springbootregistration;

import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimeController {
    
    @GetMapping("/time")
    public String time(){
        return new Date().toString();
    }
}
