package com.toniclecb.springbootregistration.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DateTimeServiceTest {

    @Autowired
    DateTimeService dateTimeService;

    /**
     * "Test the field value equal to null"
     */
    @Test
    void getStringDatetimeTest(){
        // I will write here in the comments another way to access DateTimeService and test
        // ZonedDateTime now = Instant.parse("1998-10-10T11:12:13.14Z").atZone(ZoneId.systemDefault());
        // DateTimeService dateTimeService = mock(DateTimeService.class);
        // when(dateTimeService.date(Mockito.anyString())).thenCallRealMethod();
        // when(dateTimeService.getStringDatetime(Mockito.anyString())).thenCallRealMethod();
        // when(dateTimeService.getInstantNow()).thenReturn(now);
        // valueReturned = dateTimeService.date("Year");
        // assertEquals("1998", valueReturned);

        String valueReturned = dateTimeService.date(null);
        assertNull(valueReturned);

        valueReturned = dateTimeService.date("Year");
        assertEquals(String.valueOf(Year.now().getValue()), valueReturned); // one way to get the year

        // another way to get information about dates
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayMonth = cal.get(Calendar.DAY_OF_MONTH);
        int dayYear = cal.get(Calendar.DAY_OF_YEAR);

        valueReturned = dateTimeService.date("DayOfMonth");
        assertEquals(String.valueOf(dayMonth), valueReturned);
        
        valueReturned = dateTimeService.date("DayOfYear");
        assertEquals(String.valueOf(dayYear), valueReturned);

        valueReturned = dateTimeService.date("Month");
        LocalDate currentDate = LocalDate.now();
        Month month = currentDate.getMonth();
        DayOfWeek dayWeek = currentDate.getDayOfWeek();
        assertEquals(month.toString(), valueReturned);

        valueReturned = dateTimeService.date("DayOfWeek");
        assertEquals(dayWeek.toString(), valueReturned);
    }
}
