package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.view.*;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private WebDriverWait driverWait;
	private String baseUrl;
	private List<WebElement> list;
	private NotesPO notesPO;
	private CredentialsPO credentialsPO;
	private WebElement webEl;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		this.driverWait = new WebDriverWait(driver, 20);
		this.baseUrl = "http://localhost:" + this.port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	private void login() {
		driver.get(baseUrl + "/login");
		LoginPO loginPO = new LoginPO(driver);
		loginPO.login("Username", "Password2020");
		driverWait.until(ExpectedConditions.titleIs("Home"));
	}

	private void logout() {
		driver.get(baseUrl + "/home");
		HomePO homePO = new HomePO(driver);
		homePO.logoutWeb();
		driverWait.until(ExpectedConditions.titleIs("Login"));
	}

	private void signup() {
		driver.get(baseUrl + "/signup");
		assertEquals("Sign Up", driver.getTitle());
		SignupPO signupPO = new SignupPO(driver);
		signupPO.signup("Firstname", "Lastname", "Username", "Password2020");
	}

	@Test
	@Order(1)
	public void unauthorizedAccessTest() {
		// Test unauthorized access
		driver.get(baseUrl + "/home");
		assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(2)
	public void loginPageAccessTest() {
		//Test login page access
		driver.get(baseUrl + "/login");
		assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(3)
	public void signupTest() {
		//Test signup page access and signup functionality
		driver.get(baseUrl + "/signup");
		assertEquals("Sign Up", driver.getTitle());
		SignupPO signupPO = new SignupPO(driver);
		signupPO.signup("DifferenteName", "DifferentLname", "DifferenteUser", "Password2020");
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'" + "You successfully signed up!" + "')]"));
		assertTrue("Signed Up", list.size() != 0);
	}

	@Test
	@Order(4)
	public void loginTest() {
		//Test login after signup
		signup();
		login();
		assertEquals("Home", driver.getTitle());
	}

	@Test
	@Order(5)
	public void logoutTest() {
		//Test logout and try to access unauthorized page
		signup();
		login();
		logout();
		assertEquals("Login", driver.getTitle());
		driver.get(baseUrl + "/home");
		assertEquals("Login", driver.getTitle());
	}


	@Test
	@Order(6)
	public void noteCreationTest() {
		//Test note creation
		signup();
		login();
		driver.get(baseUrl + "/home");
		notesPO = new NotesPO(driver);
		notesPO.createNote("Note title Selenium", "Note description Selenium");
		list = driver.findElements(By.xpath("//*[contains(text(),'" + "Note title Selenium" + "')]"));
		assertTrue("Note created", list.size() != 0);
	}

	@Test
	@Order(7)
	public void noteUpdateTest() {
		//Test note edition
		signup();
		login();
		driver.get(baseUrl + "/home");
		notesPO = new NotesPO(driver);
		notesPO.createNote("Note title Selenium", "Note description Selenium");
		notesPO.editNote("Edited", "Note description Selenium");
		list = driver.findElements(By.xpath("//*[contains(text(),'" + "Edited" + "')]"));
		assertTrue("Note edited", list.size() != 0);
	}

	@Test
	@Order(8)
	public void noteDeletionTest() {
		//Test note edition
		signup();
		login();
		driver.get(baseUrl + "/home");
		notesPO = new NotesPO(driver);
		notesPO.createNote("Note title Selenium", "Note description Selenium");
		notesPO.deleteNote();
		list = driver.findElements(By.xpath("//*[contains(text(),'" + "Edited" + "')]"));
		assertTrue("Note deleted", list.size() == 0);
	}

	@Test
	@Order(9)
	public void credentialCreationTest() {
		//Test credential creation, and verifies the password is encrypted
		signup();
		login();
		driver.get(baseUrl + "/home");
		credentialsPO = new CredentialsPO(driver);
		credentialsPO.createCredential("http://www.test.com", "user1", "Password1");
		list = driver.findElements(By.xpath("//*[contains(text(),'" + "user1" + "')]"));
		assertTrue("Credential created", list.size() != 0);
		webEl = driver.findElement(By.id("credential-password-item"));
		assertNotEquals("Password not equals", "Password1", webEl.getAttribute("value"));
	}

	@Test
	@Order(10)
	public void credentialPasswordUnencryptedTest() {
		//Test password unencrypted displayed on modal and credential edition
		signup();
		login();
		driver.get(baseUrl + "/home");
		credentialsPO = new CredentialsPO(driver);
		credentialsPO.createCredential("http://www.test.com", "user1", "Password1");
		credentialsPO.showCredentialModal();
		webEl = driver.findElement(By.id("credential-password"));
		assertEquals("Password1", webEl.getAttribute("value"));
		credentialsPO.editCredential("http://www.test.com", "user2", "Password1");
		webEl = driver.findElement(By.id("credential-username-item"));
		assertEquals("user2", webEl.getText());
	}

	@Test
	@Order(11)
	public void credentialDeletionTest() {
		//Test credential deletion
		signup();
		login();
		driver.get(baseUrl + "/home");
		credentialsPO = new CredentialsPO(driver);
		credentialsPO.createCredential("http://www.test.com", "user1", "Password1");
		credentialsPO.deleteCredential();
		list = driver.findElements(By.xpath("//*[contains(text(),'" + "user2" + "')]"));
		assertTrue("Credential deleted", list.size() == 0);
	}
}
