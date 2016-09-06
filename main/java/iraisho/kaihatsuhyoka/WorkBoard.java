package iraisho.kaihatsuhyoka;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.support.ui.WebDriverWait;

public class WorkBoard {
	
	private WebDriver driver;

	@FindBy(how=How.XPATH, using = ".//*[@id='spm_root-top_home_form-employee_1001-0_info']/div/table[1]/tbody/tr[2]/td/a/u")
    private WebElement empTitle;
	
	@FindBy(how=How.XPATH, using = ".//*[@id='spm_root-top_home_form']/ul/li[1]/a[1]/span")
	private WebElement empTab;
	
	@FindBy(how=How.CSS, using = ".ui-corner-top.ui-tabs-selected.ui-state-active.ui-state-focus>a>span")
	private WebElement deptTab;
	
	@FindBy(how=How.CSS, using = ".dataTables_length>select")
	private WebElement entryDropDown;
	
	@FindBy(how=How.XPATH, using = ".//*[@id='top-controls']/div[8]/img")
	private WebElement clearButton;
	
	public WorkBoard(WebDriver driver){
		this.driver = driver;		
//		this.wait = new WebDriverWait(driver, 30);
	}
	
	public WorkBoard clickEmpTitle()throws InterruptedException{
		empTitle.click();
		return this;
	}
	
	public WorkBoard clickEmpTab() throws InterruptedException{
		empTab.click();
		return this;
	}
	
	
	public WorkBoard clickDeptTab() throws InterruptedException{
		deptTab.click();
		return this;
	}
	
	public WorkBoard changeEntryOption(String entry) throws InterruptedException{
		Select dropdown = new Select(entryDropDown);
		dropdown.selectByVisibleText(entry);
		return this;
	}
	
	public WorkBoard clearWorkBoard()throws InterruptedException{
		clearButton.click();
		return this;
	}
	
	
	public EditRequestPage clickRequestNo(String reqNo) throws InterruptedException{
		driver.findElement(By.xpath(".//*[@id='spm_root-top_home_form-task_request_" + reqNo +"_info']/div/table[1]/tbody/tr[1]/td/a/u")).click();
		return PageFactory.initElements(driver, EditRequestPage.class);
	}
	
	public EditRequestPage clickClaimNo(String reqNo) throws InterruptedException{
		driver.findElement(By.xpath(".//*[@id='spm_root-top_home_form-claim_request_" + reqNo +"_info']/div/table[1]/tbody/tr[1]/td/a/u")).click();
		return PageFactory.initElements(driver, EditRequestPage.class);
	}	
	
	
}


