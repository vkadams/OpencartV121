package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage{
	
	// invoking parent class invoked here by using super keyword.
	public HomePage(WebDriver driver) 
	{
		super(driver);
	}
	
	@FindBy(xpath="//span[normalize-space()='My Account']") WebElement lnkMyaccount;
	@FindBy(xpath="//a[normalize-space()='Register']") WebElement lnkRegister;
	@FindBy(xpath="//a[normalize-space()='Login']") WebElement lnkLogin;
	
	public void clickMyAccount()
	{
		lnkMyaccount.click();
	}
	
	public void clickRegister()
	{
		lnkRegister.click();
	}
	
	public void clickLogin()
	{
		lnkLogin.click();
	}

}
