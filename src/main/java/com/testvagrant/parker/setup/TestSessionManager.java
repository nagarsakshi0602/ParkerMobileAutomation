package com.testvagrant.parker.setup;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class TestSessionManager {
    public AppiumDriver mobileDriver;

    AppiumDriverLocalService driverLocalService;

    public AppiumDriver initializeMobileDriver(String platformName) {
        if (platformName.equalsIgnoreCase("android")) {
            mobileDriver = initializeAndroidDriver();
        }
        return mobileDriver;
    }


    public AndroidDriver initializeAndroidDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", "/Users/admin/ParkerMobileAppAutomation/src/test/resources/app/parker.apk");
        capabilities.setCapability("platformName", "android");
        capabilities.setCapability("deviceName", "Pixel XL");


        driverLocalService = AppiumDriverLocalService.buildDefaultService();
        driverLocalService.start();

        URL serverUrl = null;
        serverUrl = driverLocalService.getUrl();
        // serverUrl = new URL("http://localhost:4723/wd/hub");

        return new AndroidDriver(serverUrl, capabilities);
    }

    public void closeSession() {
        // mobileDriver.quit();
        driverLocalService.stop();
    }

    public AppiumDriver getDriver() {

        return mobileDriver;
    }
}
