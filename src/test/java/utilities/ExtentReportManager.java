package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager  implements ITestListener{
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	String repName;
	
	public void onStart(ITestContext testContext) {
	    /*SimpleDateFormat df= new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	     * Date dt= new Date();
	     * String currentdatetimestamp=df.format(dt);*/
		
		// above 3 steps into single step as below-		
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		repName= "Test-Report-" +timeStamp+".html"; // report file name
		sparkReporter = new ExtentSparkReporter(".\\reports\\"+repName); // specify location
		
		sparkReporter.config().setDocumentTitle("Opencart Automation Report"); // Title
		sparkReporter.config().setReportName("Opencart Functional Testing"); // name of
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "Opencart");
		extent.setSystemInfo("Module","Admin");
		extent.setSystemInfo("Sub Module","Customers");
		extent.setSystemInfo("User Name",System.getProperty("user.name"));
		extent.setSystemInfo("Environment","QA");
		
		String os = testContext.getCurrentXmlTest().getParameter("os"); // from xml file we are getting parameters> os
		extent.setSystemInfo("Operating System", os);
		
		String browser = testContext.getCurrentXmlTest().getParameter("browser"); // from xml file we are getting parameters> browser
		extent.setSystemInfo("Browser", browser);
		
		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups(); // from xml file get groups info
		if(!includedGroups.isEmpty()) // if there is no groups in the xml, then do not add
		{
			extent.setSystemInfo("Groups", includedGroups.toString()); 
		}
	  }
	
	
	public void onTestSuccess(ITestResult result) {
	    test = extent.createTest(result.getTestClass().getName()); // creating an entry in report having class name
	    test.assignCategory(result.getMethod().getGroups()); // to display groups in report
	    test.log(Status.PASS, result.getName()+" got successfully executed");
	  }
	
	
	public void onTestFailure(ITestResult result) {
	    test= extent.createTest(result.getTestClass().getName());
	    test.assignCategory(result.getMethod().getGroups());
	    
	    test.log(Status.FAIL, result.getName()+" got failed");
	    test.log(Status.INFO, result.getThrowable().getMessage());
	    
	    try
	    {
	    	String imgPath = new BaseClass().captureScreen(result.getName()); // invokes capture screen method from base class, returns location of image
	    	test.addScreenCaptureFromPath(imgPath); // this image is attached to the report, test refers to report.
	    }
	    catch(IOException e1)
	    {
	    	e1.printStackTrace();
	    }
	  }
	
	
	public void onTestSkipped(ITestResult result) {
	    test = extent.createTest(result.getTestClass().getName());
	    test.assignCategory(result.getMethod().getGroups());
	    test.log(Status.SKIP, result.getName()+ " got Skipped");
	    test.log(Status.INFO, result.getThrowable().getMessage());
	  }
	
	
	public void onFinish(ITestContext context) {

		extent.flush();
		// opening the report after execution
		String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName;
		File extentReport = new File(pathOfExtentReport);
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*
		 * try { URL url = new
		 * URI("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName).toURL(); // url
		 * format of report
		 * 
		 * // create the email message ImageHtmlEmail email = new ImageHtmlEmail();
		 * email.setDataSourceResolver(new DataSourceUrlResolver(url));
		 * email.setHostName("smtp.googleemail.com"); email.setSmtpPort(465);
		 * email.setAuthenticator(new
		 * DefaultAuthenticator("for.mycartosell@gmail.com","password"));
		 * email.setSSLOnConnect(true); email.setFrom("for.mycartosell@gmail.com"); //
		 * Sender email.setSubject("Test Results");
		 * email.setMsg("Please find attached report..");
		 * email.addTo("for.mycartosell@gmail.com"); // Receiver email.attach(url,
		 * "extent report", "please check report.."); email.send(); // send email }
		 * catch(Exception e) { e.printStackTrace(); }
		 */
		
		
	  }

	
	
	
}
