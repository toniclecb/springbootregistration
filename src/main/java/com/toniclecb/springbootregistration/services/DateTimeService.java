package com.toniclecb.springbootregistration.services;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class DateTimeService {

    /**
     * Get a string value of the date
     * @param datetime ISO-8601 or GMT
     * @return
     */
    public String getStringDatetime(String datetime) {
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
     * Return the int value for the field supplied
     * @param field possibles values: DayOfMonth, DayOfWeek, DayOfYear, Month, Year
     * @return null if none of possibles values are supplied
     */
    public String date(String field){
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

        return null;
    }
}
