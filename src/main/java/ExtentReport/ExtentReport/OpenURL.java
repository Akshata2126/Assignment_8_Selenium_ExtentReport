package ExtentReport.ExtentReport;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import org.testng.AssertJUnit;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;


public class OpenURL {
	public WebDriver driver;
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest logger;
	ExtentTest logger2;
	
	public String captureScreen() throws IOException {
		TakesScreenshot screen = (TakesScreenshot) driver;
		File src = screen.getScreenshotAs(OutputType.FILE);
		
		String dest ="/ExtentReport/Report/Screenshot/"+System.currentTimeMillis()+".png";
		File target = new File(dest);
		FileUtils.copyFile(src, target);
		return dest;
	}
@Test
	public void Openbrowser() throws IOException
	{
			
		
		//setting property for Chrome browser
		System.setProperty("webdriver.chrome.driver", 
				"C:\\Users\\Manoj\\Desktop\\selenium\\chromedriver.exe");
		driver = new ChromeDriver();
		htmlReporter = new ExtentHtmlReporter("./Report/FirstReport1.html");
		extent= new ExtentReports();
		extent.attachReporter(htmlReporter);			
		logger= extent.createTest("Openbrowser");

		//Open google in Chrome browser
		driver.get("https://www.google.com");
		logger.log(Status.INFO, "Google browser is opened");
		logger.addScreenCaptureFromPath(captureScreen());
		
		WebElement searchbutton=driver.findElement(By.name("q"));
		logger.log(Status.INFO, "Click on Search Textbox");
		logger.addScreenCaptureFromPath(captureScreen());
		
		//search for "sap fiori trial"
		searchbutton.sendKeys("sap fiori trial");
		logger.log(Status.INFO, "Enter 'sap fiori trial' in Search Textbox");
		logger.addScreenCaptureFromPath(captureScreen());
		searchbutton.submit();
		logger.log(Status.INFO, "Click on Search button");
		System.out.println(captureScreen());
		//click on First link
		WebElement myAccount=driver.findElement(By.xpath("//*[@id=\'rso\']/div[1]/div/div[1]/div/div/div[1]/a/div/cite"));
		AssertJUnit.assertTrue(myAccount.isDisplayed());
		logger.log(Status.INFO, "Validating link is displayed or not");
		myAccount.click();
		logger.log(Status.INFO, "Clicked on link");
		logger.addScreenCaptureFromPath(captureScreen());
		//logger.addScreenCaptureFromPath("./Screenshot/1554996589533.png");
		
		logger.log(Status.PASS, "TestCase1 passed");
		extent.flush();
	}

@Test()
public void checkTitle()
{
	logger2= extent.createTest("checkTitle");
	logger2.log(Status.INFO, "Validate Title");

	String name="SAP Fiori Cloud Demo";
	String title=driver.getTitle();
	Assert.assertTrue(name.equals(title));
	logger2.log(Status.INFO, "Title is validated");
	logger2.log(Status.PASS, "TestCase2 passed");
	
	extent.flush(); 
}

@AfterTest
public void Closedriver()
{
	driver.quit();
}


}
