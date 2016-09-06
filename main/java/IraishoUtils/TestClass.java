package IraishoUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import iraisho.kaihatsuhyoka.DateUtil;
import iraisho.kaihatsuhyoka.EditRequestPage;
import iraisho.kaihatsuhyoka.IraishoLoginPage;
import iraisho.kaihatsuhyoka.NewReqCreatePage;
import iraisho.kaihatsuhyoka.UserDashBoard;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;

public class TestClass {
	
	private WebDriver driver;
	private IraishoLoginPage loginPage;		
	private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	private Date date = new Date();
	private UserDashBoard dashBoard;
	private NewReqCreatePage newReqPage;	
	
	@BeforeTest(alwaysRun=true)
	public void setUp() {
		driver = new FirefoxDriver();
//		System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Java\\jdk1.8.0_40\\chromedriver.exe");
//		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.get("https://ontopsi.asuscomm.com:18181/rsi-bpm/");
		setLoginPage();
	}
	
	public WebDriver getDriver(){
		return driver;
	}
	
	public void setLoginPage(){
		loginPage = PageFactory.initElements(driver, IraishoLoginPage.class);
	}
	
	public IraishoLoginPage getLoginPage(){
		return loginPage;
	}
	
	public EditRequestPage NewReqSubmissionWithGenericData(String userName, String passWord)throws InterruptedException{
        dashBoard = loginPage.doLogIn(userName, passWord);
        newReqPage = dashBoard.goToNewReqCreatePage();
        newReqPage.enterReqTitle("リード開発品テスト");
        newReqPage.selectReqType("開発品");
        newReqPage.selectReqCode("リード/水銀リレー");
        newReqPage.selectDivisionDropDown("リレー生産技術科(morinaga@kumamoto.sanyu.co.jp)");
        
        //current date
        newReqPage.enterReqDate(dateFormat.format(date)); 
        newReqPage.enterCustomerField("AdamFreescale");
        newReqPage.enterOrderQuantity("55555555");
        newReqPage.enterOrderAmount("555555555");
        newReqPage.enterExpDeliveryDate(dateFormat.format(DateUtil.addDays(date, 14)));
        newReqPage.enterExpDevCoexpectedst("555555555");
        newReqPage.enterExpUnitCost("555555555");
        newReqPage.enterPurposeTextField("あいうえお");
        newReqPage.enterExpEstimationDate(dateFormat.format(DateUtil.addDays(date, 21)));
        newReqPage.enterReqContentField("あいうえお");
        newReqPage.clickSaveButton();
        newReqPage.acceptPopUpDialog();        
        
        return newReqPage.acceptSubmissionCompletePopUp();

	}
	
	@AfterClass
	public void afterClass(){
		driver.quit();
	}

}
