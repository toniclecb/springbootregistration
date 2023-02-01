package com.toniclecb.springbootregistration.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.toniclecb.springbootregistration.exceptions.UnsuportedDateFormatException;
import com.toniclecb.springbootregistration.services.DateTimeService;

@RestController
public class TimeController {
    
    @Autowired
    private Environment env;

    @Autowired
    private DateTimeService dateTimeService;
    
    @GetMapping("/time")
    public String time(){

        String datetime = env.getProperty("springbootregistration.dateformat");
        return dateTimeService.getStringDatetime(datetime);
    }

    /**
     * Method to get a value based in days, month or year
     * @param field possibles values DayOfMonth, DayOfWeek, DayOfYear, Month, Year
     * @return the value of field chosen
     */
    @GetMapping("/date/{field}")
    public String date(@PathVariable(value = "field") String field){
        String intValue = dateTimeService.date(field);
        // this will call CustomizedResponseEntityExceptionHandler.handleBadRequestExceptions()
        if (intValue != null)
            return intValue;
        else
            throw new UnsuportedDateFormatException(field);
    }
}
