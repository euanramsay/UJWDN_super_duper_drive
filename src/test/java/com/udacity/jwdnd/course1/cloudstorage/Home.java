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

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(id = "addNoteButton")
    private WebElement addNoteButton;

    @FindBy(id = "addCredentialButton")
    private WebElement addCredentialButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleInput;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionInput;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlInput;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameInput;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordInput;

    @FindBy(id = "noteSaveChanges")
    private WebElement noteSaveChangesButton;

    @FindBy(id = "credentialSaveChangesButton")
    private WebElement credentialSaveChangesButton;

    @FindBy(id = "file-row")
    private WebElement fileRow;

    @FindBy(xpath = "//*[@id=\"note-row\"]/td[1]/button")
    private WebElement editFirstNoteButton;

    @FindBy(xpath = "//*[@id=\"note-row\"]/td[1]/a")
    private WebElement deleteFirstNoteButton;

    @FindBy(xpath = "//*[@id=\"credential-row\"]/td[1]/button")
    private WebElement editFirstCredentialButton;

    @FindBy(xpath = "//*[@id=\"credential-row\"]/td[1]/a")
    private WebElement deleteFirstCredentialButton;

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

    public void addNewCredential(String url, String username, String password) throws InterruptedException {
        Thread.sleep(1000);
        this.navCredentialsTab.click();
        Thread.sleep(1000);
        this.addCredentialButton.click();
        Thread.sleep(1000);
        this.credentialUrlInput.sendKeys(url);
        this.credentialUsernameInput.sendKeys(username);
        this.credentialPasswordInput.sendKeys(password);
        this.credentialSaveChangesButton.click();
    }

    public void editFirstNote(String additionToTitle, String additionToDescription) throws InterruptedException {
        Thread.sleep(1000);
        this.editFirstNoteButton.click();
        Thread.sleep(1000);
        this.noteTitleInput.sendKeys(additionToTitle);
        this.noteDescriptionInput.sendKeys(additionToDescription);
        this.noteSaveChangesButton.click();
    }

    public void openEditFirstCredentialModal() throws InterruptedException {
        Thread.sleep(1000);
        this.editFirstCredentialButton.click();
    }

    public void editFirstCredential(String additionToUrl, String additionToUsername, String additionToPassword) throws InterruptedException {
        Thread.sleep(1000);
        this.credentialUrlInput.sendKeys(additionToUrl);
        this.credentialUsernameInput.sendKeys(additionToUsername);
        this.credentialPasswordInput.sendKeys(additionToPassword);
        this.credentialSaveChangesButton.click();
    }

    public void deleteFirstNote() throws InterruptedException {
        Thread.sleep(1000);
        this.deleteFirstNoteButton.click();
        Thread.sleep(1000);
    }

    public void deleteFirstCredential() throws InterruptedException {
        Thread.sleep(1000);
        this.deleteFirstCredentialButton.click();
        Thread.sleep(1000);
    }

    public void navigateToNotesTab() throws InterruptedException {
        Thread.sleep(1000);
        this.navNotesTab.click();
        Thread.sleep(1000);
    }

    public void navigateToCredentialsTab() throws InterruptedException {
        Thread.sleep(1000);
        this.navCredentialsTab.click();
        Thread.sleep(1000);
    }
}
