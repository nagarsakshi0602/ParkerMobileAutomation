package com.testvagrant.parker.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HistoryPage extends BasePage {

    @FindBy(xpath = "//*[@resource-id='com.streetline.parker:id/timeLbl']")
    private WebElement recentTime;

    public HistoryPage(AppiumDriver mobiledriver) {
        super(mobiledriver);
        PageFactory.initElements(mobiledriver, this);
    }

    public String getRecentTime() {
        System.out.println(getText(recentTime).replaceAll("[a-zA-Z]", ""));
        return getText(recentTime).replaceAll("[a-zA-Z]", "");
    }
}
