package iraisho.kaihatsuhyoka;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * Page Object for login Page
 *
 */
public class IraishoLoginPage
{
	private WebDriver driver;
	private Alert alert;
	private WebDriverWait wait;
	
	@FindBy(id = "username")
	private WebElement userNameInputField;
	
	@FindBy(id = "password")
	private WebElement passwordInputField;
	
	@FindBy(name = "reqType")
	private WebElement taskType;
	
	@FindBy(name = "locale")
	private WebElement language;
	
	@FindBy(id = "sanyu-login")
	private WebElement loginButton;
	
	@FindBy(name = "reqType")
	private WebElement requestType;
	
	
	public IraishoLoginPage(WebDriver driver){
		this.driver = driver;
		wait = new WebDriverWait(driver, 60);
	}
	
	public IraishoLoginPage typeUsername(String username) throws InterruptedException{
		wait.until(ExpectedConditions.visibilityOf(userNameInputField));
		//userNameInputField.clear();
		userNameInputField.sendKeys(username);
		return this;
	}
	
	public IraishoLoginPage typePassword(String psswd) throws InterruptedException{
		wait.until(ExpectedConditions.visibilityOf(passwordInputField));
		//passwordInputField.clear();
		passwordInputField.sendKeys(psswd);
		return this;
	}
	
	public UserDashBoard doLogIn(String username, String psswd) throws InterruptedException{
		typeUsername(username);
		typePassword(psswd);
		return goToUserPage();
	}
	
	public UserDashBoard doLogIn(String username, String psswd, String reqType) throws InterruptedException{
		typeUsername(username);
		typePassword(psswd);
		selectRequestType(reqType);
		return goToUserPage();
	}
	
	
	public UserDashBoard doLogInWithPopUp(String username, String psswd, String businessType) throws InterruptedException{
		typeUsername(username);
		typePassword(psswd);
		loginButton.click();
		
		if(businessType == "New Business"){
			driver.findElement(By.cssSelector("#popup_panel > form:nth-child(1) > input:nth-child(1)")).click();
		} else {
			driver.findElement(By.cssSelector("#popup_panel > form:nth-child(1) > input:nth-child(3)")).click();			
		}
		return PageFactory.initElements(driver, UserDashBoard.class);
	}
	
	public IraishoLoginPage clickOnLoginPageButton() throws InterruptedException{
		loginButton.click();
		return this;
	}
		
	public UserDashBoard goToUserPage() throws InterruptedException{
		loginButton.click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return PageFactory.initElements(driver, UserDashBoard.class);
	}
	
	public void assertMismatchAlertMessage() throws InterruptedException{
		String message = "ユーザ名とパスワードが合いません。";
		alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), message);
	}
	
	public void assertNonExistentUserAlertMessage(String userName) throws InterruptedException{
		String message = "ユーザアカウント(" + userName + ")は存在しません。";
		alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), message);
	}
	
	public IraishoLoginPage clickOkOnAlert() throws InterruptedException{
		Alert alert = driver.switchTo().alert();
		alert.accept();
		return this;
	}
	
	public IraishoLoginPage selectRequestType(String reqType){
		Select dropdown = new Select(requestType);
		dropdown.selectByVisibleText(reqType);
		return this;
	}
}
