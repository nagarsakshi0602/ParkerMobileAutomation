package com.testvagrant.parker.setup;


import com.testvagrant.parker.utilities.readers.YamlReader;
import io.appium.java_client.AppiumDriver;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static com.testvagrant.parker.utilities.readers.ConfigPropertyReader.getProperty;

public class TestSessionManager {
    private YamlReader yaml;
    private DriverFactory wd;
    public AppiumDriver mobileDriver;

    public TestSessionManager() {
        wd = new DriverFactory(getConfigurations());
        yaml = new YamlReader("src/test/resources/Data/TestData.yml");
    }

    public void setDriver() {
        wd.startAppium();

        mobileDriver = wd.getDriver();

    }

    public void closeSession() {
        wd.stopAppium();
    }

    public AppiumDriver getDriver() {
        return mobileDriver;
    }

    private Map<String, String> getConfigurations() {
        String[] configKeys = {"platform", "server", "ipAddress", "port"};
        Map<String, String> config = new HashMap<String, String>();
        for (String string : configKeys) {
            try {
                if (System.getProperty(string).isEmpty()) {
                    config.put(string, getProperty("src/main/resources/config.properties", string));
                } else {
                    config.put(string, System.getProperty(string));
                }
            } catch (NullPointerException e) {
                config.put(string, getProperty("src/main/resources/config.properties", string));
            }
        }
        return config;
    }

    public void sendAppInBackground() {
        mobileDriver.runAppInBackground(Duration.ofSeconds(10));
    }

    public void launchApp() {
        mobileDriver.launchApp();
    }

    public void closeAppSession() {
        mobileDriver.closeApp();

    }
}
