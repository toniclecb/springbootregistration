package com.toniclecb.springbootregistration;

import java.util.Date;

import com.toniclecb.springbootregistration.interfaces.DateTime;

public class DateTimeImpl implements DateTime {
    @Override
    public Date getDate() {
       return new Date();
    }
}
