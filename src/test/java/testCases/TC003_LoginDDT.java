package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

/* Data is valid- login success- test pass- logout
 * Data is valid- login unsuccessful- test fail
 * 
 * Data is invalid- login success- test fail - logout
 * Data is invalid- login unsuccessful- test pass*/

public class TC003_LoginDDT extends BaseClass{
	@Test(dataProvider="LoginData", dataProviderClass=DataProviders.class, groups="DataDriven") // getting data provider from different class
	public void verify_loginDDT(String email, String pwd, String exp)
	{
		logger.info("***starting tc003_LoginDDT***");
		try {
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		LoginPage lp = new LoginPage(driver);
		lp.setEmailAddress(email);
		lp.setPassword(pwd);
		lp.clickLogin();
		
		MyAccountPage mp = new MyAccountPage(driver);
		boolean targetPage = mp.isMyAccountPageExists();
		
		if(exp.equalsIgnoreCase("Valid"))
		{
			if(targetPage == true)
			{
				Assert.assertTrue(true);
				mp.clickLogout();
			}
			else
			{
				Assert.assertTrue(false);
			}
		}
		if(exp.equalsIgnoreCase("Invalid"))
		{
			if(targetPage == true)
			{
				mp.clickLogout();
				Assert.assertTrue(false);
			}
			else
			{
				Assert.assertTrue(true);
			}
		}
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		logger.info("***Completing tc003_LoginDDT***");
	}

}
