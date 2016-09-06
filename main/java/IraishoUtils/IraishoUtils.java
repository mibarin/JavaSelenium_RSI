package IraishoUtils;

import iraisho.kaihatsuhyoka.DataUtil;
import iraisho.kaihatsuhyoka.DateUtil;
import iraisho.kaihatsuhyoka.EditRequestPage;
import iraisho.kaihatsuhyoka.IraishoLoginPage;
import iraisho.kaihatsuhyoka.NewReqCreatePage;
import iraisho.kaihatsuhyoka.UserDashBoard;
import iraisho.kaihatsuhyoka.WorkBoard;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;


public class IraishoUtils {
	
	private WebDriver driver;
	private IraishoLoginPage loginPage;		
	private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	private Date date = new Date();
	private String requestNo;	

	/*
	 * User 2005 creates a request and submit it.
	 */
	

	public void setUp() {
		driver = new FirefoxDriver();
//		System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Java\\jdk1.8.0_40\\chromedriver.exe");
//		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.get("https://10.6.2.180:8181/rsi-bpm/");
		setLoginPage();
	}
	
	public void tearDown(){
		driver.quit();
	}
	

	public void signOut(){
		driver.findElement(By.linkText("Sign out")).click();
	}
	
	private void setLoginPage(){
		loginPage = PageFactory.initElements(driver, IraishoLoginPage.class);
	}
	
	public void newReqSubmissionWithMinEntry(String userName, String password, String title, String code, String dept)throws InterruptedException
	{
        UserDashBoard dashBoard = loginPage.doLogIn(userName, password);
        NewReqCreatePage newReqPage = dashBoard.goToNewReqCreatePage();
        newReqPage.enterReqTitle(title);
        newReqPage.selectReqType("開発品");
        newReqPage.selectReqCode(code);
        newReqPage.selectDivisionDropDown(dept);
        
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
        EditRequestPage submitRequest = newReqPage.acceptSubmissionCompletePopUp();
        //store request number
        requestNo = submitRequest.getReqID(); 
        submitRequest.clickSubmit();
        Assert.assertTrue(acceptSubmissionPopups(submitRequest));	
	}
	
