package com.toniclecb.springbootregistration.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.toniclecb.springbootregistration.interfaces.DateTime;

@SpringBootTest
class DateTimeServiceTest {

    // @Autowired here we go create our object, because we need mock Date
    static DateTimeService dateTimeService;


    /**
     * Here we go mock the calls to methods on Date and mock the method getDate from DateTime
     */
    @BeforeAll
    public static void setUp() {
        final Date date = Mockito.mock(Date.class);
        Mockito.when(date.toString()).thenReturn("Wed Dec 14 00:43:34 UTC 2022");
        Mockito.when(date.toGMTString()).thenReturn("14 Dec 2022 00:52:13 GMT");

        final DateTime dt = Mockito.mock(DateTime.class);
        Mockito.when(dt.getDate()).thenReturn(date);

        dateTimeService = new DateTimeService(dt);
    }

    /**
     * "Test the field value equal to null"
     */
    @Test
    void dateTest(){ // renaming because this method tests only date() method
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

    @Test
    void getStringDatetimeTest(){
        String result = dateTimeService.getStringDatetime(null);
        assertEquals("Wed Dec 14 00:43:34 UTC 2022", result);

        result = dateTimeService.getStringDatetime("GMT");
        assertEquals("14 Dec 2022 00:52:13 GMT", result);

        result = dateTimeService.getStringDatetime("ISO-8601"); // 2022-12-14T01:17:39.192Z
        assertNotNull(result);
        assertTrue(result.startsWith(String.valueOf(Year.now().getValue())));
        assertTrue(result.contains("T"));
        assertTrue(result.contains("."));
    }
}
