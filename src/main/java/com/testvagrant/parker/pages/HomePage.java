package com.testvagrant.parker.pages;

import com.testvagrant.parker.utilities.Orientation;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {

    @FindBy(id = "com.streetline.parker:id/maps_fab_park_button")
    private WebElement parkBtn;

    @FindBy(id = "com.streetline.parker:id/maps_park_banner_button")
    private WebElement setParkingLocation;

    @FindBy(id = "com.streetline.parker:id/maps_onboarding_pager_next")
    private WebElement nextBtn;

    @FindBy(id = "com.streetline.parker:id/maps_toolbar_filter")
    private WebElement filterBtn;

    @FindBy(id = "com.streetline.parker:id/dismiss_park_location_button")
    private WebElement unpark;

    @FindBy(xpath = "//*[@resource-id='com.streetline.parker:id/maps_toolbar_toolbar']//android.widget.ImageButton")
    private WebElement menu;

    @FindBy(id = "com.streetline.parker:id/maps_filter_report_layout")
    private WebElement filterReport;

    @FindBy(xpath = "//*[@resource-id='com.streetline.parker:id/maps_park_banner_label']//*[@resource-id='com.streetline.parker:id/youParkedAtTxt']")
    private WebElement parkedAtText;

    @FindBy(id = "com.streetline.parker:id/maps_toolbar_autocomplete")
    private WebElement search;

    @FindBy(id = "com.streetline.parker:id/maps_search_result_fab")
    private WebElement searchResultNav;

    @FindBy(xpath = "//*[@resource-id='com.streetline.parker:id/maps_launch_route_text' and @text='Start route']")
    private WebElement launchRouteLayout;

    @FindBy(id = "com.streetline.parker:id/maps_toolbar_close")
    private WebElement closeSearch;

    @FindBy(id = "com.streetline.parker:id/maps_address_back")
    private WebElement backBtn;


    public HomePage(AppiumDriver mobiledriver) {
        super(mobiledriver);
        PageFactory.initElements(mobiledriver, this);

    }

    public HomePage clickPark() {
        click(parkBtn);
        return this;
    }

    public HomePage setParkingLocation() {
        click(setParkingLocation);
        return this;
    }

    public HomePage clickNextAndContinue() {
        click(nextBtn);
        click(nextBtn);
        click(nextBtn);
        return this;
    }

    public HomePage changeOrientation(Orientation orientation) {
        modifyOrientation(orientation);
        return this;
    }

    public HomePage setGeoLocation(double latitude, double longitude, int altitude) {
        setLocation(latitude, longitude, altitude);
        return this;
    }
    public HomePage setDefaultLocation()
    {
        setLocation(12.955312, 77.638740,0);
        return this;
    }

    public HomePage clickFilter() {
        click(filterBtn);
        return this;
    }

    public HomePage selectMaximumPricePerHour(String price) {
        WebElement element = mobiledriver.findElement(By
                .xpath("//*[contains(@resource-id,'com.streetline.parker:id/priceSegControlBtn') and contains(@text,'" + price + "')]"));
        click(element);
        return this;
    }

    public HomePage selectDurationInHour(String duration) {
        WebElement element = mobiledriver.findElement(By
                .xpath("//*[contains(@resource-id,'com.streetline.parker:id/durButton') and contains(@text,'" + duration + "')]"));
        click(element);
        return this;
    }

    public HomePage selectTypeOfParking(String type) {
        WebElement element = mobiledriver.findElement(By
                .xpath("//*[contains(@resource-id,'com.streetline.parker:id/typeButton') and contains(@text,'" + type + "')]"));
        click(element);
        return this;
    }
    public HomePage enterLocationInSearch(String searchText){
        click(search);
        sendKeys(search,searchText);
        waitForElementToBeInVisible(mobiledriver.findElement(By.id("com.streetline.parker:id/maps_search_progress")));
        WebElement element = mobiledriver.findElement(By.xpath("//android.widget.RelativeLayout[@content-desc='"+searchText+"'][1]"));
        click(element);
        return this;
    }
    public HomePage startNavigation(){
        click(searchResultNav);
        return this;
    }
    public HomePage launchRoute(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        click(launchRouteLayout);
        return this;
    }
    public HomePage closeSearch(){
        click(closeSearch);
        return this;
    }
    public HomePage clickUnpark(){
        click(unpark);
        return this;
    }
    public MenuPage clickMenu() {
        click(menu);
        return new MenuPage(mobiledriver);
    }
    public HomePage clickBack(){
        click(backBtn);
        return this;
    }

    public boolean isFilterReportDisplayed() {
        return isDisplayed(filterReport);
    }

    public boolean isParked() {
        boolean flag = false;
        try {
            if (getText(parkedAtText).split(" ")[0].equalsIgnoreCase("Parked")) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (NoSuchElementException e) {
            flag = false;
        }
        return flag;
    }

    public String parkedTime() {
        String text = getText(parkedAtText).split(" ")[2];
        return text;
    }



}
