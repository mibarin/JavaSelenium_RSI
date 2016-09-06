package iraisho.kaihatsuhyoka;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * Page Object for User's dashboard
 *
 */
public class UserDashBoard {
	
	private WebDriver driver;
	private int maxNumItems = 9;
	private String itemXpathRoot = ".//*[@id='top-controls']/div[";
	protected String reqNo;
	
	@FindBy(how = How.CLASS_NAME, using = "back-next-button")
	private WebElement backButton;
	
	@FindBy(how = How.CLASS_NAME, using = "view-contol")
	private WebElement nextButton;
	
	@FindBy(id = "top-controls")
	private WebElement topControls;
	
	@FindBy(how = How.XPATH, using = ".//*[@id='login-info']/b")
	private WebElement userName;
	
	@FindBy(how = How.CLASS_NAME, using = "req-create-icon")
	private WebElement reqCreateIcon;
	
	@FindBy(how = How.CLASS_NAME, using = "dashboard-icon")
	private WebElement dashboardIcon;
	
	@FindBy(how = How.XPATH, using=".//*[@id='spm_root-top_home_list_view']/div/div/div/table[1]/tbody/tr[1]/td/a/u")
	private WebElement empNameLink;
	
	@FindBy(how = How.XPATH, using=".//*[@id='spm_root-top_home_list_view']/div/div/table[1]/tbody/tr[2]/td[2]/a/u")
	private WebElement firstItemOnMyTurn;
	
	@FindBy(how = How.XPATH, using =".//*[@id='spm_root-top_home_list_view']/div/div/table[4]/tbody/tr[2]/td[2]/a/u")
	private WebElement firstCompletedItem;
	
	@FindBy(how = How.XPATH, using = ".//*[@id='spm_root-top_home_list_view']/div/div/table[2]/tbody/tr[2]/td[2]/a/u")
	private WebElement firstItemInWaiting;
	
//	@FindBy(how = How.LINK_TEXT, using = reqNo)
//	private WebElement linkToReqNo;
	
	public UserDashBoard(WebDriver driver){
		this.driver = driver;	
	}
	
	public void assertTitleBarItemsStringPresence() throws InterruptedException{
		List<WebElement> titleBarItems = new ArrayList<WebElement>();
		String[] definedTitleBarItems = {"ディレクトリ", "ボード", "ワーク", "依頼編集", 
				                            "依頼検索", "組織編集", "組織検索", "新規依頼"};
		for (int i=1; i<maxNumItems; i++){
			titleBarItems.add(driver.findElement(By.xpath(itemXpathRoot + i+ "]")));
			Assert.assertEquals(titleBarItems.get(i-1).getText(), definedTitleBarItems[i-1]);
		}
	}	

	
	public NewReqCreatePage goToNewReqCreatePage() throws InterruptedException{
		reqCreateIcon.click();
		return PageFactory.initElements(driver, NewReqCreatePage.class);
	}

	public WorkBoard clickEmpName() throws InterruptedException{
		empNameLink.click();
		return PageFactory.initElements(driver, WorkBoard.class);
	}
	
	public String getFirstReqNoOnMyTurn() throws InterruptedException{
		return firstItemOnMyTurn.getText();
	}
	
	public String getFirstItemInWaiting() throws InterruptedException{
		return firstItemInWaiting.getText();
	}
	
	public WorkBoard clickRequestOnMyTurn() throws InterruptedException{
		firstItemOnMyTurn.click();
		return PageFactory.initElements(driver, WorkBoard.class);		
	}
	
	public WorkBoard clickFirstRequestInWaiting() throws InterruptedException{
		firstItemInWaiting.click();
		return PageFactory.initElements(driver, WorkBoard.class);		
	}
	
	public void assertReportNumEquals(String status, String reportNo) throws InterruptedException{

		switch (status){		
		case "IN_PROGRESS":
			Assert.assertEquals(reportNo, firstCompletedItem.getText());
			break;
		case "COMPLETED":
			Assert.assertEquals(reportNo, firstCompletedItem.getText());
			break;
		default:
			System.out.println("The first argument should be 'IN_PROGRESS' or 'COMPLETED'.");
		}

	}
	
//	public boolean isReqNumExists(){
//		if (linkToReqNo.findElement(By.linkText(reqNo))) {
//			return true;
//		}  else {
//			return false;
//		}
//	}
//	
}
