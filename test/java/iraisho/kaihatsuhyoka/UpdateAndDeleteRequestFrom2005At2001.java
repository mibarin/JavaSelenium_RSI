package iraisho.kaihatsuhyoka;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import IraishoUtils.TestProject;

public class UpdateAndDeleteRequestFrom2005At2001 extends TestProject {
	
	private WebDriver driver;
	private IraishoLoginPage loginPage;
	private EditRequestPage editReqPage;
	
	@BeforeTest(alwaysRun=true)
	public void setUp() {
		  super.setUp();
		  driver = super.getDriver();
		  loginPage = super.getLoginPage();
	}
	
    @Test
    public void updateRequest() throws InterruptedException{
    	
    	editReqPage = super.NewReqSubmissionWithGenericData("2005", "q1111111");  
    	editReqPage.updateRequestContentTest(EditRequestPage editRequestPage, String requestContent, Tes
	}
    
   
  
  
  
}


