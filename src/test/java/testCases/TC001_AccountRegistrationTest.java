package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {
	
	@Test(groups={"Regression", "Master"})
	public void verify_account_registration()
	{
		logger.info("*** Starting TC001_AccountRegistrationTest ***");
		try {
			HomePage hp = new HomePage(driver);
			logger.info("Clicked on My Account link");
			hp.clickMyAccount();
			logger.info("Clicked on Register link");
			hp.clickRegister();
			
			AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
			logger.info("Providing customer details...");
			regpage.setFirstName(randomeString().toUpperCase());
			regpage.setLastName(randomeString().toUpperCase());
			regpage.setEmail(randomeString()+"@yahoo.com");
			regpage.setPhone(randomeNumber());
			
			String password = randomeAlphaNumeric();
			regpage.setPassword(password);
			regpage.setPassconf(password);
			
			regpage.setPrivacyPolicy();
			regpage.clickContinue();
			
			logger.info("Validating expected message...");
			String confMsg =regpage.getConfirmationMsg();
			if(confMsg.equals("Your Account Has Been Created!"))
			{
				Assert.assertTrue(true);
			}
			else
			{
				logger.error("Test failed..");
				logger.debug("Debug logs..");
				Assert.assertTrue(false);
			}
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		logger.info("*** Completed TC001_AccountRegistrationTest ***");
	}
	
	

}
