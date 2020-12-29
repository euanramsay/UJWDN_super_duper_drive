package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private String username = "billg";
	private String password = "password";
	private String firstName = "Bill";
	private String lastName = "Gates";

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
	public void homePageRedirectsToLoginPageWhenLoggedOut() {
		driver.get("http://localhost:" + this.port + "/home");
		assertEquals("Login", driver.getTitle());
	}

	@Test
	public void userCanSignUpAndLogIn() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");

		Signup signup = new Signup(driver);
		signup.signupUser(firstName, lastName, username, password);
		signup.clickLoginLink();

		Login login = new Login(driver);
		login.loginUser(username, password);

		assertEquals("Home", driver.getTitle());
	}

	@Test
	public void userCanLogout() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");

		Signup signup = new Signup(driver);
		signup.signupUser(firstName, lastName, username, password);
		signup.clickLoginLink();

		Login login = new Login(driver);
		login.loginUser(username, password);

		assertEquals("Home", driver.getTitle());

		Home home = new Home(driver);
		home.logoutUser();

		assertEquals("Login", driver.getTitle());
	}

	@Test
	public void userCanUploadFile() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");

		Signup signup = new Signup(driver);
		signup.signupUser(firstName, lastName, username, password);
		signup.clickLoginLink();

		Login login = new Login(driver);
		login.loginUser(username, password);

		assertEquals("Home", driver.getTitle());

		Home home = new Home(driver);
		home.uploadFile();

		assertEquals("Result", driver.getTitle());
	}

}
