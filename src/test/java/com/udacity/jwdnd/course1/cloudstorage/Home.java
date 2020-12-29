package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Home {

    @FindBy(id = "logoutButton")
    private WebElement logoutButton;

    @FindBy(id = "fileUpload")
    private WebElement fileUpload;

    @FindBy(id = "uploadSubmitButton")
    private WebElement uploadSubmitButton;

    public Home(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void logoutUser() throws InterruptedException {
        Thread.sleep(1000);
        this.logoutButton.click();
        Thread.sleep(1000);
    }

    public void uploadFile() throws InterruptedException {
        Thread.sleep(1000);
        this.fileUpload.sendKeys(System.getProperty("user.dir") + '/' + "src/test/resources/testFile.png");
        Thread.sleep(3000);
        this.uploadSubmitButton.click();
    }
}
