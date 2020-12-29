package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Result {

    @FindBy(id = "successContinueLink")
    private WebElement successContinueLink;

    public Result(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void continueToHomePage() throws InterruptedException {
        Thread.sleep(1000);
        this.successContinueLink.click();
        Thread.sleep(1000);
    }
}
