package view;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.chrome.ChromeDriver;

public class RegisterTestOld {

	private static WebDriver driver;

	@BeforeClass
	public static void setUp() {
		driver = new ChromeDriver();
		Window window = driver.manage().window();
		window.setSize(new Dimension(1920 / 2, 1080));
		window.setPosition(new Point(1920 / 2, 0));
		// driver.get("http://localhost:8080/shop-web/Controller?action=signUp");
	}

	@Before
	public void get() {
		driver.get("http://localhost:8080/Website/Controller?action=register");
	}

	@AfterClass
	public static void clean() {
		driver.quit();
	}

	private String generateRandomUseridInOrderToRunTestMoreThanOnce(String component) {
		int random = (int) (Math.random() * 1000 + 1);
		return random + component;
	}

	private void fillOutField(String name, String value) {
		WebElement field = driver.findElement(By.id(name));
		field.clear();
		field.sendKeys(value);
	}

	private void submitForm(String userid, String firstname, String lastname, String email,
		String password) {
		fillOutField("userid", userid);
		fillOutField("firstname", firstname);
		fillOutField("lastname", lastname);
		fillOutField("email", email);
		fillOutField("password", password);

		WebElement button = driver.findElement(By.id("register"));
		button.click();
	}

	@Test
	public void testRegisterCorrect() {
		// FIXME Only administrators can see the overview page... (also the
		// actual table check is wrong)

		String useridRandom = generateRandomUseridInOrderToRunTestMoreThanOnce("jakke");
		submitForm(useridRandom, "Jan", "Janssens", "jan.janssens@hotmail.com", "1234");

		String title = driver.getTitle();
		assertEquals("Home", title);

		driver.get("http://localhost:8080/shop-web/Controller?action=overview");

		ArrayList<WebElement> listItems =
			(ArrayList<WebElement>) driver.findElements(By.cssSelector("table tr"));
		boolean found = false;
		for (WebElement listItem : listItems) {
			if (listItem.getText().contains("jan.janssens@hotmail.com")
				&& listItem.getText().contains(" Jan Janssens")) {
				found = true;
			}
		}
		assertEquals(true, found);

	}

	@Test
	public void testRegisterUseridEmpty() {
		submitForm("", "Jan", "Janssens", "jan.janssens@hotmail.com", "1234");

		String title = driver.getTitle();
		assertEquals("Register", title);

		WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
		assertEquals("No userid given", errorMsg.getText());

		WebElement fieldUserid = driver.findElement(By.id("userid"));
		assertEquals("", fieldUserid.getAttribute("value"));

		WebElement fieldFirstName = driver.findElement(By.id("firstname"));
		assertEquals("Jan", fieldFirstName.getAttribute("value"));

		WebElement fieldLastName = driver.findElement(By.id("lastname"));
		assertEquals("Janssens", fieldLastName.getAttribute("value"));

		WebElement fieldEmail = driver.findElement(By.id("email"));
		assertEquals("jan.janssens@hotmail.com", fieldEmail.getAttribute("value"));

	}

	@Test
	public void testRegisterFirstNameEmpty() {
		submitForm("jakke", "", "Janssens", "jan.janssens@hotmail.com", "1234");

		String title = driver.getTitle();
		assertEquals("Register", title);

		WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
		assertEquals("No firstname given", errorMsg.getText());

		WebElement fieldUserid = driver.findElement(By.id("userid"));
		assertEquals("jakke", fieldUserid.getAttribute("value"));

		WebElement fieldFirstName = driver.findElement(By.id("firstname"));
		assertEquals("", fieldFirstName.getAttribute("value"));

		WebElement fieldLastName = driver.findElement(By.id("lastname"));
		assertEquals("Janssens", fieldLastName.getAttribute("value"));

		WebElement fieldEmail = driver.findElement(By.id("email"));
		assertEquals("jan.janssens@hotmail.com", fieldEmail.getAttribute("value"));

	}

	@Test
	public void testRegisterLastNameEmpty() {
		submitForm("jakke", "Jan", "", "jan.janssens@hotmail.com", "1234");

		String title = driver.getTitle();
		assertEquals("Register", title);

		WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
		assertEquals("No last name given", errorMsg.getText());

		WebElement fieldUserid = driver.findElement(By.id("userid"));
		assertEquals("jakke", fieldUserid.getAttribute("value"));

		WebElement fieldFirstName = driver.findElement(By.id("firstname"));
		assertEquals("Jan", fieldFirstName.getAttribute("value"));

		WebElement fieldLastName = driver.findElement(By.id("lastname"));
		assertEquals("", fieldLastName.getAttribute("value"));

		WebElement fieldEmail = driver.findElement(By.id("email"));
		assertEquals("jan.janssens@hotmail.com", fieldEmail.getAttribute("value"));

	}

	@Test
	public void testRegisterEmailEmpty() {
		submitForm("jakke", "Jan", "Janssens", "", "1234");

		String title = driver.getTitle();
		assertEquals("Register", title);

		WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
		assertEquals("No email given", errorMsg.getText());

		WebElement fieldUserid = driver.findElement(By.id("userid"));
		assertEquals("jakke", fieldUserid.getAttribute("value"));

		WebElement fieldFirstName = driver.findElement(By.id("firstname"));
		assertEquals("Jan", fieldFirstName.getAttribute("value"));

		WebElement fieldLastName = driver.findElement(By.id("lastname"));
		assertEquals("Janssens", fieldLastName.getAttribute("value"));

		WebElement fieldEmail = driver.findElement(By.id("email"));
		assertEquals("", fieldEmail.getAttribute("value"));

	}

	@Test
	public void testRegisterPasswordEmpty() {
		submitForm("jakke", "Jan", "Janssens", "jan.janssens@hotmail.com", "");

		String title = driver.getTitle();
		assertEquals("Register", title);

		WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
		assertEquals("No password given", errorMsg.getText());

		WebElement fieldUserid = driver.findElement(By.id("userid"));
		assertEquals("jakke", fieldUserid.getAttribute("value"));

		WebElement fieldFirstName = driver.findElement(By.id("firstname"));
		assertEquals("Jan", fieldFirstName.getAttribute("value"));

		WebElement fieldLastName = driver.findElement(By.id("lastname"));
		assertEquals("Janssens", fieldLastName.getAttribute("value"));

		WebElement fieldEmail = driver.findElement(By.id("email"));
		assertEquals("jan.janssens@hotmail.com", fieldEmail.getAttribute("value"));

	}

	@Test
	public void testRegisterUserAlreadyExists() {
		String useridRandom = generateRandomUseridInOrderToRunTestMoreThanOnce("pierke");
		submitForm(useridRandom, "Pieter", "Pieters", "pieter.pieters@hotmail.com", "1234");

		driver.get("http://localhost:8080/Website/Controller?action=register");

		submitForm(useridRandom, "Pieter", "Pieters", "pieter.pieters@hotmail.com", "1234");

		WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
		assertEquals("User already exists", errorMsg.getText());

		WebElement fieldUserid = driver.findElement(By.id("userid"));
		assertEquals(useridRandom, fieldUserid.getAttribute("value"));

		WebElement fieldFirstName = driver.findElement(By.id("firstname"));
		assertEquals("Pieter", fieldFirstName.getAttribute("value"));

		WebElement fieldLastName = driver.findElement(By.id("lastname"));
		assertEquals("Pieters", fieldLastName.getAttribute("value"));

		WebElement fieldEmail = driver.findElement(By.id("email"));
		assertEquals("pieter.pieters@hotmail.com", fieldEmail.getAttribute("value"));

	}
}
