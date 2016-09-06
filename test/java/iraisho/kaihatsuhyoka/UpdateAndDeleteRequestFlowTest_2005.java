package iraisho.kaihatsuhyoka;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

/*
 * This is user workflow using 2005 as a requester, 2001 is its boss and quality 
 * manager/quality team is 1043.  In all cases, the submissions are accepted.
 */
		

public class UpdateAndDeleteRequestFlowTest_2005 {
	
	private WebDriver driver;
	private IraishoLoginPage loginPage;		
	private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	private Date date = new Date();
	private String requestNo;	
	private UserDashBoard dashBoard;
	private EditRequestPage submitRequest;
	private NewReqCreatePage newReqPage;
	
	@BeforeTest(alwaysRun=true)
	public void setUp() {
		driver = new FirefoxDriver();
//		System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Java\\jdk1.8.0_40\\chromedriver.exe");
//		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.get("https://ontopsi.asuscomm.com:18181/rsi-bpm/");
		loginPage = PageFactory.initElements(driver, IraishoLoginPage.class);
	}

	/*
	 * User 2005 creates a request and submit it.
	 */
    
	@BeforeTest (dependsOnMethods={"setUp"})
	public void NewReqSubmission()throws InterruptedException
	{
        dashBoard = loginPage.doLogIn("2005", "q1111111");
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
        //go to 依頼編集
        
		submitRequest = newReqPage.acceptSubmissionCompletePopUp();
        //store request number
        requestNo = submitRequest.getReqID();
	}
	
	@Test(priority = 1)
	public void updateRequest() throws InterruptedException{		
		
		submitRequest.enterRequestContent("7890,.\n`~!@#$%^&*()_+{}|\":<>?,./;'[]\\=-");
		submitRequest.clickReqUpdateButton();

		if (acceptSubmissionPopups(submitRequest)){
				Assert.assertTrue(true);			
		} else {
			Assert.assertFalse(true);
		}
	}
	
	
	@Test(priority = 2)
	public void deleteRequest() throws InterruptedException{		

		submitRequest.clickReqDeleteButton();	
		if (acceptSubmissionPopups(submitRequest)){
			dashBoard = submitRequest.goToDashBoard();
			try{
				if (driver.findElement(By.linkText(requestNo)).isDisplayed()) {
					Assert.fail();
				}
			}catch (NoSuchElementException e){
				Assert.assertTrue(true);			
			}
		} else {
			Assert.assertFalse(true);
		}
		
	}


	@AfterClass
	public void afterClass(){
		driver.quit();
	}


	@SuppressWarnings("finally")
	private boolean acceptSubmissionPopups(EditRequestPage editRequestPage) throws InterruptedException{
        
		boolean isPopupsCleared = true;
		
		try {
			//XXしますか？OK.
		    editRequestPage.clickOkOnPopUp();
            //XX完了: ReqNo ok.
            editRequestPage.acceptSubmissionOkPopup();
            
		} catch (Exception e) {
			isPopupsCleared = false;
		} finally {
			return isPopupsCleared;
		}
	}
}
