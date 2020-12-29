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

		assertEquals(editButton.getText(), "Edit");
		assertEquals(deleteButton.getText(), "Delete");
		assertEquals(noteRowTitle.getText(), noteTitle);
		assertEquals(noteRowDescription.getText(), noteDescription);
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

		assertEquals(driver.findElement(By.xpath("//*[@id=\"note-row\"]/th")).getText(), noteTitle);
		assertEquals(driver.findElement(By.xpath("//*[@id=\"note-row\"]/td[2]")).getText(), noteDescription);

		home.editFirstNote(additionToNoteTitle, additionToNoteDescription);

		result.continueToHomePage();
		home.navigateToNotesTab();

		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("note-row")));

		assertEquals(driver.findElement(By.xpath("//*[@id=\"note-row\"]/th")).getText(), noteTitle + additionToNoteTitle);
		assertEquals(driver.findElement(By.xpath("//*[@id=\"note-row\"]/td[2]")).getText(), noteDescription + additionToNoteDescription);
	}

	@Test
	public void deletedNoteIsNoLongerShown() throws InterruptedException {
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

		assertEquals(driver.findElement(By.xpath("//*[@id=\"note-row\"]/th")).getText(), noteTitle);
		assertEquals(driver.findElement(By.xpath("//*[@id=\"note-row\"]/td[2]")).getText(), noteDescription);

		home.deleteFirstNote();
		home.navigateToNotesTab();

		List<WebElement> noteTableRows = driver.findElements(By.xpath("//*[@id=\"userTable\"]/tbody/tr"));
		assertTrue(noteTableRows.isEmpty());
	}
}
