package com.toniclecb.springbootregistration.services;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.toniclecb.springbootregistration.interfaces.DateTime;

@Service
public class DateTimeService {

    private final DateTime dateTime;

    public DateTimeService(final DateTime dateTime) {
        this.dateTime = dateTime;
    }

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
            return this.dateTime.getDate().toGMTString();
        } else {
            return this.dateTime.getDate().toString();
        }
    }

    /**
     * Return the int value for the field supplied
     * @param field possibles values: DayOfMonth, DayOfWeek, DayOfYear, Month, Year
     * @return null if none of possibles values are supplied
     */
    public String date(String field){
        if (field == null)
            return null;
        switch (field) {
            case "DayOfMonth":
                return "" + getInstantNow().getDayOfMonth();
            case "DayOfWeek":
                return "" + getInstantNow().getDayOfWeek();
            case "DayOfYear":
                return "" + getInstantNow().getDayOfYear();
            case "Month":
                return "" + getInstantNow().getMonth();
            case "Year":
                return "" + getInstantNow().getYear();
        }

        return null;
    }

    /**
     * Let's write less code putting repetitive code in method
     * @return
     */
    public ZonedDateTime getInstantNow() {
        return Instant.now().atZone(ZoneId.systemDefault());
    }
}
