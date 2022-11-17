package com.toniclecb.springbootregistration;

import java.time.Instant;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimeController {
    
    @GetMapping("/time")
    public String time(){
        // this is a easier way to represent datetime in a string
        return Instant.now().toString();
    }
}
