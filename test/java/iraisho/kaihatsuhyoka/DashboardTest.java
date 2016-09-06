package iraisho.kaihatsuhyoka;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Unit test for simple App.
 */
public class DashboardTest
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
	
	private WebDriver driver;
	private IraishoLoginPage loginPage;
	
	@BeforeMethod
	public void setUp(){
//		driver = new FirefoxDriver();
		System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Java\\jdk1.8.0_40\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.get("https://ontopsi.asuscomm.com:18181/rsi-bpm/");
		loginPage = PageFactory.initElements(driver, IraishoLoginPage.class);
	}


	@Test
    public void TitleBarTest()throws InterruptedException
    {       
        //load the data from yakuin xls.
        loginPage.typeUsername("1001");
        loginPage.typePassword("@Wpu8asw");
        UserDashBoard dashBoard = loginPage.goToUserPage();
        dashBoard.assertTitleBarItemsStringPresence();
        
    }
	
	@Test
	public void WorkBoadTest()throws InterruptedException
	{
        loginPage.typeUsername("1001");
        loginPage.typePassword("@Wpu8asw");
        UserDashBoard dashBoard = loginPage.goToUserPage();
		WorkBoard workBoard = dashBoard.clickEmpName();
		workBoard.clickEmpTab();
		workBoard.clickEmpTab();
		workBoard.changeEntryOption("100");
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
	}
	
	@AfterMethod
	public void tearDown(){
		driver.close();
	}
}