	//claim
	public void newClaimSubmission(String userName, String password, String filePath, String tabName)throws Exception
	{
        UserDashBoard dashBoard = loginPage.doLogIn(userName, password, "クレーム処理");
        String [][] testInput = DataUtil.getTableArray(filePath, tabName, "StartData", "EndData");
        NewReqCreatePage newReqPage = dashBoard.goToNewReqCreatePage();
        newReqPage.enterReqTitle(testInput[0][1]);
        newReqPage.selectReqCode(testInput[1][1]);
        newReqPage.selectDivisionDropDown(testInput[2][1]);

        //current date
        newReqPage.enterReqDate(dateFormat.format(DateUtil.addDays(date, Integer.valueOf(testInput[3][1])))); 
        newReqPage.enterCustomerField(testInput[4][1]);
        newReqPage.enterPurposeTextField(testInput[5][1]);
        newReqPage.enterClaimProductName(testInput[6][1]);
        newReqPage.enterClaimLot(testInput[7][1]);
        newReqPage.enterClaimQuantity(testInput[8][1]);
        newReqPage.enterReqContentField(testInput[9][1]);
        newReqPage.enterExpEstimationDate(dateFormat.format(DateUtil.addDays(date, Integer.valueOf(testInput[10][1]))));
        newReqPage.clickSaveButton();
        newReqPage.acceptPopUpDialog();      
        //go to 依頼編集
        EditRequestPage submitClaim = newReqPage.acceptSubmissionCompletePopUp();
        //store request number
        requestNo = submitClaim.getReqID(); 
        submitClaim.clickSubmit();
        Assert.assertTrue(acceptSubmissionPopups(submitClaim));	
	}

	
	/*
	 * User  creates acknowledge the request.
	 */
	public void acknowledgeRequest(String userName, String password) throws InterruptedException{
		UserDashBoard dashBoard = loginPage.doLogIn(userName, password);        
		//ボードの作業待ちの依頼書ランの一番初めに上記の依頼書番号があること。
		Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
		WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	//依頼書番号をクリック	
		EditRequestPage editRequestPage = workBoard.clickRequestNo(requestNo);//さぎょうNoをクリックする。	
		editRequestPage.enterReqApprovalDate(dateFormat.format(DateUtil.addDays(date, 15)));		//提出確認日に入力
		editRequestPage.clickReqApprovalButton();//依頼書承認ボタンをクリック		
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));	
	}
	
	//acknowledge claim
	public void acknowledgeClaim(String userName, String password) throws InterruptedException{
		UserDashBoard dashBoard = loginPage.doLogIn(userName, password, "クレーム処理");       
		//ボードの作業待ちの依頼書ランの一番初めに上記の依頼書番号があること。
		Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
		WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	//依頼書番号をクリック	
		EditRequestPage editRequestPage = workBoard.clickClaimNo(requestNo);//さぎょうNoをクリックする。	
		editRequestPage.enterReqApprovalDate(dateFormat.format(DateUtil.addDays(date, 15)));		//提出確認日に入力
		editRequestPage.clickReqApprovalButton();//依頼書承認ボタンをクリック		
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));	
	}
	
	
	public void acknowledgeRequestWithChangeDept(String userName, String password, String divisionCode) throws InterruptedException{
		
		UserDashBoard dashBoard = loginPage.doLogIn(userName, password);        
		//ボードの作業待ちの依頼書ランの一番初めに上記の依頼書番号があること。
		Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
		WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	//依頼書番号をクリック	
		EditRequestPage editRequestPage = workBoard.clickRequestNo(requestNo);//さぎょうNoをクリックする。	
		editRequestPage.enterReqApprovalDate(dateFormat.format(DateUtil.addDays(date, 15)));		//提出確認日に入力
		editRequestPage.selectDivisionDropDown(divisionCode);
		editRequestPage.clickReqApprovalButton();//依頼書承認ボタンをクリック		
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));	
  
	}

	
	public void requestConfirmation(String userName, String password) throws InterruptedException{
		UserDashBoard dashBoard = loginPage.doLogIn(userName, password);

		//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
		Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
		WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
		//ワークで履歴が開く。依頼書Noをクリック。
		EditRequestPage editRequestPage = workBoard.clickRequestNo(requestNo);	
		//依頼事項確認に入力。
		editRequestPage.enterReqConfInfo("あいうえお");
		editRequestPage.clickReqConfButton();
		//依頼確認をクリック。「依頼確認しますか？」でOK。
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));		
	}
	
	
	//claim
	public void claimRequestConfirmation(String userName, String password, String filePath, String tabName) throws Exception{

		UserDashBoard dashBoard = loginPage.doLogIn(userName, password, "クレーム処理");
        String [][] testInput = DataUtil.getTableArray(filePath, tabName, "StartData", "EndData");

		//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
		Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
		WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
		//ワークで履歴が開く。依頼書Noをクリック。
		EditRequestPage editRequestPage = workBoard.clickClaimNo(requestNo);	
		//依頼事項確認に入力。
		editRequestPage.enterReqConfInfo(testInput[0][1]);
		editRequestPage.clickReqConfButton();
		//依頼確認をクリック。「依頼確認しますか？」でOK。
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));		
	}
	
	public void requestConfirmationReject(String userName, String password, String businessType, String reason) throws InterruptedException{
		UserDashBoard dashBoard = loginPage.doLogInWithPopUp(userName, password, businessType);

		//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
		Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
		WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
		//ワークで履歴が開く。依頼書Noをクリック。
		EditRequestPage editRequestPage = workBoard.clickRequestNo(requestNo);	
		//依頼事項確認に入力。
		editRequestPage.enterReqConfInfo("あいうえお");

		editRequestPage.enterRequestRemandReason(reason);
		editRequestPage.clickReqRejectButton();           	
		//依頼確認をクリック。「依頼確認しますか？」でOK。
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));	
	}
	
	
	

	public void requestConfirmation(String userName, String password, String businessType) throws InterruptedException {	
		UserDashBoard dashBoard = loginPage.doLogInWithPopUp(userName, password, businessType);

		//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
		Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
		WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
		//ワークで履歴が開く。依頼書Noをクリック。
		EditRequestPage editRequestPage = workBoard.clickRequestNo(requestNo);	
		//依頼事項確認に入力。
		editRequestPage.enterReqConfInfo("あいうえお");
		editRequestPage.clickReqConfButton();
		//依頼確認をクリック。「依頼確認しますか？」でOK。
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));	
	}
	
	public void requestConfirmationReject(String userName, String password, String reason) throws InterruptedException {	
		UserDashBoard dashBoard = loginPage.doLogIn(userName, password);

		//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
		Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
		WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
		//ワークで履歴が開く。依頼書Noをクリック。
		EditRequestPage editRequestPage = workBoard.clickRequestNo(requestNo);	
		//依頼事項確認に入力。
		editRequestPage.enterReqConfInfo("あいうえお");
		editRequestPage.enterRequestRemandReason(reason);
		editRequestPage.clickReqRejectButton();    
		//依頼確認をクリック。「依頼確認しますか？」でOK。
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));	
	}
	


	public void acknowledgeRequestConfirmation(String userName, String password) throws InterruptedException{

		UserDashBoard dashBoard = loginPage.doLogIn(userName, password);
		Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
		//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
		WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
		//ワークで履歴が開く。依頼書Noをクリック。
		EditRequestPage editRequestPage = workBoard.clickRequestNo(requestNo);
		//依頼確認了承ボタンをクリック。。「依頼確認了承しますか？」でOK。
		editRequestPage.clickAcknwlgReqConfButton();
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));	
	}
	
	//claim
	public void acknowledgeClaimRequestConfirmation(String userName, String password) throws InterruptedException{

		UserDashBoard dashBoard = loginPage.doLogIn(userName, password,"クレーム処理");
		Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
		//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
		WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
		//ワークで履歴が開く。依頼書Noをクリック。
		EditRequestPage editRequestPage = workBoard.clickClaimNo(requestNo);
		//依頼確認了承ボタンをクリック。。「依頼確認了承しますか？」でOK。
		editRequestPage.clickAcknwlgReqConfButton();
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));	
	}


	public void acceptRequest(String userName, String password, String chargeId) throws InterruptedException {

		UserDashBoard dashBoard = loginPage.doLogIn(userName, password);
		Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
		//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
		WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
		//ワークで履歴が開く。依頼書Noをクリック。
		EditRequestPage editRequestPage = workBoard.clickRequestNo(requestNo);
		//担当者回答期限, 作業担当:1043-0, 担当部署受理日>提出承認日
		editRequestPage.enterReqAcceptDate(dateFormat.format(DateUtil.addDays(date, 15)));
		editRequestPage.editChargeIdField(chargeId);
		editRequestPage.enterDueResponseDate(dateFormat.format(DateUtil.addDays(date, 15)));
		//主要項目が現れる。依頼受理をクリック。
		editRequestPage.clickAcceptRequestButton();
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));	
	}
	
	public void acceptClaimRequest(String userName, String password, String chargeId) throws InterruptedException {

		UserDashBoard dashBoard = loginPage.doLogIn(userName, password, "クレーム処理");
		Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
		//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
		WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
		//ワークで履歴が開く。依頼書Noをクリック。
		EditRequestPage editRequestPage = workBoard.clickClaimNo(requestNo);
		//担当者回答期限, 作業担当:1043-0, 担当部署受理日>提出承認日
		editRequestPage.enterReqAcceptDate(dateFormat.format(DateUtil.addDays(date, 15)));
		editRequestPage.editChargeIdField(chargeId);
		editRequestPage.enterDueResponseDate(dateFormat.format(DateUtil.addDays(date, 15)));
		//主要項目が現れる。依頼受理をクリック。
		editRequestPage.clickAcceptRequestButton();
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));	
	}
	
	public void acceptRequestGenerateAndSubmitReply(String userName, String password, String chargeId) throws InterruptedException {

		UserDashBoard dashBoard = loginPage.doLogIn(userName, password);
		
		Assert.assertTrue (dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
		//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
		WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
		//ワークで履歴が開く。依頼書Noをクリック。
		EditRequestPage editRequestPage = workBoard.clickRequestNo(requestNo);
		//担当者回答期限, 作業担当:1043-0, 担当部署受理日>提出承認日
		editRequestPage.enterReqAcceptDate(dateFormat.format(DateUtil.addDays(date, 15)));
		editRequestPage.editChargeIdField(chargeId);
		editRequestPage.enterDueResponseDate(dateFormat.format(DateUtil.addDays(date, 15)));
		//主要項目が現れる。依頼受理をクリック。
		editRequestPage.clickAcceptRequestButton();
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));	
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		editRequestPage.clickGenerateReplyButton();
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));	       	//回答内容, 回答提出日(>担当部署受理日) 入力
		editRequestPage.editRespContentTextArea("あいうえお");
		editRequestPage.enterRespDate(dateFormat.format(DateUtil.addDays(date, 15)));
		//回答提出をクリック。
		editRequestPage.clickSubmitRespButton();
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));

	}


	public void generateReply(String userName, String password) throws InterruptedException {

		UserDashBoard dashBoard = loginPage.doLogIn(userName, password);

		Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
		//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
		WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
		//ワークで履歴が開く。依頼書Noをクリック。
		EditRequestPage editRequestPage = workBoard.clickRequestNo(requestNo);
		//回答生成をクリック
		editRequestPage.clickGenerateReplyButton();
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));	
	}
	
	//claim
	public void generateClaimReply(String userName, String password) throws InterruptedException {

		UserDashBoard dashBoard = loginPage.doLogIn(userName, password, "クレーム処理");

		Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
		//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
		WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
		//ワークで履歴が開く。依頼書Noをクリック。
		EditRequestPage editRequestPage = workBoard.clickClaimNo(requestNo);
		//回答生成をクリック
		editRequestPage.clickGenerateReplyButton();
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));	
	}

	
	

	public void submitReply(String userName, String password) throws InterruptedException {

		UserDashBoard dashBoard = loginPage.doLogIn(userName, password);
		
		Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
		//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
		WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
		//ワークで履歴が開く。依頼書Noをクリック。
		EditRequestPage editRequestPage = workBoard.clickRequestNo(requestNo);
		//回答内容, 回答提出日(>担当部署受理日) 入力
		editRequestPage.editRespContentTextArea("あいうえお");
		editRequestPage.enterRespDate(dateFormat.format(DateUtil.addDays(date, 15)));
		//回答提出をクリック。
		editRequestPage.clickSubmitRespButton();
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));
	}
	
	public void submitClaimReply(String userName, String password, String filePath, String tabName) throws Exception {

		UserDashBoard dashBoard = loginPage.doLogIn(userName, password, "クレーム処理");
		String [][] testInput = DataUtil.getTableArray(filePath, tabName, "StartData", "EndData");
		
		Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
		//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
		WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
		//ワークで履歴が開く。依頼書Noをクリック。
		EditRequestPage editRequestPage = workBoard.clickClaimNo(requestNo);
		//回答内容, 回答提出日(>担当部署受理日) 入力
		editRequestPage.editRespContentTextArea(testInput[0][1]);
		editRequestPage.enterRespDate(dateFormat.format(DateUtil.addDays(date, Integer.valueOf(testInput[1][1]))));		
		editRequestPage.selectTypeOfCause(testInput[2][1]);
		editRequestPage.enterTotalTestedQuantity(testInput[3][1]);
		editRequestPage.enterLifeIssue(testInput[4][1]);
		editRequestPage.enterUnsuitableIssue(testInput[5][1]);
		editRequestPage.enterInsulationIssue(testInput[6][1]);
		editRequestPage.enterOtherAssyIssue(testInput[7][1]);
		editRequestPage.enterFerriticIssue(testInput[8][1]);
		editRequestPage.enterNoIssue(testInput[9][1]);
		editRequestPage.enterDissolutionIssue(testInput[10][1]);
		editRequestPage.enterBreakLineIssue(testInput[11][1]);
		editRequestPage.enterSolderIssue(testInput[12][1]);
		editRequestPage.enterADTLeakIssue(testInput[13][1]);
		editRequestPage.enterUnknownIssue(testInput[14][1]);
		//回答提出をクリック。
		editRequestPage.clickSubmitRespButton();
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));
	}
	

	public void acknowledgeResp(String userName, String password) throws InterruptedException {

		UserDashBoard dashBoard = loginPage.doLogIn(userName, password);

		Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
		//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
		WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
		//ワークで履歴が開く。依頼書Noをクリック。
		EditRequestPage editRequestPage = workBoard.clickRequestNo(requestNo);
		//担当部署回答承認日
		editRequestPage.enterRespApprovalDate(dateFormat.format(DateUtil.addDays(date, 15)));
		//回答承認をクリック。
		editRequestPage.clickRespApprovalButton();
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));

	}
	
	public void acknowledgeClaimResp(String userName, String password) throws InterruptedException {

		UserDashBoard dashBoard = loginPage.doLogIn(userName, password, "クレーム処理");

		Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
		//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
		WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
		//ワークで履歴が開く。依頼書Noをクリック。
		EditRequestPage editRequestPage = workBoard.clickClaimNo(requestNo);
		//担当部署回答承認日
		editRequestPage.enterRespApprovalDate(dateFormat.format(DateUtil.addDays(date, 15)));
		//回答承認をクリック。
		editRequestPage.clickRespApprovalButton();
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));

	}
	
	public void acknowledgeClaimResp(String userName, String password, String filePath, String tabName) throws Exception {

		UserDashBoard dashBoard = loginPage.doLogIn(userName, password, "クレーム処理");
		String [][] testInput = DataUtil.getTableArray(filePath, tabName, "StartData", "EndData");

		Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
		//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
		WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
		//ワークで履歴が開く。依頼書Noをクリック。
		EditRequestPage editRequestPage = workBoard.clickClaimNo(requestNo);
		//担当部署回答承認日
		editRequestPage.enterRespApprovalDate(dateFormat.format(DateUtil.addDays(date, Integer.valueOf(testInput[0][1]))));
		//回答承認をクリック。
		editRequestPage.clickRespApprovalButton();
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));

	}
	


	public void acceptResp(String userName, String password) throws InterruptedException {

		UserDashBoard dashBoard = loginPage.doLogIn(userName, password);

		Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
		//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
		WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
		//ワークで履歴が開く。依頼書Noをクリック。
		EditRequestPage editRequestPage = workBoard.clickRequestNo(requestNo);
		//依頼回答受理日
		editRequestPage.enterRespAcceptDate(dateFormat.format(DateUtil.addDays(date, 15)));
		//回答受理をクリック。
		editRequestPage.clickRespAcceptButton();
		//回答受理しますか？ok
		//回答受理完了 
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));

	}
	
	public void acceptClaimResp(String userName, String password, String filePath, String tabName) throws Exception {

		UserDashBoard dashBoard = loginPage.doLogIn(userName, password, "クレーム処理");
		String [][] testInput = DataUtil.getTableArray(filePath, tabName, "StartData", "EndData");

		Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
		//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
		WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
		//ワークで履歴が開く。依頼書Noをクリック。
		EditRequestPage editRequestPage = workBoard.clickClaimNo(requestNo);
		//回答受理日
		editRequestPage.enterRespAcceptDate(dateFormat.format(DateUtil.addDays(date, Integer.valueOf(testInput[0][1]))));
		//回答受理をクリック。
		editRequestPage.clickSubmitRespButton();
		//回答受理しますか？ok
		//回答受理完了 
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));
	}
	
	public void acceptClaimResp(String userName, String password) throws InterruptedException {

		UserDashBoard dashBoard = loginPage.doLogIn(userName, password, "クレーム処理");

		Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
		//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
		WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
		//ワークで履歴が開く。依頼書Noをクリック。
		EditRequestPage editRequestPage = workBoard.clickClaimNo(requestNo);
		//回答受理日
		editRequestPage.enterRespAcceptDate(dateFormat.format(DateUtil.addDays(date, 15)));
		//回答受理をクリック。
		editRequestPage.clickRespAcceptButton();
		//回答受理しますか？ok
		//回答受理完了 
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));

	}
	

	public void generateResultReport(String userName, String password) throws InterruptedException {	

        UserDashBoard dashBoard = loginPage.doLogIn(userName, password);
        
        Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
        //ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
        WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
        //ワークで履歴が開く。依頼書Noをクリック。
        EditRequestPage editRequestPage = workBoard.clickRequestNo(requestNo);
        //結果
        editRequestPage.selectResultType("受注");
        //結果受注数量
        editRequestPage.enterResultOrderQuantity("55555");
        //結果受注金額
        editRequestPage.enterResultOrderAmount("555555");
        //結果出荷納期
        editRequestPage.enterResultDeliveryDate(dateFormat.format(DateUtil.addDays(date, 21)));
        //結果開発費
        editRequestPage.enterResultDevCost("5555555");
        //結果単価
        editRequestPage.enterResultUnitCost("55");
        //結果報告内容
        editRequestPage.enterResultContent("あいうえお");
        //結果報告作成をクリック。
        editRequestPage.clickGenerateResultReportButton();
        //結果報告作成しますか？OK.
        //結果報告作成完了: A01E-000115ok.
        Assert.assertTrue(acceptSubmissionPopups(editRequestPage));	
	}
	
	//claim
	public void generateClaimResultReport(String userName, String password) throws InterruptedException {	

        UserDashBoard dashBoard = loginPage.doLogIn(userName, password, "クレーム処理");
        
        Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
        //ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
        WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
        //ワークで履歴が開く。依頼書Noをクリック。
        EditRequestPage editRequestPage = workBoard.clickClaimNo(requestNo);
        //結果報告内容
        editRequestPage.enterResultContent("あいうえお");
        //結果報告作成をクリック。
        editRequestPage.clickGenerateResultReportButton();
        //結果報告作成しますか？OK.
        //結果報告作成完了: A01E-000115ok.
        Assert.assertTrue(acceptSubmissionPopups(editRequestPage));	
	}
	
	public void generateClaimResultReport(String userName, String password, String filePath, String tabName) throws Exception {	

        UserDashBoard dashBoard = loginPage.doLogIn(userName, password, "クレーム処理");
		String [][] testInput = DataUtil.getTableArray(filePath, tabName, "StartData", "EndData");
        
        Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
        //ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
        WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
        //ワークで履歴が開く。依頼書Noをクリック。
        EditRequestPage editRequestPage = workBoard.clickClaimNo(requestNo);
        //結果報告内容
        editRequestPage.enterResultContent(testInput[0][1]);
        //結果報告作成をクリック。
        editRequestPage.clickGenerateResultReportButton();
        //結果報告作成しますか？OK.
        //結果報告作成完了: A01E-000115ok.
        Assert.assertTrue(acceptSubmissionPopups(editRequestPage));	
	}
	


	public void submitResultReport(String userName, String password) throws InterruptedException {	

		UserDashBoard dashBoard = loginPage.doLogIn(userName, password);

		Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
		//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
		WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
		//ワークで履歴が開く。依頼書Noをクリック。
		EditRequestPage editRequestPage = workBoard.clickRequestNo(requestNo);
		//結果報告日
		editRequestPage.enterResultDate(dateFormat.format(DateUtil.addDays(date, 21)));

		//結果報告提出ボタンをクリック。
		editRequestPage.clickResultReportButton();
		//結果報告提出しますか？ok.
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));		
	}	
	
	
	//claim
	public void submitClaimResultReport(String userName, String password) throws InterruptedException {	

		UserDashBoard dashBoard = loginPage.doLogIn(userName, password, "クレーム処理");

		Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
		//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
		WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
		//ワークで履歴が開く。依頼書Noをクリック。
		EditRequestPage editRequestPage = workBoard.clickClaimNo(requestNo);
		//結果報告日
		editRequestPage.enterResultDate(dateFormat.format(DateUtil.addDays(date, 21)));

		//結果報告提出ボタンをクリック。
		editRequestPage.clickResultReportButton();
		//結果報告提出しますか？ok.
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));		
	}
	
	public void submitClaimResultReport(String userName, String password, String filePath, String tabName) throws Exception {	

		UserDashBoard dashBoard = loginPage.doLogIn(userName, password, "クレーム処理");
		String [][] testInput = DataUtil.getTableArray(filePath, tabName, "StartData", "EndData");

		Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
		//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
		WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
		//ワークで履歴が開く。依頼書Noをクリック。
		EditRequestPage editRequestPage = workBoard.clickClaimNo(requestNo);
		//結果報告日
		editRequestPage.enterResultDate(dateFormat.format(DateUtil.addDays(date, Integer.valueOf(testInput[0][1]))));
		//結果報告提出ボタンをクリック。
		editRequestPage.clickResultReportButton();
		//結果報告提出しますか？ok.
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));		
	}	
	

	public void approveResultReport(String userName, String password) throws InterruptedException {	

		UserDashBoard dashBoard = loginPage.doLogIn(userName, password);

		Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
		//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
		WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
		//ワークで履歴が開く。依頼書Noをクリック。
		EditRequestPage editRequestPage = workBoard.clickRequestNo(requestNo);
		//結果報告承認日
		editRequestPage.enterResultApprovalDate(dateFormat.format(DateUtil.addDays(date, 21)));

		//結果報告承認ボタン
		editRequestPage.clickResultApprovalButton();
		//結果報告承認しますか？ ok
		//結果報告承認完了: A01E-000115 ok
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));	
	}	
	
	//claim
	public void approveClaimResultReport(String userName, String password) throws InterruptedException {	

		UserDashBoard dashBoard = loginPage.doLogIn(userName, password, "クレーム処理");

		Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
		//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
		WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
		//ワークで履歴が開く。依頼書Noをクリック。
		EditRequestPage editRequestPage = workBoard.clickClaimNo(requestNo);
		//結果報告承認日
		editRequestPage.enterResultApprovalDate(dateFormat.format(DateUtil.addDays(date, 21)));

		//結果報告承認ボタン
		editRequestPage.clickResultApprovalButton();
		//結果報告承認しますか？ ok
		//結果報告承認完了
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));	
	}	
	
	public void approveClaimResultReport(String userName, String password,String filePath, String tabName) throws Exception {	

		UserDashBoard dashBoard = loginPage.doLogIn(userName, password, "クレーム処理");
		String [][] testInput = DataUtil.getTableArray(filePath, tabName, "StartData", "EndData");

		Assert.assertTrue(dashBoard.getFirstReqNoOnMyTurn().equals(requestNo));
		//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
		WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
		//ワークで履歴が開く。依頼書Noをクリック。
		EditRequestPage editRequestPage = workBoard.clickClaimNo(requestNo);
		//結果報告承認日
		editRequestPage.enterResultApprovalDate(dateFormat.format(DateUtil.addDays(date, Integer.valueOf(testInput[0][1]))));

		//結果報告承認ボタン
		editRequestPage.clickResultApprovalButton();
		//結果報告承認しますか？ ok
		//結果報告承認完了
		Assert.assertTrue(acceptSubmissionPopups(editRequestPage));	
	}	


	public void confirmIfLocatedInCompleted(String userName, String password) throws InterruptedException {	

		UserDashBoard dashBoard = loginPage.doLogIn(userName, password);    	
		//ボードの完了済みの依頼書にリストされる。
		dashBoard.assertReportNumEquals("COMPLETED", requestNo);
	}
	
	public void confirmIfLocatedInCompleted(UserDashBoard userDashBoard) throws InterruptedException {	
	
		//ボードの完了済みの依頼書にリストされる。
		userDashBoard.assertReportNumEquals("COMPLETED", requestNo);
	}
	
	public WebDriver getDriver(){
		return driver;
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
