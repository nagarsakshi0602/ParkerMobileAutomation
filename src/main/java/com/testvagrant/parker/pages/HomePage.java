package com.testvagrant.parker.pages;

import com.testvagrant.parker.utilities.Orientation;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.html5.LocationContext;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {

    @FindBy(id = "com.streetline.parker:id/maps_onboarding_pager_next")
    private WebElement nextBtn;

    @FindBy(id = "com.streetline.parker:id/maps_fab_park_button")
    private WebElement parkBtn;

    @FindBy(id="com.streetline.parker:id/maps_park_banner_button")
    private WebElement setParkingLocation;

    @FindBy(xpath= "//*[@resource-id='com.streetline.parker:id/maps_park_banner_label']//*[@resource-id='com.streetline.parker:id/youParkedAtTxt']")
    private WebElement parkedAtText;

    @FindBy(id="com.streetline.parker:id/maps_toolbar_filter")
    private WebElement filterBtn;

    @FindBy(id="com.streetline.parker:id/maps_filter_report_layout")
    private WebElement filterReport;

    @FindBy(id= "com.streetline.parker:id/maps_toolbar_autocomplete")
    private WebElement search;

    @FindBy(xpath = "//android.widget.RelativeLayout[@content-desc='Indoor Stadium']")
    private  WebElement searchList;

    @FindBy(id = "com.streetline.parker:id/maps_search_result_fab")
    private WebElement searchResultNav;

    @FindBy(id= "com.streetline.parker:id/maps_launch_route_layout")
    private WebElement launchRouteLayout;

    public HomePage(AppiumDriver mobiledriver) {
        super(mobiledriver);
        PageFactory.initElements(mobiledriver,this);

    }
    public void getLocation()
    {
        Location loc = mobiledriver.location();
        System.out.println("----------------------Location: "+loc);
        Location location = new Location(loc.getLatitude(),loc.getLongitude(),loc.getAltitude());

        mobiledriver.setLocation(location);


        click(search);
        sendKeys(search,"Indoor Stadium");

        click(searchList);




        click(searchResultNav);
        click(launchRouteLayout);

        Location location1 = mobiledriver.location();
        System.out.println("----------------------Location: "+location1);
        Location loca = new Location(location1.getLatitude(),location1.getLongitude(),location1.getAltitude());

        mobiledriver.setLocation(location);
    }
    public HomePage clickPark()
    {
         click(parkBtn);
         return this;
    }
    public HomePage setParkingLocation()
    {
        click(setParkingLocation);
        return this;
    }
    public String parkedTime()
    {
        String text = getText(parkedAtText).split(" ")[2];
        System.out.println("Text=======================  "+text);
        return text;
        //com.streetline.parker:id/maps_onboarding_pager_text
        //com.android.packageinstaller:id/permission_allow_button
        //com.streetline.parker:id/maps_onboarding_pager_next

        ////*[@resource-id='com.streetline.parker:id/maps_park_banner_label']//*[@resource-id='com.streetline.parker:id/youParkedAtTxt']
    }

    public HomePage clickNextAndContinue()
    {
        click(nextBtn);
        click(nextBtn);
        click(nextBtn);
        return this;
    }
    public boolean isParked()
    {
        boolean flag = false;
        if(getText(parkedAtText).split(" ")[0].equalsIgnoreCase("Parked"))
        {
            flag = true;
        }else{
            flag = false;
        }
        return flag;
    }
    public HomePage changeOrientation(Orientation orientation)
    {
        if(orientation.toString().equalsIgnoreCase("LANDSCAPE"))
        {
            mobiledriver.rotate(ScreenOrientation.LANDSCAPE);
        }else if(orientation.toString().equalsIgnoreCase("PORTRAIT")){
            mobiledriver.rotate(ScreenOrientation.PORTRAIT);
        }

        return this;
    }
    public HomePage setGeoLocation(double latitude, double longitude, int altitude)
    {
        Location location = new Location(latitude,longitude,altitude);
        mobiledriver.setLocation(location);
        return this;
    }
    public HomePage clickFilter()
    {
        click(filterBtn);
        return this;
    }
    public HomePage selectMaximumPricePerHour(String price){
        WebElement element = mobiledriver.findElement(By.xpath("//*[contains(@resource-id,'com.streetline.parker:id/priceSegControlBtn') and contains(@text,'"+price+"')]"));
        click(element);
        return this;
    }
    public HomePage selectDurationInHour(String duration)
    {
        WebElement element = mobiledriver.findElement(By.xpath("//*[contains(@resource-id,'com.streetline.parker:id/durButton') and contains(@text,'"+duration+"')]"));
        click(element);
        return this;
    }
    public HomePage selectTypeOfParking(String type){
        WebElement element = mobiledriver.findElement(By.xpath("//*[contains(@resource-id,'com.streetline.parker:id/typeButton') and contains(@text,'"+type+"')]"));
        click(element);
        return this;
    }
    public boolean isFilterReportDisplayed()
    {
        return isDisplayed(filterReport);
    }
    ////*[@resource-id='com.streetline.parker:id/maps_toolbar_toolbar']//android.widget.ImageButton ---- Menu
    //com.streetline.parker:id/activity_maps_drawer_notifications
    //com.streetline.parker:id/activity_maps_drawer_history
    //com.streetline.parker:id/activity_maps_drawer_help
    //com.streetline.parker:id/activity_maps_drawer_feedback
    //com.streetline.parker:id/activity_maps_drawer_battery
}
