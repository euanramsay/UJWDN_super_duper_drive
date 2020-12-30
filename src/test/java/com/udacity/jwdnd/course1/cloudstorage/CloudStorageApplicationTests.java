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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private final String username = "username";
	private final String password = "password";
	private final String firstName = "Bill";
	private final String lastName = "Gates";
	private final String noteTitle = "Test title";
	private final String noteDescription = "Test description";
	private final String additionToNoteTitle = " edited";
	private final String additionToNoteDescription = " edited";
	private final String credentialUrl = "www.icloud.com";
	private final String credentialUsername = "credential";
	private final String credentialPassword = "password";
	private final String additionToUrl = "/mail";
	private final String additionToUsername = "24";
	private final String additionToPassword = "1";

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
	@Order(1)
	public void userCanNotAccessHomePageWhenLoggedOutTest() {
		driver.get("http://localhost:" + this.port + "/home");
		assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(2)
	public void userCanAccessHomePageWhenLoggedInButNotWhenLoggedOutTest() throws InterruptedException {
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
	@Order(3)
	public void userCanCreateANoteTest() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
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
	@Order(4)
	public void userCanEditAnExistingNoteTest() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		Login login = new Login(driver);
		login.loginUser(username, password);

		assertEquals("Home", driver.getTitle());

		Home home = new Home(driver);
		home.navigateToNotesTab();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("note-row")));

		assertEquals(noteTitle, driver.findElement(By.xpath("//*[@id=\"note-row\"]/th")).getText());
		assertEquals(noteDescription, driver.findElement(By.xpath("//*[@id=\"note-row\"]/td[2]")).getText());

		home.editFirstNote(additionToNoteTitle, additionToNoteDescription);
		Result result = new Result(driver);
		result.continueToHomePage();
		home.navigateToNotesTab();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("note-row")));

		assertEquals(noteTitle + additionToNoteTitle, driver.findElement(By.xpath("//*[@id=\"note-row\"]/th")).getText());
		assertEquals(noteDescription + additionToNoteDescription, driver.findElement(By.xpath("//*[@id=\"note-row\"]/td[2]")).getText());
	}

	@Test
	@Order(5)
	public void deletedNoteIsNoLongerDisplayedTest() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		Login login = new Login(driver);
		login.loginUser(username, password);

		assertEquals("Home", driver.getTitle());

		Home home = new Home(driver);
		home.navigateToNotesTab();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("note-row")));

		assertEquals(noteTitle + additionToNoteTitle, driver.findElement(By.xpath("//*[@id=\"note-row\"]/th")).getText());
		assertEquals(noteDescription + additionToNoteDescription, driver.findElement(By.xpath("//*[@id=\"note-row\"]/td[2]")).getText());

		home.deleteFirstNote();
		Result result = new Result(driver);
		result.continueToHomePage();
		home.navigateToNotesTab();
		List<WebElement> noteTableRows = driver.findElements(By.xpath("//*[@id=\"userTable\"]/tbody/tr"));

		assertTrue(noteTableRows.isEmpty());
	}

	@Test
	@Order(6)
	public void userCanCreateACredentialTest() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
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
	@Order(7)
	public void userCanEditAnExistingCredentialTest() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		Login login = new Login(driver);
		login.loginUser(username, password);

		assertEquals("Home", driver.getTitle());

		Home home = new Home(driver);
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
		Result result = new Result(driver);
		result.continueToHomePage();
		home.navigateToCredentialsTab();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("credential-row")));

		assertEquals(credentialUrl + additionToUrl, driver.findElement(By.xpath("//*[@id=\"credential-row\"]/th")).getText());
		assertEquals(credentialUsername + additionToUsername, driver.findElement(By.xpath("//*[@id=\"credential-row\"]/td[2]")).getText());
		// Password shown in list is encrypted
		assertNotEquals(credentialPassword + additionToPassword, driver.findElement(By.xpath("//*[@id=\"credential-row\"]/td[3]")).getText());
	}

	@Test
	@Order(8)
	public void deletedCredentialIsNoLongerDisplayedTest() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		Login login = new Login(driver);
		login.loginUser(username, password);

		assertEquals("Home", driver.getTitle());

		Home home = new Home(driver);
		home.navigateToCredentialsTab();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("credential-row")));

		assertEquals(credentialUrl + additionToUrl, driver.findElement(By.xpath("//*[@id=\"credential-row\"]/th")).getText());
		assertEquals(credentialUsername + additionToUsername, driver.findElement(By.xpath("//*[@id=\"credential-row\"]/td[2]")).getText());
		// Password shown in list is encrypted
		assertNotEquals(credentialPassword + additionToPassword, driver.findElement(By.xpath("//*[@id=\"credential-row\"]/td[3]")).getText());

		home.deleteFirstCredential();
		Result result = new Result(driver);
		result.continueToHomePage();
		home.navigateToCredentialsTab();
		List<WebElement> credentialTableRows = driver.findElements(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr"));

		assertTrue(credentialTableRows.isEmpty());
	}
}
