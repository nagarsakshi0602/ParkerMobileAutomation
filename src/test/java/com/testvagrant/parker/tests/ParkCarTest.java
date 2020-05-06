package com.testvagrant.parker.tests;

import com.testvagrant.parker.pages.HomePage;
import com.testvagrant.parker.setup.TestSessionManager;
import com.testvagrant.parker.utilities.DateUtils;
import com.testvagrant.parker.utilities.Orientation;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ParkCarTest {
    TestSessionManager testSession;
    HomePage homePage;

    @BeforeTest
    public void setup()
    {
        testSession = new TestSessionManager();
        testSession.setDriver();
    }
    @Test
    public void parkCarAtCurrentLocation()
    {
        homePage = new HomePage(testSession.getDriver())
                .clickNextAndContinue()
                .clickPark()
                .setParkingLocation();
        Assert.assertTrue(homePage.isParked());
        Assert.assertEquals(homePage.parkedTime(), DateUtils.currentTimeShort());
    }
    @Test
    public void changeOrientationToLanscapeAndPark()
    {
        homePage = new HomePage(testSession.getDriver())
                .clickNextAndContinue()
                .changeOrientation(Orientation.LANDSCAPE)
                .clickPark()
                .setParkingLocation();
        Assert.assertTrue(homePage.isParked());
        Assert.assertEquals(homePage.parkedTime(), DateUtils.currentTimeShort());
    }
    @Test
    public void changeLocationAndParkCar()
    {
        //36.037870, -120.019265
        homePage = new HomePage(testSession.getDriver())
                .clickNextAndContinue()
                .setGeoLocation(36.037870,-120.019265,0)
                .clickFilter()
                .selectDurationInHour("1")
                .selectMaximumPricePerHour("Free")
                .clickFilter();
        Assert.assertTrue(homePage.isFilterReportDisplayed());
        homePage.clickPark()
                .setParkingLocation();
        Assert.assertTrue(homePage.isParked());
        Assert.assertEquals(homePage.parkedTime(), DateUtils.currentTimeShort());
    }
    @AfterTest
    public void close()
    {
        testSession.closeSession();
    }

}
