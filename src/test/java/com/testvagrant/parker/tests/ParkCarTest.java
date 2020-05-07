package com.testvagrant.parker.tests;

import com.testvagrant.parker.pages.HistoryPage;
import com.testvagrant.parker.pages.HomePage;
import com.testvagrant.parker.pages.NotificationsPage;
import com.testvagrant.parker.setup.TestSessionManager;
import com.testvagrant.parker.utilities.DateUtils;
import com.testvagrant.parker.utilities.Orientation;
import org.testng.Assert;
import org.testng.annotations.*;

import static com.testvagrant.parker.utilities.readers.YamlReader.getYamlValue;

public class ParkCarTest {
    TestSessionManager testSession;
    HomePage homePage;
    NotificationsPage notificationsPage;
    HistoryPage historyPage;

    @BeforeTest
    public void setup() {
        testSession = new TestSessionManager();
        testSession.setDriver();
    }

    @BeforeMethod
    public void launch() {
        testSession.launchApp();
        homePage = new HomePage(testSession.getDriver())
                .setDefaultLocation()
                .clickNextAndContinue();
    }

    @Test
    public void parkCarAtCurrentLocation() {
        homePage = homePage
                .clickPark()
                .setParkingLocation();
        Assert.assertTrue(homePage.isParked());
        Assert.assertEquals(homePage.parkedTime(), DateUtils.currentTimeShort());
    }

    @Test
    public void changeOrientationToLanscapeAndPark() {
        homePage = homePage
                .changeOrientation(Orientation.LANDSCAPE)
                .clickPark()
                .setParkingLocation();
        Assert.assertTrue(homePage.isParked());
        Assert.assertEquals(homePage.parkedTime(), DateUtils.currentTimeShort());

    }

    @Test
    public void changeLocationAndParkCar() {
        //36.037870, -120.019265
        homePage = homePage
                .setGeoLocation(Double.parseDouble(getYamlValue("California.latitude")),
                        Double.parseDouble(getYamlValue("California.longitude")),
                        Integer.parseInt(getYamlValue("California.altitude")))
                .clickFilter()
                .selectDurationInHour(getYamlValue("Duration.1hr"))
                .selectMaximumPricePerHour(getYamlValue("MaximumPrice.free"))
                .clickFilter();
        Assert.assertTrue(homePage.isFilterReportDisplayed());
        homePage.clickPark()
                .setParkingLocation();
        Assert.assertTrue(homePage.isParked());
        Assert.assertEquals(homePage.parkedTime(), DateUtils.currentTimeShort());

    }

    @Test
    public void turnOffNotificationAndPark() {
        notificationsPage = homePage
                .clickMenu()
                .clickNotifications()
                .switchNavigation()
                .switchAutoTimer()
                .switchAutoPark();
        Assert.assertFalse(notificationsPage.isNavigationOn());
        Assert.assertFalse(notificationsPage.isAutoTimerOn());
        Assert.assertFalse(notificationsPage.isAutoParkOn());
        homePage = notificationsPage
                .navigateUp()
                .clickPark()
                .setParkingLocation();
        Assert.assertTrue(homePage.isParked());
        Assert.assertEquals(homePage.parkedTime(), DateUtils.currentTimeShort());
    }

    @Test
    public void driveToALocationAndPark() {
        homePage = homePage
                .enterLocationInSearch(getYamlValue("IndoorStadium.name"))
                .closeSearch()
                .setGeoLocation(Double.parseDouble(getYamlValue("IndoorStadium.latitude")),
                        Double.parseDouble(getYamlValue("IndoorStadium.longitude")),
                        Integer.parseInt(getYamlValue("IndoorStadium.altitude")))
                .clickPark()
                .setParkingLocation();
        Assert.assertTrue(homePage.isParked());
        Assert.assertEquals(homePage.parkedTime(), DateUtils.currentTimeShort());

    }

    @Test
    public void sendAppInBackgroundAndUnpark() {
        homePage = homePage
                .clickPark()
                .setParkingLocation();
        testSession.sendAppInBackground();
        homePage.clickUnpark();
        Assert.assertFalse(homePage.isParked());

    }

    @Test
    public void killAppAfterParkAndVerifyUnpark() {
        homePage = homePage
                .clickPark()
                .setParkingLocation();
        testSession.closeAppSession();
        testSession.launchApp();
        homePage = homePage.clickNextAndContinue();
        Assert.assertFalse(homePage.isParked());
    }

    @Test
    public void parkAndLookForRecentHistory() {
        homePage = homePage
                .clickPark()
                .setParkingLocation()
                .clickBack();
        String time = homePage.parkedTime();
        System.out.println(time);
        historyPage = homePage
                .clickMenu()
                .clickHistory();
        Assert.assertTrue(time.contains(historyPage.getRecentTime()));

    }

    @AfterMethod
    public void close() {
        testSession.closeAppSession();

    }

    @AfterTest
    public void tearDown() {
        testSession.closeSession();
    }

}
