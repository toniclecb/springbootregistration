package com.toniclecb.springbootregistration;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toniclecb.springbootregistration.exceptions.UnsuportedDateFormatException;

@RestController
public class TimeController {
    
    @Autowired
    private Environment env;
    
    @GetMapping("/time")
    public String time(){

        String datetime = env.getProperty("springbootregistration.dateformat");
        return getStringDatetime(datetime);
    }

    /**
     * Extracting business logic to method
     * @param datetime
     * @return
     */
    private String getStringDatetime(String datetime) {
        if ("ISO-8601".equals(datetime)){
            // this is a easier way to represent datetime in a string
            return Instant.now().toString();
        } else if ("GMT".equals(datetime)){
            return new Date().toGMTString();
        } else {
            return new Date().toString();
        }
    }

    /**
     * Method to get a value based in days, month or year
     * @param field possibles values DayOfMonth, DayOfWeek, DayOfYear, Month, Year
     * @return the value of field chosen
     */
    @GetMapping("/date/{field}")
    public String date(@PathVariable(value = "field") String field){
        switch (field) {
            case "DayOfMonth":
                return "" + Instant.now().atZone(ZoneId.systemDefault()).getDayOfMonth();
            case "DayOfWeek":
                return "" + Instant.now().atZone(ZoneId.systemDefault()).getDayOfWeek();
            case "DayOfYear":
                return "" + Instant.now().atZone(ZoneId.systemDefault()).getDayOfYear();
            case "Month":
                return "" + Instant.now().atZone(ZoneId.systemDefault()).getMonth();
            case "Year":
                return "" + Instant.now().atZone(ZoneId.systemDefault()).getYear();
        }
        // this will call CustomizedResponseEntityExceptionHandler.handleBadRequestExceptions()
        throw new UnsuportedDateFormatException(field);
    }
}
