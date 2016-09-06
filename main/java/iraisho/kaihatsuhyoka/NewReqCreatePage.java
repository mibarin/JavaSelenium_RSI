package iraisho.kaihatsuhyoka;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class NewReqCreatePage {
	
	//----claim
	
	@FindBy(name = "claim_product_name")
	private WebElement claimProductName;
	
	@FindBy(name = "claim_lot")
	private WebElement claimLot;
	
	@FindBy(name = "claim_quantity")
	private WebElement claimQuantity;
	
	
	//----kaihatsuhyoka
	
	@FindBy(name = "req_type")
	private WebElement reqTypeDropDown;
	
	@FindBy(name = "req_title")
	private WebElement reqTitle;
	
	@FindBy(name = "req_code")
	private WebElement reqCodeDropDown;
	
	@FindBy(name = "charged_division_id")
	private WebElement divisionDropDown;
	
	@FindBy(name = "req_date")
	private WebElement reqDate;
	
	@FindBy(name = "customer_id")
	private WebElement custId;
	
	@FindBy(name = "req_purpose")
	private WebElement purposeTextField;
	
	@FindBy(name = "order_quantity")
	private WebElement orderQuantity;
	
	@FindBy(name = "order_amount")
	private WebElement orderAmount;
	
	@FindBy(name = "expected_delivery_date")
	private WebElement expectedDeliveryDate;
	
	@FindBy(name = "expected_dev_cost")
	private WebElement expectedDevCost;
	
	@FindBy(name = "expected_unit_cost")
	private WebElement expectedUnitCost;
	
	@FindBy(name = "expected_estimation_date")
	private WebElement expectedEstimationDate;
	
	@FindBy(name = "req_content")
	private WebElement reqContentField;
	
	@FindBy(css = ".form_command-update_entity>input")
	private WebElement saveButton;
	
	@FindBy(id = "popup_ok")
	private WebElement popUpOkButton;
	
	
	private WebDriver driver;
	
	
	//claim
	public NewReqCreatePage enterClaimProductName(String claimPName) throws InterruptedException{
		claimProductName.sendKeys(claimPName);
		return this;		
	}
	
	public NewReqCreatePage enterClaimLot(String clot) throws InterruptedException{
		claimLot.sendKeys(clot);
		return this;		
	}
	
	public NewReqCreatePage enterClaimQuantity(String cQuantity) throws InterruptedException{
		claimQuantity.sendKeys(cQuantity);
		return this;		
	}
	
	
	//kaihatsuhyoka
	
	public NewReqCreatePage(WebDriver driver) throws InterruptedException{
		this.driver = driver;		
//		this.wait = new WebDriverWait(driver, 30);
	}
	
	public NewReqCreatePage enterReqTitle(String s) throws InterruptedException{
		reqTitle.sendKeys(s);
		return this;
	}
	
	public NewReqCreatePage selectReqType(String s) throws InterruptedException{
		Select dropdown = new Select(reqTypeDropDown);
		dropdown.selectByVisibleText(s);		
		return this;
	}
	
	public NewReqCreatePage selectReqCode(String s) throws InterruptedException{
		Select dropdown = new Select(reqCodeDropDown);
		dropdown.selectByVisibleText(s);		
		return this;
	}
	
	
	public NewReqCreatePage selectDivisionDropDown(String s) throws InterruptedException{
		Select dropdown = new Select(divisionDropDown);
		dropdown.deselectByValue(s);//	リレー生産技術課（morinaga@kumamoto.sanyu.co.jp）
		return this;
	}
	
	public NewReqCreatePage enterReqDate(String date) throws InterruptedException{
		reqDate.sendKeys(date);	
		return this;
	}
	
	public NewReqCreatePage enterCustomerField(String customer) throws InterruptedException{
		custId.sendKeys(customer);
		return this;		
	}
	
	public NewReqCreatePage enterReqContentField(String content) throws InterruptedException{
		reqContentField.sendKeys(content);
		return this;		
	}
	
	public NewReqCreatePage enterPurposeTextField(String text) throws InterruptedException{
		purposeTextField.sendKeys(text);
		return this;		
	}
		
	public NewReqCreatePage enterOrderQuantity(String orderQ) throws InterruptedException{
		orderQuantity.sendKeys(orderQ);
		return this;		
	}
	
	public NewReqCreatePage enterOrderAmount(String orderA) throws InterruptedException{
		orderAmount.sendKeys(orderA);
		return this;		
	}
	
	public NewReqCreatePage acceptPopUpDialog() throws InterruptedException{
		popUpOkButton.click();
		return this;
	}
	
	public NewReqCreatePage enterExpDeliveryDate(String expDelivDate) throws InterruptedException{
		expectedDeliveryDate.sendKeys(expDelivDate);
		return this;		
	}
		
	public NewReqCreatePage enterExpDevCoexpectedst(String expDevCoex) throws InterruptedException{
		expectedDevCost.sendKeys(expDevCoex);
		return this;		
	}
	
	public NewReqCreatePage enterExpUnitCost(String expUnitCost) throws InterruptedException{
		expectedUnitCost.sendKeys(expUnitCost);
		return this;		
	}
	
	public NewReqCreatePage enterExpEstimationDate(String expEstimationDate) throws InterruptedException{
		expectedEstimationDate.sendKeys(expEstimationDate);
		return this;		
	}
		

	public NewReqCreatePage clickSaveButton() throws InterruptedException{
		saveButton.click();
		return this;
	}
	
	public EditRequestPage acceptSubmissionCompletePopUp() throws InterruptedException{
		Alert alert = driver.switchTo().alert();
		alert.accept();
		return PageFactory.initElements(driver, EditRequestPage.class);
	}
	

}
