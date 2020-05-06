package com.testvagrant.parker.pages;

import com.testvagrant.parker.utilities.Orientation;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    AppiumDriver mobiledriver;
    WebDriverWait wait;

    public BasePage(AppiumDriver mobiledriver) {
        this.mobiledriver = mobiledriver;
        wait = new WebDriverWait(this.mobiledriver, 30);
    }

    protected void click(WebElement element) {
        waitForElementToBeClickable(element);
        element.click();
    }

    protected void sendKeys(WebElement element, String text) {
        waitForElementToBeVisible(element);
        element.clear();
        element.sendKeys(text);
    }

    protected void waitForElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForElementToBeInVisible(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    protected void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected boolean isDisplayed(WebElement element) {
        return element.isDisplayed();
    }

    protected String getText(WebElement element) {
        return element.getText();
    }

    protected void scrollUp(WebElement element) {

        Double screenHeightStart = getScreenHeight() * 0.9;
        int scrollStart = screenHeightStart.intValue();

        Double screenHeightEnd = getScreenHeight() * 0.3;
        int scrollEnd = screenHeightEnd.intValue();

        Double screenWidthMid = getScreenWidth() * 0.5;
        int screenWidth = screenWidthMid.intValue();
        while (isDisplayed(element)) {
            TouchAction touchAction = new TouchAction(mobiledriver);

            touchAction.press(PointOption.point(screenWidth, scrollStart))
                    .moveTo(PointOption.point(screenWidth, scrollEnd))
                    .perform().release();
            break;
        }


    }

    protected Dimension getDimensions() {
        return mobiledriver.manage().window().getSize();
    }

    protected int getScreenHeight() {
        return getDimensions().getHeight();
    }

    protected int getScreenWidth() {
        return getDimensions().getWidth();
    }

    protected void modifyOrientation(Orientation orientation) {
        if (orientation.toString().equalsIgnoreCase("LANDSCAPE")) {
            mobiledriver.rotate(ScreenOrientation.LANDSCAPE);
        } else if (orientation.toString().equalsIgnoreCase("PORTRAIT")) {
            mobiledriver.rotate(ScreenOrientation.PORTRAIT);
        }

    }

    protected void setLocation(double latitude, double longitude, int altitude) {
        Location location = new Location(latitude, longitude, altitude);
        mobiledriver.setLocation(location);
    }

}
