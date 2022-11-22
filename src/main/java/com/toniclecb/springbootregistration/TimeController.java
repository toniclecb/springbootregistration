package com.toniclecb.springbootregistration;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimeController {
    
    @Autowired
    private Environment env;
    
    @GetMapping("/time")
    public String time(){

        String datetime = env.getProperty("springbootregistration.dateformat");
        if ("ISO-8601".equals(datetime)){
            // this is a easier way to represent datetime in a string
            return Instant.now().toString();
        } else if ("GMT".equals(datetime)){
            return new Date().toGMTString();
        } else {
            return new Date().toString();
        }
    }
}
