package iraisho.kaihatsuhyoka;

import org.testng.annotations.Test;

import IraishoUtils.IraishoUtils;


public class SpecialRejectionFlow {
	
@Test
public void specialRejectionTest() throws InterruptedException {
	  
	  IraishoUtils iraishoTest = new IraishoUtils();
	  String title = "その他, プレシジョン設計課差し戻しケース";
	  
	  iraishoTest.setUp();
	  
	  //2005 login,コード：その他, 担当部署：プレ氏ション設計か
	  //select sisutemu seihinn	
	  iraishoTest.newReqSubmissionWithMinEntry("2005", "q1111111", title, "その他", "G201");
	  iraishoTest.signOut(); 
	  
	  //2001, 提出承認日, submitted.
	  iraishoTest.acknowledgeRequest("2001", "q1111111");
	  iraishoTest.signOut();
	  
 	  //10０２,常務取締役　選択, 差し戻し理由,差し戻し
	  iraishoTest.requestConfirmationReject("1002", "EuDCnsx5", "常務取締役", "担当部署を変更してください。");
	  iraishoTest.signOut();
	  
	  //2001, 担当部署にプレシジョンを入れて, 提出承認日
	  iraishoTest.acknowledgeRequestWithChangeDept("2001", "q1111111","G201");
	  iraishoTest.signOut();  
	  
	  //10１０, 依頼事項確認, 依頼確認
	  iraishoTest.requestConfirmation("1010", "eQDr^Aap");
	  iraishoTest.signOut();

	  //2001
	  //依頼確認了承ボタン
	  iraishoTest.acknowledgeRequestConfirmation("2001", "q1111111");
	  iraishoTest.signOut();
	  //1010
	  //作業担当：１０１０－０、担当者回答期限、担当部署受理日
	  iraishoTest.acceptRequestGenerateAndSubmitReply("1010", "eQDr^Aap", "1010-0");
	  iraishoTest.signOut();
	  
//	  //1010, 回答作成, 回答内容、回答提出日、回答提出
//	  iraishoTest.submitReply("1010", "eQDr^Aap");
//	  iraishoTest.signOut();
	  
	   //1010, 担当部署回答承認日、回答承認
	  iraishoTest.acknowledgeResp("1010", "eQDr^Aap");
	  iraishoTest.signOut();
	  
//	  ２００1, 結果、結果内容、結果報告作成, 結果報告日、結果報告提出
	  iraishoTest.acceptResp("2001", "q1111111");
	  iraishoTest.signOut();
	    
//	  ２００5, 結果、結果内容、結果報告作成, 結果報告日、結果報告提出
	  iraishoTest.generateResultReport("2005", "q1111111");
	  iraishoTest.signOut();
	  
	  iraishoTest.submitResultReport("2005", "q1111111");
	  iraishoTest.signOut();
	    
	    //2001, 結果報告承認日、結果報告承認, 
	  iraishoTest.approveResultReport("2001", "q1111111");
	  iraishoTest.signOut();
	  
	  //ボードで完了済みの依頼書欄に来たかどうかチェック
	  iraishoTest.confirmIfLocatedInCompleted("2005", "q1111111");
	  
  }
}
