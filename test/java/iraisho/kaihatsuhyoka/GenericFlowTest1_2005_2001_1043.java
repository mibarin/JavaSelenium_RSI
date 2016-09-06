package iraisho.kaihatsuhyoka;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*
 * This is user workflow using 2005 as a requester, 2001 is its boss and quality 
 * manager/quality team is 1043.  In all cases, the submissions are accepted.
 */
		

public class GenericFlowTest1_2005_2001_1043 {
	
	private WebDriver driver;
	private IraishoLoginPage loginPage;		
	private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	private Date date = new Date();
	private String requestNo;	
	
	@BeforeMethod
	public void setUp() {
		driver = new FirefoxDriver();
//		System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Java\\jdk1.8.0_40\\chromedriver.exe");
//		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.get("https://10.6.2.180:8181/rsi-bpm/");
		loginPage = PageFactory.initElements(driver, IraishoLoginPage.class);
	}

	/*
	 * User 2005 creates a request and submit it.
	 */
	@Test
	public void NewReqSubmission()throws InterruptedException
	{
        UserDashBoard dashBoard = loginPage.doLogIn("2005", "q1111111");
        NewReqCreatePage newReqPage = dashBoard.goToNewReqCreatePage();
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
        EditRequestPage submitRequest = newReqPage.acceptSubmissionCompletePopUp();
        //store request number
        requestNo = submitRequest.getReqID(); 
        submitRequest.clickSubmit();
        Assert.assertTrue(acceptSubmissionPopups(submitRequest));	
	}
	
	/*
	 * User 2001 creates acknowledge the request.
	 */
	@Test(dependsOnMethods={"NewReqSubmission"})
	public void acknowledgeRequest() throws InterruptedException{
		
		//2001でログイン
        UserDashBoard dashBoard = loginPage.doLogIn("2001", "q1111111");        
		//ボードの作業待ちの依頼書ランの一番初めに上記の依頼書番号があること。
        if (dashBoard.getFirstReqNoOnMyTurn().equals(requestNo)){
        	WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	//依頼書番号をクリック	
            EditRequestPage editRequestPage = workBoard.clickRequestNo(requestNo);//さぎょうNoをクリックする。	
            editRequestPage.enterReqApprovalDate(dateFormat.format(DateUtil.addDays(date, 15)));		//提出確認日に入力
		
            editRequestPage.clickReqApprovalButton();//依頼書承認ボタンをクリック		
            Assert.assertTrue(acceptSubmissionPopups(editRequestPage));	
        }else {
        	Assert.assertTrue(false);
        }
	}
	
	@Test(dependsOnMethods={"acknowledgeRequest"})
	public void requestConfirmation() throws InterruptedException {
	
	//1043、zdDnf86Pでログイン
        UserDashBoard dashBoard = loginPage.doLogIn("1043", "");
	//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていzdDnf86Pて、クリック。
        if (dashBoard.getFirstReqNoOnMyTurn().equals(requestNo)){
        	WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
        	//ワークで履歴が開く。依頼書Noをクリック。
        	EditRequestPage editRequestPage = workBoard.clickRequestNo(requestNo);	
        	//依頼事項確認に入力。
        	editRequestPage.enterReqConfInfo("あいうえお");
        	//依頼確認をクリック。「依頼確認しますか？」でOK。
        	editRequestPage.clickReqConfButton();
        	Assert.assertTrue(acceptSubmissionPopups(editRequestPage));	
        }else {
        	Assert.assertTrue(false);
        }
	}
	

	@Test(dependsOnMethods={"requestConfirmation"})
	public void acknowledgeRequestConfirmation() throws InterruptedException{

        UserDashBoard dashBoard = loginPage.doLogIn("2001", "q1111111");
        if (dashBoard.getFirstReqNoOnMyTurn().equals(requestNo)){
        	//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
        	WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
        	//ワークで履歴が開く。依頼書Noをクリック。
        	EditRequestPage editRequestPage = workBoard.clickRequestNo(requestNo);
        	//依頼確認了承ボタンをクリック。。「依頼確認了承しますか？」でOK。
        	editRequestPage.clickAcknwlgReqConfButton();
        	Assert.assertTrue(acceptSubmissionPopups(editRequestPage));	
        }else {
        	Assert.assertTrue(false);
        }
	}
	

	@Test(dependsOnMethods={"acknowledgeRequestConfirmation"})
	public void acceptRequest() throws InterruptedException {

        UserDashBoard dashBoard = loginPage.doLogIn("1043", "zdDnf86P");
        if (dashBoard.getFirstReqNoOnMyTurn().equals(requestNo)){
        	//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
        	WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
        	//ワークで履歴が開く。依頼書Noをクリック。
        	EditRequestPage editRequestPage = workBoard.clickRequestNo(requestNo);
        	//担当者回答期限, 作業担当:1043-0, 担当部署受理日>提出承認日
        	editRequestPage.enterReqAcceptDate(dateFormat.format(DateUtil.addDays(date, 15)));
        	editRequestPage.editChargeIdField("1043-0");
        	editRequestPage.enterDueResponseDate(dateFormat.format(DateUtil.addDays(date, 15)));
        	//主要項目が現れる。依頼受理をクリック。
        	editRequestPage.clickAcceptRequestButton();
        	Assert.assertTrue(acceptSubmissionPopups(editRequestPage));	
        }else {
        	Assert.assertTrue(false);
        }
	}

