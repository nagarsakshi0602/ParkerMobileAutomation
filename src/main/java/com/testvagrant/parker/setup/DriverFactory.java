package com.testvagrant.parker.setup;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class DriverFactory {
    AppiumDriver driver;
    private AppiumDriverLocalService driverLocalService;
    private Map<String, String> config;

    protected AppiumDriver getDriver(Map<String, String> config) {
        this.config = config;
        if (config.get("platform").equalsIgnoreCase("android")) {
            driver = initializeAndroidDriver();

        } else if (config.get("platform").equalsIgnoreCase("ios")) {
            //
        }
        return driver;
    }

    public AndroidDriver initializeAndroidDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", "src/test/resources/app/parker.apk");
        capabilities.setCapability("platformName", "android");
        capabilities.setCapability("deviceName", "Pixel XL");

        URL serverUrl = driverLocalService.getUrl();
        return new AndroidDriver(serverUrl, capabilities);
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
        return driverLocalService;
    }
    public void stopAppium()
    {
        driverLocalService.stop();
    }

}
