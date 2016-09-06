package iraisho.kaihatsuhyoka;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

public class LoginTest {


	private WebDriver driver;
	private IraishoLoginPage loginPage;
	
	@BeforeMethod	
	public void setUp(){
		driver = new FirefoxDriver();
		driver.manage().deleteAllCookies();	
		driver.get("https://ontopsi.asuscomm.com:18181/rsi-bpm/");	
		loginPage = PageFactory.initElements(driver, IraishoLoginPage.class);
	}


	@Test
	public void BlankPasswordMessageTest() throws InterruptedException
	{
		loginPage.typeUsername("1001");
		loginPage.typePassword("");
		loginPage.clickOnLoginPageButton();
		loginPage.assertMismatchAlertMessage();
		loginPage.clickOkOnAlert();
	}
	;
	@Test
	public void BlankUsernameMessageTest() throws InterruptedException
	{
		loginPage.typeUsername("");
		loginPage.typePassword("@Wpu8asw");
		loginPage.clickOnLoginPageButton();
		
		loginPage.assertMismatchAlertMessage();
		loginPage.clickOkOnAlert();
	}

	@Test
	public void IncorrectPsswdMessageTest() throws InterruptedException
	{
		loginPage.typeUsername("1002");
		loginPage.typePassword("@Wpu8asw");
		loginPage.clickOnLoginPageButton();
		loginPage.assertMismatchAlertMessage();
		loginPage.clickOkOnAlert();
	}

	@Test
	public void IncorrectUnameMessageTest() throws InterruptedException
	{
		String userName = "0000";
		loginPage.typeUsername(userName);
		loginPage.typePassword("@Wpu8asw");
		loginPage.clickOnLoginPageButton();
		loginPage.assertNonExistentUserAlertMessage(userName);
		loginPage.clickOkOnAlert();
	}


	@AfterMethod
	public void afterMethod() {
		driver.close();
	}

}
