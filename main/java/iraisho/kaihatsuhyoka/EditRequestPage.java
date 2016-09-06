package iraisho.kaihatsuhyoka;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class EditRequestPage {
	
	private WebDriver driver;
	
	@FindBy(css = ".form_command-ACTION_RP_REMAND>input")
	private WebElement requestRemandButton;
		
	@FindBy(name = "remand_reason")
	private WebElement remandReasonTextArea;
	
	@FindBy(name = "req_content")
	private WebElement reqContentTextArea;
	
	@FindBy(css = ".form_command-ACTION_RC_INSPECT > input:nth-child(1)")
	private WebElement requestSubmitButton;
	
	@FindBy(css =".form_command-ACTION_RC_DELETE>input")
	private WebElement requestDeleteButton;
	
	@FindBy(how=How.XPATH, using = ".//*[@id='top-controls']/div[1]/img")
	private WebElement dashBoardButton;
	
	@FindBy(css = ".form_command-ACTION_RC_UPDATE>input")
	private WebElement requestUpdateButton;
	
	@FindBy(id = "popup_ok")
	private WebElement clickOkOnPopUpButton;
	
	@FindBy(name = "req_approval_date")
	private WebElement reqApprovalDateField;
	
	@FindBy(css = ".form_command-ACTION_RS_APPROVE>input")
	private WebElement reqApprovalButton;
	
	@FindBy(name = "req_confirmation")
	private WebElement reqConfTextArea;
	
	@FindBy(name = "charged_division_id")
	private WebElement divisionDropDown;
	
	@FindBy(css = ".form_command-ACTION_RP_VERIFY>input")
	private WebElement reqConfButton;
	
	@FindBy(css = ".form_command-ACTION_RV_CONFIRM>input")
	private WebElement acknowledgeReqConfButton;
	
	@FindBy(css = ".form_command-ACTION_RF_ACCEPT>input")
	private WebElement acceptRequestButton;
	
	@FindBy(name = "due_response_date")
	private WebElement dueResponseDate;
	
	@FindBy(name = "charge_id")
	private WebElement chargeIDField;
	
	@FindBy(name = "req_accept_date")
	private WebElement reqAcceptDate;
	
	@FindBy(css = ".form_command-ACTION_RA_REPORT>input")
	private WebElement generateReplyButton;
	
	@FindBy(name = "resp_content")
	private WebElement respContentTextArea;
	
	@FindBy(name = "resp_date")
	private WebElement respDate;
	
	@FindBy(css = ".form_command-ACTION_PC_INSPECT>input")
	private WebElement submitRespButton;
	
	@FindBy(name = "resp_approval_date")
	private WebElement respApprovalDate;
	
	@FindBy(css = ".form_command-ACTION_PS_APPROVE > input")
	private WebElement respApprovalButton;
	
	@FindBy(name = "resp_accept_date")
	private WebElement respAcceptDate;
	
	@FindBy(css = ".form_command-ACTION_PP_ACCEPT>input")
	private WebElement respAcceptButton;
	
	@FindBy(css = ".form_command-ACTION_PA_REPORT>input")
	private WebElement generateResultReportButton;
	
	@FindBy(name = "result_type")
	private WebElement resultTypeDropDown;
	
	@FindBy(name = "result_order_quantity")
	private WebElement resultOrderQuantity;
	
	@FindBy(name = "result_delivery_date")
	private WebElement resultDeliveryDate;
	
	@FindBy(name = "result_dev_cost")
	private WebElement resultDevCost;
	
	@FindBy(name = "result_order_amount")
	private WebElement resultOrderAmount;
	
	@FindBy(name = "result_unit_cost")
	private WebElement resultUnitCost;
	
	@FindBy(name = "result_content")
	private WebElement resultContentTextArea;
	
	@FindBy(name = "result_date")
	private WebElement resultDate;
	
	@FindBy(css = ".form_command-ACTION_SC_INSPECT>input")
	private WebElement resultReportButton;
	
	@FindBy(name = "result_approval_date")
	private WebElement resultApprovalDate;
	
	@FindBy(css = ".form_command-ACTION_SS_APPROVE>input")
	private WebElement resultApprovalButton;
	
	@FindBy(name = "req_id")
	private WebElement reqID;
	
	//claim
	@FindBy(name = "type_of_cause")
	private WebElement typeOfCause;

	@FindBy(name = "total_tested_quantity")
	private WebElement totalTestedQuantity;
	
	@FindBy(name = "life_issue")
	private WebElement lifeIssue;
	
	@FindBy(name = "unsuitable_issue")
	private WebElement unsuitableIssue;
	
	@FindBy(name = "insulation_issue")
	private WebElement insulationIssue;
	
	@FindBy(name = "other_assy_issue")
	private WebElement otherAssyIssue;
	
	@FindBy(name = "ferritic_issue")
	private WebElement ferriticIssue;
	
	@FindBy(name = "no_issue")
	private WebElement noIssue;
	
	@FindBy(name = "dissolution_issue")
	private WebElement dissolutionIssue;
	
	@FindBy(name = "break_line_issue")
	private WebElement breakLineIssue;
	
	@FindBy(name = "solder_issue")
	private WebElement solderIssue;
	
	@FindBy(name = "ADT_leak_issue")
	private WebElement ADTLeakIssue;
	
	@FindBy(name = "unknown_issue")
	private WebElement unknownIssue;
	
	@FindBy(css = ".form_command-ACTION_SC_UPDATE>input")
	private WebElement claimResultSubmitButton;
	
	public EditRequestPage(WebDriver driver) {
		this.driver = driver;
	}
		
	public EditRequestPage enterReqApprovalDate(String date) throws InterruptedException{
		reqApprovalDateField.sendKeys(date);
		return this;		
	}
	
	public EditRequestPage enterReqConfInfo(String s) throws InterruptedException{
		reqConfTextArea.sendKeys(s);
		return this;
	}
	
	public EditRequestPage clickSubmit() throws InterruptedException{
		requestSubmitButton.click();
		return this;
	}
	
	public EditRequestPage clickOkOnPopUp() throws InterruptedException{
		clickOkOnPopUpButton.click();
		return this;
	}
	
	public EditRequestPage acceptSubmissionOkPopup() throws InterruptedException{
		Alert alert = driver.switchTo().alert();
		Assert.assertTrue(alert.getText().contains("完了"));
		alert.accept();
		return this;
	}
	
	public EditRequestPage clickReqApprovalButton() throws InterruptedException{
		reqApprovalButton.click();        
		return this;		
	}
	
	public EditRequestPage clickReqConfButton() throws InterruptedException{
		reqConfButton.click();
		return this;
	}	
	
	public EditRequestPage clickAcknwlgReqConfButton() throws InterruptedException{
		acknowledgeReqConfButton.click();
		return this;
	}
	
	public EditRequestPage editChargeIdField(String chargeID) throws InterruptedException{
		chargeIDField.sendKeys(chargeID);
		return this;
	}
	
	public EditRequestPage enterReqAcceptDate(String date) throws InterruptedException{
		reqAcceptDate.sendKeys(date);
		return this;
	}
	
	public EditRequestPage enterDueResponseDate(String date) throws InterruptedException{
		dueResponseDate.sendKeys(date);
		return this;
	}
	
	public EditRequestPage clickAcceptRequestButton() throws InterruptedException{
		acceptRequestButton.click();
		return this;
	}
	
	public EditRequestPage clickGenerateReplyButton() throws InterruptedException{
		generateReplyButton.click();
		return this;
	}
	
	public EditRequestPage editRespContentTextArea(String s) throws InterruptedException{
		respContentTextArea.sendKeys(s);
		return this;
	}
	
	public EditRequestPage enterRespDate(String date) throws InterruptedException{
		respDate.sendKeys(date);
		return this;
	}
	
	public EditRequestPage clickSubmitRespButton() throws InterruptedException{
		submitRespButton.click();
		return this;
	}
	
	public EditRequestPage enterRespApprovalDate(String date) throws InterruptedException{
		respApprovalDate.sendKeys(date);
		return this;		
	}
	
	public EditRequestPage clickRespApprovalButton() throws InterruptedException{
		respApprovalButton.click();
		return this;
	}	
	
	public EditRequestPage enterRespAcceptDate(String date) throws InterruptedException{
		respAcceptDate.sendKeys(date);
		return this;		
	}	
	
	public EditRequestPage clickRespAcceptButton() throws InterruptedException{
		respAcceptButton.click();
		return this;
	}	
	
	public EditRequestPage clickGenerateResultReportButton() throws InterruptedException{
		generateResultReportButton.click();
		return this;
	}
		
	public EditRequestPage selectResultType(String entry) throws InterruptedException{
		Select dropdown = new Select(resultTypeDropDown);
		dropdown.selectByVisibleText(entry);
		return this;
	}
	
	public EditRequestPage enterResultOrderQuantity(String quantity) throws InterruptedException{
		resultOrderQuantity.sendKeys(quantity);
		return this;
	}	
	
	public EditRequestPage enterResultDeliveryDate(String date) throws InterruptedException{
		resultDeliveryDate.sendKeys(date);
		return this;		
	}	
	
	public EditRequestPage enterResultDevCost(String cost) throws InterruptedException{
		resultDevCost.sendKeys(cost);
		return this;
	}
	
	public EditRequestPage enterResultOrderAmount(String amount) throws InterruptedException{
		resultOrderAmount.sendKeys(amount);
		return this;
	}
	
	public EditRequestPage enterResultUnitCost(String unitCost) throws InterruptedException{
		resultUnitCost.sendKeys(unitCost);
		return this;		
	}
	
	public EditRequestPage enterResultContent(String content) throws InterruptedException{
		resultContentTextArea.sendKeys(content);
		return this;
	}
	
	
	public EditRequestPage enterRequestContent(String content) throws InterruptedException{
		reqContentTextArea.sendKeys(content);
		return this;
	}
	
	public EditRequestPage enterResultDate(String date) throws InterruptedException{
		resultDate.sendKeys(date);
		return this;		
	}	
	
	public EditRequestPage clickResultReportButton() throws InterruptedException{
		resultReportButton.click();
		return this;
	}
	
	public EditRequestPage enterResultApprovalDate(String date) throws InterruptedException{
		resultApprovalDate.sendKeys(date);
		return this;		
	}	
	
	public EditRequestPage clickResultApprovalButton() throws InterruptedException{
		resultApprovalButton.click();
		return this;
	}		
	
	public String getReqID() throws InterruptedException{
		return reqID.getAttribute("value");
	}
	
	public EditRequestPage clickReqDeleteButton() throws InterruptedException{
		requestDeleteButton.click();
		return this;
	}	
	
	public UserDashBoard goToDashBoard() throws InterruptedException{
		dashBoardButton.click();
		return PageFactory.initElements(driver, UserDashBoard.class);
	}	
	
	public EditRequestPage clickReqUpdateButton() throws InterruptedException{
		requestUpdateButton.click();
		return this;
	}
	
	
	public EditRequestPage clickReqRejectButton() throws InterruptedException{
		requestRemandButton.click();
		return this;
	}

	public EditRequestPage enterRequestRemandReason(String reason) throws InterruptedException{
		remandReasonTextArea.sendKeys(reason);
		return this;		
	}	
	
	public EditRequestPage selectDivisionDropDown(String divisionCode) throws InterruptedException{
		Select dropdown = new Select(divisionDropDown);
		dropdown.selectByValue(divisionCode);
		return this;
	}
	
	//claim
	public EditRequestPage selectTypeOfCause(String causeType) throws InterruptedException{
		Select dropdown = new Select(typeOfCause);
		dropdown.selectByVisibleText(causeType);
		return this;
	}
	

	public EditRequestPage enterTotalTestedQuantity(String testedQuantity) throws InterruptedException{
		totalTestedQuantity.sendKeys(testedQuantity);
		return this;
	}
	
	public EditRequestPage enterLifeIssue(String num) throws InterruptedException{
		lifeIssue.sendKeys(num);
		return this;
	}
	
	public EditRequestPage enterUnsuitableIssue(String num) throws InterruptedException{
		unsuitableIssue.sendKeys(num);
		return this;
	}
	
	public EditRequestPage enterOtherAssyIssue(String num) throws InterruptedException{
		otherAssyIssue.sendKeys(num);
		return this;
	}
	
	public EditRequestPage enterInsulationIssue(String num) throws InterruptedException{
		insulationIssue.sendKeys(num);
		return this;
	}
	
	public EditRequestPage enterFerriticIssue(String num) throws InterruptedException{
		ferriticIssue.sendKeys(num);
		return this;
	}
	
	public EditRequestPage enterNoIssue(String num) throws InterruptedException{
		noIssue.sendKeys(num);
		return this;
	}
	
	public EditRequestPage enterDissolutionIssue(String num) throws InterruptedException{
		dissolutionIssue.sendKeys(num);
		return this;
	}
	
	public EditRequestPage enterBreakLineIssue(String num) throws InterruptedException{
		breakLineIssue.sendKeys(num);
		return this;
	}	
	
	
	public EditRequestPage enterSolderIssue(String num) throws InterruptedException{
		solderIssue.sendKeys(num);
		return this;
	}	
	
	
	public EditRequestPage enterADTLeakIssue(String num) throws InterruptedException{
		ADTLeakIssue.sendKeys(num);
		return this;
	}	
	
	
	public EditRequestPage enterUnknownIssue(String num) throws InterruptedException{
		unknownIssue.sendKeys(num);
		return this;
	}	
	
	public EditRequestPage clickClaimResultSubmitButton() throws InterruptedException{
		claimResultSubmitButton.click();
		return this;
	}

	
}