	@Test(dependsOnMethods={"acceptRequest"})
	public void generateReply() throws InterruptedException {
	
        UserDashBoard dashBoard = loginPage.doLogIn("1043", "zdDnf86P");
        if (dashBoard.getFirstReqNoOnMyTurn().equals(requestNo)){
        	//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
        	WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
        	//ワークで履歴が開く。依頼書Noをクリック。
        	EditRequestPage editRequestPage = workBoard.clickRequestNo(requestNo);
        	//回答生成をクリック
        	editRequestPage.clickGenerateReplyButton();
        	Assert.assertTrue(acceptSubmissionPopups(editRequestPage));	
        }else {
        	Assert.assertTrue(false);
        }
	}
	
	@Test(dependsOnMethods={"generateReply"})
	public void submitReply() throws InterruptedException {
		
        UserDashBoard dashBoard = loginPage.doLogIn("1043", "zdDnf86P");
        if (dashBoard.getFirstReqNoOnMyTurn().equals(requestNo)){
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
        }else {
        	Assert.assertTrue(false);
        }
	}
	
	@Test(dependsOnMethods={"submitReply"})
	public void acknowledgeResp() throws InterruptedException {
		
        UserDashBoard dashBoard = loginPage.doLogIn("1043", "zdDnf86P");
        if (dashBoard.getFirstReqNoOnMyTurn().equals(requestNo)){
        	//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
        	WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
        	//ワークで履歴が開く。依頼書Noをクリック。
        	EditRequestPage editRequestPage = workBoard.clickRequestNo(requestNo);
        	//担当部署回答承認日
        	editRequestPage.enterRespApprovalDate(dateFormat.format(DateUtil.addDays(date, 15)));
        	//回答承認をクリック。
        	editRequestPage.clickRespApprovalButton();
        	Assert.assertTrue(acceptSubmissionPopups(editRequestPage));
        }else {
        	Assert.assertTrue(false);
        }
	}
	

	@Test(dependsOnMethods={"acknowledgeResp"})
	public void acceptResp() throws InterruptedException {

		//2001　	
        UserDashBoard dashBoard = loginPage.doLogIn("2001", "q1111111");
        if (dashBoard.getFirstReqNoOnMyTurn().equals(requestNo)){
        	//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
        	WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
        	//ワークで履歴が開く。依頼書Noをクリック。
        	EditRequestPage editRequestPage = workBoard.clickRequestNo(requestNo);
        	//依頼回答受理日
        	editRequestPage.enterRespAcceptDate(dateFormat.format(DateUtil.addDays(date, 15)));
        	//回答受理をクリック。
        	editRequestPage.clickRespAcceptButton();
        	//回答受理しますか？ok
        	//回答受理完了: A01E-000115 ok
        	Assert.assertTrue(acceptSubmissionPopups(editRequestPage));
        }else {
        	Assert.assertTrue(false);
        }
	}
	
	@Test(dependsOnMethods={"acceptResp"})
	public void generateResultReport() throws InterruptedException {	

		//2005
        UserDashBoard dashBoard = loginPage.doLogIn("2005", "q1111111");
        if (dashBoard.getFirstReqNoOnMyTurn().equals(requestNo)){
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
        }else {
        	Assert.assertTrue(false);
        }
	}
	
	@Test(dependsOnMethods={"generateResultReport"})
	public void submitResultReport() throws InterruptedException {	

		//2005
        UserDashBoard dashBoard = loginPage.doLogIn("2005", "q1111111");
        if (dashBoard.getFirstReqNoOnMyTurn().equals(requestNo)){
        	//ボードの作業番の依頼書ランの一番上に依頼書Noが来ていて、クリック。
        	WorkBoard workBoard = dashBoard.clickRequestOnMyTurn();	
        	//ワークで履歴が開く。依頼書Noをクリック。
        	EditRequestPage editRequestPage = workBoard.clickRequestNo(requestNo);
        	//結果報告日
        	editRequestPage.enterResultDate(dateFormat.format(DateUtil.addDays(date, 21)));

        	//結果報告提出ボタンをクリック。
        	editRequestPage.clickResultReportButton();
        	//結果報告提出しますか？ok.
        	//結果報告提出完了: A01E-000115 ok.
        	Assert.assertTrue(acceptSubmissionPopups(editRequestPage));		
        }else {
        	Assert.assertTrue(false);
        }		

	}	
	
	@Test(dependsOnMethods={"submitResultReport"})
	public void approveResultReport() throws InterruptedException {	

		//2001
        UserDashBoard dashBoard = loginPage.doLogIn("2001", "q1111111");
        if (dashBoard.getFirstReqNoOnMyTurn().equals(requestNo)){
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
        }else {
        	Assert.assertTrue(false);
        }			

	}	

	@Test(dependsOnMethods={"approveResultReport"})
	public void confirmIfLocatedInCompleted() throws InterruptedException {	

		//2005
		UserDashBoard dashBoard = loginPage.doLogIn("2005", "q1111111");    	
		//ボードの完了済みの依頼書にリストされる。
		dashBoard.assertReportNumEquals("COMPLETED", requestNo);
	}
	
	@AfterMethod
	public void tearDown(){
		driver.close();
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
