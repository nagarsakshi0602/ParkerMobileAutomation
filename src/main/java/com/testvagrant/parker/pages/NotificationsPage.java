package com.testvagrant.parker.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NotificationsPage extends BasePage {
    @FindBy(xpath = "//*[@resource-id='android:id/title' and @text='Navigation notifications']/../following-sibling::*/android.widget.Switch[@resource-id='android:id/switch_widget']")
    private WebElement navigationSwitch;

    @FindBy(xpath = "//*[@resource-id='android:id/title' and @text='Auto timer notifications']/../following-sibling::*/android.widget.Switch")
    private WebElement timerSwitch;

    @FindBy(xpath = "//*[@resource-id='android:id/title' and @text='Auto park notifications']/../following-sibling::*/android.widget.Switch")
    private WebElement autoParkSwitch;

    @FindBy(xpath = "//*[@content-desc='Navigate up']")
    private WebElement navigateUp;

    public NotificationsPage(AppiumDriver mobiledriver) {
        super(mobiledriver);
        PageFactory.initElements(mobiledriver,this);
    }
    public NotificationsPage switchNavigation(){
        click(navigationSwitch);
        return this;
    }
    public NotificationsPage switchAutoTimer(){
        click(timerSwitch);
        return this;
    }
    public NotificationsPage switchAutoPark(){
        click(autoParkSwitch);
        return this;
    }
    public HomePage navigateUp(){
        click(navigateUp);
        return new HomePage(mobiledriver);
    }
    public boolean isNavigationOn() {
        boolean flag = false;
        if(navigationSwitch.getAttribute("checked").equalsIgnoreCase("true")){
            flag = true;
        }else if(navigationSwitch.getAttribute("checked").equalsIgnoreCase("false")){
            flag = false;
        }
        return flag;
    }
    public boolean isAutoTimerOn() {
        boolean flag = false;
        if(timerSwitch.getAttribute("checked").equalsIgnoreCase("true")){
            flag = true;
        }else if(timerSwitch.getAttribute("checked").equalsIgnoreCase("false")){
            flag = false;
        }
        return flag;
    }
    public boolean isAutoParkOn() {
        boolean flag = false;
        if(autoParkSwitch.getAttribute("checked").equalsIgnoreCase("true")){
            flag = true;
        }else if(autoParkSwitch.getAttribute("checked").equalsIgnoreCase("false")){
            flag = false;
        }
        return flag;
    }
}
