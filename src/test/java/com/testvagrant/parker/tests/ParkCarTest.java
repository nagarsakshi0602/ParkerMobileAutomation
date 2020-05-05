package com.testvagrant.parker.tests;

import com.testvagrant.parker.pages.HomePage;
import com.testvagrant.parker.setup.TestSessionManager;
import com.testvagrant.parker.utilities.DateUtils;
import org.openqa.selenium.Platform;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;

public class ParkCarTest {
    TestSessionManager testSessionManager;
    HomePage homePage;

    @BeforeTest
    public void setup()
    {
        testSessionManager = new TestSessionManager();
        testSessionManager.setDriver();
    }
    @Test
    public void parkCarAtCurrentLocation()
    {
        homePage = new HomePage(testSessionManager.getDriver())
                .clickNextAndContinue()
                .clickPark()
                .setParkingLocation();
        Assert.assertEquals(homePage.parkedTime(), DateUtils.currentTimeShort());
    }
    @AfterTest
    public void close()
    {
        testSessionManager.closeSession();
    }

}
