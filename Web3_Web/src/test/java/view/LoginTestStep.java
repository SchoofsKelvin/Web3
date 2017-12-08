package view;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Properties;

import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import db.PersonRepository;
import db.PersonRepositoryInDatabase;

public class LoginTestStep {

	static WebDriver		driver;
	static PersonRepository	rep;

	private String			userid;

	@Before
	public static void setUp() {
		Properties props = new Properties();
		props.setProperty("url", "jdbc:postgresql://gegevensbanken.khleuven.be:51516/2TX32");
		props.setProperty("ssl", "true");
		props.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
		props.setProperty("user", "r0620441");
		props.setProperty("scheme_person", "r0620441_test.person");
		props.setProperty("scheme_product", "r0620441_test.product");
		try {
			props.setProperty("password", System.getenv("password"));
		} catch (NullPointerException e) {
			System.err.println("Forgot to set 'password' in environment");
			System.exit( -1);
		}
		rep = new PersonRepositoryInDatabase(props);
		driver = new ChromeDriver();
		Window window = driver.manage().window();
		window.setSize(new Dimension(1920 / 2, 1080));
		window.setPosition(new Point(1920 / 2, 0));
		// driver.get("http://localhost:8080/shop-web/Controller?action=signUp");
	}

	@After
	public static void quit() throws Throwable {
		driver.quit();
		rep.close();
	}

	@After
	public void deleteUser() {
		try {
			rep.delete(userid);
		} catch (Exception e) {}
	}

	private void fillOutField(String name, String value) {
		WebElement field = driver.findElement(By.id(name));
		field.clear();
		field.sendKeys(value);
	}

	private void registerUser(String name, String user, String pass) {
		driver.get("http://localhost:8080/Web3_Web/Controller?action=register");

		fillOutField("userid", user);
		fillOutField("firstname", name);
		fillOutField("lastname", "Janssens");
		fillOutField("length", "170");
		fillOutField("email", "jan.janssens@hotmail.com");
		fillOutField("password", pass);

		WebElement button = driver.findElement(By.id("register"));
		button.click();
	}

	// private void deleteUser(String userid) {
	// // FIXME Only administrators can delete users...
	// driver.get("http://localhost:8080/Web3_Web/Controller?action=overview");
	// String cssSelector = "a[href*='deletePerson&userid=" + userid + "']";
	// WebElement link = driver.findElement(By.cssSelector(cssSelector));
	// link.click();
	//
	// WebElement deleteButtonConfirm =
	// driver.findElement(By.cssSelector("input[type='submit'"));
	// deleteButtonConfirm.click();
	// }

	private void logIn(String userid, String password) {
		fillOutField("userid", userid);
		fillOutField("password", password);

		WebElement button = driver.findElement(By.id("login"));
		button.click();
	}

	private boolean findTekstWelcome(String name) {
		ArrayList<WebElement> paragraphs =
			(ArrayList<WebElement>) driver.findElements(By.tagName("p"));
		for (WebElement webElement : paragraphs) {
			if (webElement.getText().equals("Welcome, " + name + ".")) return true;
		}
		return false;
	}

	@Given("^a userid \"([^\"]*)\"$")
	public void a_userid(String userid) throws Throwable {
		this.userid = userid;
	}

	@When("^I register as \"([^\"]*)\" using password \"([^\"]*)\"$")
	public void i_register_as_using_password(String name, String pass) throws Throwable {
		registerUser(name, userid, pass);
	}

	@When("^I login with password \"([^\"]*)\"$")
	public void i_login_with_password(String password) throws Throwable {
		logIn(userid, password);
	}

	@Then("^I am logged in as \"([^\"]*)\"$")
	public void i_am_logged_in_as(String name) throws Throwable {
		assertTrue(findTekstWelcome(name));
	}

}
