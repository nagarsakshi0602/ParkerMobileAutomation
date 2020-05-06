package com.testvagrant.parker.setup;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class DriverFactory {
    AppiumDriver driver;
    private AppiumDriverLocalService driverLocalService;
    private Map<String, String> config;

    public DriverFactory(Map<String, String> config)
    {
        this.config = config;
    }

    protected AppiumDriver getDriver() {

        if (config.get("platform").equalsIgnoreCase("android")) {
            driver = initializeAndroidDriver();

        } else if (config.get("platform").equalsIgnoreCase("ios")) {
            //
        }
        return driver;
    }

    public AndroidDriver initializeAndroidDriver() {
        AndroidDriver localDriver = null;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        //capabilities.setCapability("app", "/Users/admin/ParkerMobileAppAutomation/src/test/resources/app/parker.apk");
        capabilities.setCapability("platformName", "android");
        capabilities.setCapability("deviceName", "Redmi Note 7 Pro");
        capabilities.setCapability("deviceId","80ca6acf");
        capabilities.setCapability("appPackage","com.streetline.parker");
        capabilities.setCapability("appActivity","com.streetline.parker.ui.main.MainActivity");
        capabilities.setCapability("autoGrantPermissions", true);
        capabilities.setCapability("noReset",true);

        //capabilities.setCapability("noReset",true);

        localDriver = new AndroidDriver(driverLocalService, capabilities);

        return localDriver;
    }

    public AppiumDriverLocalService startAppium() {

        if (config.get("server").equalsIgnoreCase("local")) {
            driverLocalService = AppiumDriverLocalService.buildDefaultService();

        } else if (config.get("server").equalsIgnoreCase("remote")) {
            driverLocalService = AppiumDriverLocalService
                    .buildService(new AppiumServiceBuilder()
                            .withIPAddress(config.get("ipAddress"))
                            .usingPort(Integer.parseInt(config.get("port"))));

        }
        System.out.println("Appium Started Successfully..........");
        return driverLocalService;
    }
    public void stopAppium()
    {
        try
        {
            driverLocalService.stop();
        }catch (Exception e)
        {
            driverLocalService.stop();
        }
        System.out.println("Appium Stopped Successfully....");
    }

}
