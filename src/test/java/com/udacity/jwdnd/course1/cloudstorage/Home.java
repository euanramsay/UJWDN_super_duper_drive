package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Home {

    @FindBy(id = "logoutButton")
    private WebElement logoutButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id = "addNoteButton")
    private WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleInput;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionInput;

    @FindBy(id = "noteSaveChanges")
    private WebElement noteSaveChangesButton;

    @FindBy(id = "file-row")
    private WebElement fileRow;

    @FindBy(xpath = "//*[@id=\"note-row\"]/td[1]/button")
    private WebElement editFirstNoteButton;

    public Home(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void logoutUser() throws InterruptedException {
        Thread.sleep(1000);
        this.logoutButton.click();
        Thread.sleep(1000);
    }

    public void addNewNote(String title, String description) throws InterruptedException {
        Thread.sleep(1000);
        this.navNotesTab.click();
        Thread.sleep(1000);
        this.addNoteButton.click();
        Thread.sleep(1000);
        this.noteTitleInput.sendKeys(title);
        this.noteDescriptionInput.sendKeys(description);
        this.noteSaveChangesButton.click();
    }

    public void editFirstNote(String additionToTitle, String additionToDescription) throws InterruptedException {
        Thread.sleep(1000);
        this.editFirstNoteButton.click();
        Thread.sleep(1000);
        this.noteTitleInput.sendKeys(additionToTitle);
        this.noteDescriptionInput.sendKeys(additionToDescription);
        this.noteSaveChangesButton.click();
    }

    public void navigateToNotesTab() throws InterruptedException {
        Thread.sleep(1000);
        this.navNotesTab.click();
        Thread.sleep(1000);
    }
}
