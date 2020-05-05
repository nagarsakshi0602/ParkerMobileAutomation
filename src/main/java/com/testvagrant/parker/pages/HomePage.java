package com.testvagrant.parker.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.html5.LocationContext;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {
    @FindBy(id= "com.streetline.parker:id/maps_onboarding_pager_text")
    private WebElement enableLocation;

    @FindBy(id= "com.android.packageinstaller:id/permission_allow_button")
    private WebElement allowPermission;

    @FindBy(id = "com.streetline.parker:id/maps_onboarding_pager_next")
    private WebElement nextBtn;

    @FindBy(id = "com.streetline.parker:id/maps_fab_park_button")
    private WebElement parkBtn;

    @FindBy(id="com.streetline.parker:id/maps_park_banner_button")
    private WebElement setParkingLocation;

    @FindBy(xpath= "//*[@resource-id='com.streetline.parker:id/maps_park_banner_label']//*[@resource-id='com.streetline.parker:id/youParkedAtTxt']")
    private WebElement parkedAtText;

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
    public HomePage enableLocation()
    {
        click(enableLocation);
        click(allowPermission);
        return this;
    }
    public HomePage clickNextAndContinue()
    {
        click(nextBtn);
        click(nextBtn);
        click(nextBtn);
        return this;
    }
}
