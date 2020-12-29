package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private String username = "billg";
	private String password = "password";
	private String firstName = "Bill";
	private String lastName = "Gates";
	private String noteTitle = "Test title";
	private String noteDescription = "Test description";
	private String additionToNoteTitle = " edited";
	private String additionToNoteDescription = " edited";
	private String credentialUrl = "www.icloud.com";
	private String credentialUsername = "credential";
	private String credentialPassword = "password";
	private String additionToUrl = "/mail";
	private String additionToUsername = "24";
	private String additionToPassword = "1";

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void userCanNotAccessHomePageWhenLoggedOut() {
		driver.get("http://localhost:" + this.port + "/home");
		assertEquals("Login", driver.getTitle());
	}

	@Test
	public void userCanAccessHomePageWhenLoggedInButNotWhenLoggedOut() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");

		Signup signup = new Signup(driver);
		signup.signupUser(firstName, lastName, username, password);
		signup.clickLoginLink();

		Login login = new Login(driver);
		login.loginUser(username, password);

		assertEquals("Home", driver.getTitle());

		Home home = new Home(driver);
		home.logoutUser();

		driver.get("http://localhost:" + this.port + "/home");

		assertEquals("Login", driver.getTitle());
	}

	@Test
	public void userCanCreateANote() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");

		Signup signup = new Signup(driver);
		signup.signupUser(firstName, lastName, username, password);
		signup.clickLoginLink();

		Login login = new Login(driver);
		login.loginUser(username, password);

		assertEquals("Home", driver.getTitle());

		Home home = new Home(driver);
		home.addNewNote(noteTitle, noteDescription);

		assertEquals("Result", driver.getTitle());

		Result result = new Result(driver);
		result.continueToHomePage();
		home.navigateToNotesTab();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("note-row")));

		WebElement editButton = driver.findElement(By.xpath("//*[@id=\"note-row\"]/td[1]/button"));
		WebElement deleteButton = driver.findElement(By.xpath("//*[@id=\"note-row\"]/td[1]/a"));
		WebElement noteRowTitle = driver.findElement(By.xpath("//*[@id=\"note-row\"]/th"));
		WebElement noteRowDescription = driver.findElement(By.xpath("//*[@id=\"note-row\"]/td[2]"));

		assertEquals("Edit", editButton.getText());
		assertEquals("Delete", deleteButton.getText());
		assertEquals(noteTitle, noteRowTitle.getText());
		assertEquals(noteDescription, noteRowDescription.getText());
	}

	@Test
	public void userCanEditAnExistingNote() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");

		Signup signup = new Signup(driver);
		signup.signupUser(firstName, lastName, username, password);
		signup.clickLoginLink();

		Login login = new Login(driver);
		login.loginUser(username, password);

		assertEquals("Home", driver.getTitle());

		Home home = new Home(driver);
		home.addNewNote(noteTitle, noteDescription);

		assertEquals("Result", driver.getTitle());

		Result result = new Result(driver);
		result.continueToHomePage();
		home.navigateToNotesTab();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("note-row")));

		assertEquals(noteTitle, driver.findElement(By.xpath("//*[@id=\"note-row\"]/th")).getText());
		assertEquals(noteDescription, driver.findElement(By.xpath("//*[@id=\"note-row\"]/td[2]")).getText());

		home.editFirstNote(additionToNoteTitle, additionToNoteDescription);

		result.continueToHomePage();
		home.navigateToNotesTab();

		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("note-row")));

		assertEquals(noteTitle + additionToNoteTitle, driver.findElement(By.xpath("//*[@id=\"note-row\"]/th")).getText());
		assertEquals(noteDescription + additionToNoteDescription, driver.findElement(By.xpath("//*[@id=\"note-row\"]/td[2]")).getText());
	}

	@Test
	public void deletedNoteIsNoLongerDisplayed() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");

		Signup signup = new Signup(driver);
		signup.signupUser(firstName, lastName, username, password);
		signup.clickLoginLink();

		Login login = new Login(driver);
		login.loginUser(username, password);

		assertEquals("Home", driver.getTitle());

		Home home = new Home(driver);
		home.addNewNote(noteTitle, noteDescription);

		assertEquals("Result", driver.getTitle());

		Result result = new Result(driver);
		result.continueToHomePage();
		home.navigateToNotesTab();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("note-row")));

		assertEquals(noteTitle, driver.findElement(By.xpath("//*[@id=\"note-row\"]/th")).getText());
		assertEquals(noteDescription, driver.findElement(By.xpath("//*[@id=\"note-row\"]/td[2]")).getText());

		home.deleteFirstNote();
		home.navigateToNotesTab();

		List<WebElement> noteTableRows = driver.findElements(By.xpath("//*[@id=\"userTable\"]/tbody/tr"));
		assertTrue(noteTableRows.isEmpty());
	}

	@Test
	public void userCanCreateACredential() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");

		Signup signup = new Signup(driver);
		signup.signupUser(firstName, lastName, username, password);
		signup.clickLoginLink();

		Login login = new Login(driver);
		login.loginUser(username, password);

		assertEquals("Home", driver.getTitle());

		Home home = new Home(driver);
		home.addNewCredential(credentialUrl, credentialUsername, credentialPassword);

		assertEquals("Result", driver.getTitle());

		Result result = new Result(driver);
		result.continueToHomePage();
		home.navigateToCredentialsTab();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("credential-row")));

		WebElement editButton = driver.findElement(By.xpath("//*[@id=\"credential-row\"]/td[1]/button"));
		WebElement deleteButton = driver.findElement(By.xpath("//*[@id=\"credential-row\"]/td[1]/a"));
		WebElement credentialRowUrl = driver.findElement(By.xpath("//*[@id=\"credential-row\"]/th"));
		WebElement credentialRowUsername = driver.findElement(By.xpath("//*[@id=\"credential-row\"]/td[2]"));
		WebElement credentialRowPassword = driver.findElement(By.xpath("//*[@id=\"credential-row\"]/td[3]"));

		assertEquals("Edit", editButton.getText());
		assertEquals("Delete", deleteButton.getText());
		assertEquals(credentialUrl, credentialRowUrl.getText());
		assertEquals(credentialUsername, credentialRowUsername.getText());
		// Password shown in list is encrypted
		assertNotEquals(credentialPassword, credentialRowPassword.getText());
		assertFalse(credentialRowPassword.getText().isEmpty());
	}

	@Test
	public void userCanEditAnExistingCredential() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");

		Signup signup = new Signup(driver);
		signup.signupUser(firstName, lastName, username, password);
		signup.clickLoginLink();

		Login login = new Login(driver);
		login.loginUser(username, password);

		assertEquals("Home", driver.getTitle());

		Home home = new Home(driver);
		home.addNewCredential(credentialUrl, credentialUsername, credentialPassword);

		assertEquals("Result", driver.getTitle());

		Result result = new Result(driver);
		result.continueToHomePage();
		home.navigateToCredentialsTab();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("credential-row")));

		assertEquals(credentialUrl, driver.findElement(By.xpath("//*[@id=\"credential-row\"]/th")).getText());
		assertEquals(credentialUsername, driver.findElement(By.xpath("//*[@id=\"credential-row\"]/td[2]")).getText());
		// Password shown in list is encrypted
		assertNotEquals(credentialPassword, driver.findElement(By.xpath("//*[@id=\"credential-row\"]/td[3]")).getText());

		home.openEditFirstCredentialModal();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("credentialModalLabel")));
		// Password shown in modal is unencrypted
		assertEquals(credentialPassword, driver.findElement(By.id("credential-password")).getAttribute("value"));

		home.editFirstCredential(additionToUrl, additionToUsername, additionToPassword);

		result.continueToHomePage();
		home.navigateToCredentialsTab();

		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("credential-row")));

		assertEquals(credentialUrl + additionToUrl, driver.findElement(By.xpath("//*[@id=\"credential-row\"]/th")).getText());
		assertEquals(credentialUsername + additionToUsername, driver.findElement(By.xpath("//*[@id=\"credential-row\"]/td[2]")).getText());
		// Password shown in list is encrypted
		assertNotEquals(credentialPassword + additionToPassword, driver.findElement(By.xpath("//*[@id=\"credential-row\"]/td[3]")).getText());
	}

	@Test
	public void deletedCredentialIsNoLongerDisplayed() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");

		Signup signup = new Signup(driver);
		signup.signupUser(firstName, lastName, username, password);
		signup.clickLoginLink();

		Login login = new Login(driver);
		login.loginUser(username, password);

		assertEquals("Home", driver.getTitle());

		Home home = new Home(driver);
		home.addNewCredential(credentialUrl, credentialUsername, credentialPassword);

		assertEquals("Result", driver.getTitle());

		Result result = new Result(driver);
		result.continueToHomePage();
		home.navigateToCredentialsTab();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("credential-row")));

		assertEquals(credentialUrl, driver.findElement(By.xpath("//*[@id=\"credential-row\"]/th")).getText());
		assertEquals(credentialUsername, driver.findElement(By.xpath("//*[@id=\"credential-row\"]/td[2]")).getText());
		// Password shown in list is encrypted
		assertNotEquals(credentialPassword, driver.findElement(By.xpath("//*[@id=\"credential-row\"]/td[3]")).getText());

		home.deleteFirstCredential();
		home.navigateToCredentialsTab();

		List<WebElement> credentialTableRows = driver.findElements(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr"));
		assertTrue(credentialTableRows.isEmpty());
	}
}
