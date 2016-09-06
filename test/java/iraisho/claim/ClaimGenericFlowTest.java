package iraisho.claim;


import iraisho.kaihatsuhyoka.IraishoLoginPage;
import iraisho.kaihatsuhyoka.UserDashBoard;

import org.testng.annotations.Test;

import IraishoUtils.IraishoUtils;

public class ClaimGenericFlowTest {
	
		  
	@Test
	public void NewReqSubmission()throws Exception
	{
		  IraishoUtils iraishoTest = new IraishoUtils();
		  String dataFilePath = "C:\\Users\\Akiko\\Desktop\\RSI\\Automation\\bpm_claim_data.xls";
		  
		  iraishoTest.setUp();		  
		  iraishoTest.newClaimSubmission("2005", "q1111111", dataFilePath, "claim_start");
		  iraishoTest.signOut(); 
          iraishoTest.acknowledgeClaim("2001", "q1111111");
		  iraishoTest.signOut(); 
          iraishoTest.claimRequestConfirmation("1070", "E@5T*dp#", dataFilePath, "claim_conf");
		  iraishoTest.signOut(); 
          iraishoTest.acknowledgeClaimRequestConfirmation("2001", "q1111111");
		  iraishoTest.signOut(); 
		  iraishoTest.acceptClaimRequest("1070", "E@5T*dp#", "1043-0");
		  iraishoTest.signOut(); 
		  iraishoTest.generateClaimReply("1043", "zdDnf86P");
		  iraishoTest.signOut(); 
		  iraishoTest.submitClaimReply("1043", "zdDnf86P", dataFilePath, "claim_reply");
		  iraishoTest.signOut(); 
		  iraishoTest.acknowledgeClaimResp("1070", "E@5T*dp#");
		  iraishoTest.signOut(); 
		  iraishoTest.acceptClaimResp("2001", "q1111111");
		  iraishoTest.signOut(); 
		  iraishoTest.generateClaimResultReport("2005", "q1111111", dataFilePath, "claim_result");
		  iraishoTest.signOut(); 
		  iraishoTest.submitClaimResultReport("2005", "q1111111",dataFilePath, "claim_result_date");
		  iraishoTest.signOut(); 
		  iraishoTest.approveClaimResultReport("2001", "q1111111",dataFilePath, "claim_result_date");
		  iraishoTest.signOut(); 
		  
		  IraishoLoginPage login = new IraishoLoginPage(iraishoTest.getDriver());	  
		  UserDashBoard dashBoard = login.doLogIn("2005","q1111111", "クレーム処理");
		  iraishoTest.confirmIfLocatedInCompleted(dashBoard);
		  iraishoTest.tearDown();
		  
	}

}
