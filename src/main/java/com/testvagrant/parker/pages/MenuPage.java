package com.testvagrant.parker.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MenuPage extends BasePage {
    @FindBy(id= "com.streetline.parker:id/activity_maps_drawer_notifications")
    private WebElement notifications;

    @FindBy(id="com.streetline.parker:id/activity_maps_drawer_history")
    private WebElement history;

    @FindBy(id="com.streetline.parker:id/activity_maps_drawer_help")
    private WebElement help;

    @FindBy(id="com.streetline.parker:id/activity_maps_drawer_feedback")
    private WebElement feedback;

    @FindBy(id="com.streetline.parker:id/activity_maps_drawer_battery")
    private WebElement troubleshooting;

    public MenuPage(AppiumDriver mobiledriver) {
        super(mobiledriver);
        PageFactory.initElements(mobiledriver,this);
    }
    public MenuPage clickNotifications()
    {
        click(notifications);
        return this;
    }
    public MenuPage clickHistory()
    {
        click(history);
        return this;
    }
    public MenuPage clickHelp()
    {
        click(help);
        return this;
    }
    public MenuPage clickFeedback()
    {
        click(feedback);
        return this;
    }
    public MenuPage clickTroubleshooting()
    {
        click(troubleshooting);
        return this;
    }
}
