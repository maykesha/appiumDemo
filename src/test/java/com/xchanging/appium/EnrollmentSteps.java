package com.xchanging.appium;

import static org.testng.AssertJUnit.assertEquals;
import io.appium.java_client.AndroidKeyCode;
import io.appium.java_client.AppiumDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.xchanging.util.ConfigurationManager;
import com.xchanging.util.ImportfromExcel;
import com.xchanging.util.JDBC;
import com.xchanging.util.Logging;

import java.io.*;
//import android.widget.TextView;
//import android.widget.ScrollView;



public class EnrollmentSteps{

	
	private AppiumDriver driver = null;
	public WebElement element = null;

	ImportfromExcel excel = new ImportfromExcel();

	Logger log = Logging.getLogger(getClass());
	
	private WebDriverWait wait = null; 
	
	
	
	@Given("I have launched the application")
	public void givenIHaveLaunchedTheApplication() {
		
		String fileName = System.getProperty("user.dir");
		
		File app = new File(fileName + "//APK_Files//CBG//"
				+ ConfigurationManager.getString("APK_FILE"));
		
		final DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "android");
		capabilities.setCapability(CapabilityType.VERSION,
				ConfigurationManager.getString("ANDROID_VER"));
		capabilities.setCapability(CapabilityType.PLATFORM,
				ConfigurationManager.getString("PLATFORM"));
		
		capabilities.setCapability("platformName",
				ConfigurationManager.getString("DEVICE"));
		
		capabilities.setCapability("device",
				ConfigurationManager.getString("DEVICE"));
		capabilities.setCapability("deviceName",
				ConfigurationManager.getString("DEVICE_NAME"));
		capabilities.setCapability("newCommandTimeout", "180");
		capabilities.setCapability("appPackage",
				ConfigurationManager.getString("APK_PACKAGE"));
		
		capabilities.setCapability("appActivity",
				ConfigurationManager.getString("APK_ACTIVITY"));
		
		capabilities.setCapability("App", app.getAbsolutePath());

		try {
			driver = new AppiumDriver(new URL(
					"http://127.0.0.1:4723/wd/hub"), capabilities);
			log.debug("Driver invoked successfully");
		} catch (final MalformedURLException e) {
			e.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		log.debug("Maximized and implicit wait is set to 80 seconds");
		
		wait = new WebDriverWait(driver, 80); 
		
		
		
	}

	
	@Given("I have accepted the terms and conditions")
	public void givenIHaveAcceptedTerms() throws Exception {

		
		
		Thread.sleep(10000);
		// Find and click on "Register"
		
		WebElement Enroll = driver
				.findElementByClassName("android.widget.Button");
		assertEquals("Register", Enroll.getText());
		Enroll = driver.findElement(By.name("Register"));
		if (Enroll.isEnabled()) {
			log.info(Enroll.getText() + " is enabled");
			Enroll.click();
		} else {
			log.error(Enroll.getText() + " is disabled");
		}

	// Scroll down to "Accept Terms and Conditions"
		WebElement Accept = driver
				.findElementByClassName("android.widget.Button");
		
		do {

			driver.swipe(400, 600, 400, 100, 1000);

			} while (!Accept.isEnabled());

			if (Accept.isEnabled()){

			Accept.click();

			}
		 		 		 
		
	}
	
	@Given("I have entered Phone Number")
	public void givenIHaveEnteredPhoneNumber() throws Exception {

		//final String DATA_NAME = excel.readFromExcel("TestData", 2, 1);
		final String DATA_MSISDN = excel.readFromExcel("TestAccounts", 2, 1);
		//final String DATA_EMAIL = excel.readFromExcel("TestAccounts", 1, 1);
		//final String DATA_PASSCODE = excel.readFromExcel("TestData", 1, 1);	
		
		// MSISDN
		WebElement MSISDN = driver
				.findElementByClassName("android.widget.EditText");
		MSISDN = driver.findElement(By.name("Enter mobile"));
		MSISDN.click();
		MSISDN.sendKeys("0"+ DATA_MSISDN);
		log.info(DATA_MSISDN + "is entered for MSISDN");
		Thread.sleep(1000);
		
		driver.sendKeyEvent(AndroidKeyCode.ENTER);
		
		

		
		// Click on NEXT
		WebElement NEXT = driver
				.findElementByClassName("android.widget.Button");
		      NEXT = driver.findElement(By.name("Next"));
              NEXT.click();
              log.info("Clicked on NEXT");

              Thread.sleep(2000);
              
         // Confirm the Mobile Number for HVC generation
              
              WebElement CONFIRM = driver
      				.findElementByClassName("android.widget.Button");
              CONFIRM = driver.findElement(By.name("Confirm"));
              CONFIRM.click();
                    log.info("Clicked on NEXT");

                    Thread.sleep(3000);
		
		

	
	}
	
	@Given("I have entered my HVC code")
	public void givenIHaveEnteredHvc() throws Exception {
		
		final String DATA_MSISDN = excel.readFromExcel("TestAccounts", 2, 1);
	

		final JDBC jdbcObject = new JDBC();
		jdbcObject.jdbcConnection();
		log.info("Connected to DB");
		Thread.sleep(5000);

		final String number = "44" + DATA_MSISDN;
		final long l = Long.parseLong(number);

		final String hvc = jdbcObject.getHVC(l);
		System.out.println(hvc);
		log.info("Got the hvc value from DB :" + hvc);

		final WebElement HVC = driver
				.findElementByClassName("android.widget.EditText");
		HVC.click();
		HVC.sendKeys(hvc);
		Thread.sleep(1000);
		
		driver.sendKeyEvent(AndroidKeyCode.ENTER);


		// Click on NEXT
				final WebElement NEXT = driver
						.findElementByClassName("android.widget.Button");

		NEXT.click();
		Thread.sleep(2000);
	}
	
	@Given("I have entered Passcode")
	public void givenIHaveEnteredPasscode() throws Exception {
		
		final String DATA_PASSCODE = excel.readFromExcel("TestData", 1, 1);
		
		List<WebElement> PASSCODE = driver.findElementsByClassName("android.widget.EditText");
		PASSCODE.get(0).sendKeys(DATA_PASSCODE);
		driver.sendKeyEvent(AndroidKeyCode.ENTER);
		PASSCODE.get(1).sendKeys(DATA_PASSCODE);
		driver.sendKeyEvent(AndroidKeyCode.ENTER);
		
		// Click on NEXT
				WebElement NEXT = driver
						.findElementByClassName("android.widget.Button");
				NEXT = driver.findElement(By.name("Next"));
				NEXT.click();		              
		               
		               Thread.sleep(1000);		
		               
		               log.info("MONITISE REGISTRATION PART IS COMPLETED SUCCESSFULLY");
		
	}
	
	@Given("I have entered Authentication Details SP1")	
	public void givenIHaveEnteredAuthenticationDetailsSP1() throws Exception {
		log.info("Hello");
		final String DATA_SURNAME = excel.readFromExcel("TestData", 2, 1);
		final String DATA_DOB_DD = excel.readFromExcel("TestData", 3, 1);
		final String DATA_DOB_MMM = excel.readFromExcel("TestData", 4,1);
		final String DATA_DOB_YYYY = excel.readFromExcel("TestData", 5,1);		
		final String DATA_SORTCODE_1 = excel.readFromExcel("TestData", 6, 1);		
		final String DATA_ACCOUNTNO = excel.readFromExcel("TestData", 7, 1);
		
		// Enter Customer Name
		
		WebElement SURNAME = driver
				.findElementByClassName("android.widget.EditText");
		log.info("Hello");
		
		
		SURNAME = driver.findElement(By.name("Surname"));
		SURNAME.click();
		SURNAME.sendKeys(DATA_SURNAME);
		
		driver.sendKeyEvent(AndroidKeyCode.ENTER);
		
		Thread.sleep(2000);
		
		//Enter Customer Date of Birth
		
				
		List<WebElement> N1 = driver.findElementsByClassName("android.widget.NumberPicker");
		N1.get(0).sendKeys(DATA_DOB_DD);
		Thread.sleep(1000);
		N1.get(1).sendKeys(DATA_DOB_MMM);
		Thread.sleep(1000);
		N1.get(2).sendKeys(DATA_DOB_YYYY);
		Thread.sleep(1000);
		WebElement SET = driver
				.findElementByClassName("android.widget.Button");
		SET = driver.findElement(By.name("Set"));
		SET.click();
		Thread.sleep(1000);
		
		//Enter Customer SortCode

		WebElement SORTCODE = driver
				.findElementByClassName("android.widget.EditText");
		SORTCODE = driver.findElement(By.name("00"));
		SORTCODE.sendKeys(DATA_SORTCODE_1);
		Thread.sleep(1000);
		
		
		// Enter Account Number
		
		WebElement ACCOUNTNO = driver
				.findElementByClassName("android.widget.EditText");
		ACCOUNTNO = driver.findElement(By.name("Account number"));
		ACCOUNTNO.click();
		ACCOUNTNO.sendKeys(DATA_ACCOUNTNO);
		
		
		driver.sendKeyEvent(AndroidKeyCode.ENTER);
		Thread.sleep(1000);
		
		
		
		// Click on NEXT
		WebElement NEXT = driver
				.findElementByClassName("android.widget.Button");
		NEXT = driver.findElement(By.name("Next"));
		NEXT.click();

               
               Thread.sleep(2000);					
               }
	
	@Given("I have entered Authentication Details SP2")	
	public void givenIHaveEnteredAuthenticationDetailsSP2() throws Exception {
		
final String DATA_ANSWER1 = excel.readFromExcel("TestData", 8, 1);
final String DATA_ANSWER2 = excel.readFromExcel("TestData", 9, 1);
		
		List<WebElement> ANSWER = driver.findElementsByClassName("android.widget.EditText");
		ANSWER.get(0).sendKeys(DATA_ANSWER1);
		driver.sendKeyEvent(AndroidKeyCode.ENTER);
		ANSWER.get(1).sendKeys(DATA_ANSWER2);
		driver.sendKeyEvent(AndroidKeyCode.ENTER);
		
		// Click on NEXT
				WebElement NEXT = driver
						.findElementByClassName("android.widget.Button");
				NEXT = driver.findElement(By.name("Next"));
				NEXT.click();		              
		               
		               Thread.sleep(2000);			
		
	}
	
	@When("I have entered Authentication Details SP3")	
	public void whenIHaveEnteredAuthenticationDetailsSP3() throws Exception {
		
final String DATA_ANSWER3 = excel.readFromExcel("TestData", 10, 1);

		
		List<WebElement> QA1 = driver.findElementsByClassName("android.widget.EditText");
		QA1.get(0).sendKeys(DATA_ANSWER3);
		driver.sendKeyEvent(AndroidKeyCode.ENTER);
		
		
		// Click on NEXT
				WebElement NEXT = driver
						.findElementByClassName("android.widget.Button");
				NEXT = driver.findElement(By.name("Next"));
				NEXT.click();		              
		               
		               Thread.sleep(2000);			
		
	}
	
	@Then("I get successfully registered")	
	public void thenUserIsSuccessfullyRegistered() throws Exception{
		
		WebElement VIEWACCOUNTS = driver
				.findElementByClassName("android.widget.Button");
		VIEWACCOUNTS = driver.findElement(By.name("View your accounts"));
		VIEWACCOUNTS.click();
		
		Thread.sleep(2000);
		
	}
	
	@Given("I have successfully registered to CBG")	
	public void givenIHaveSuccessfullyRegisteredToCBG() throws Exception {
		
		log.info("User is already registered");
		
		Thread.sleep(1000);
		
	}
	
	@Given("I have navigated to various Accounts")	
	public void givenIHaveNavigatedToVariousAccounts() throws Exception {
		
		
		//driver.swipe(600, 400, 100, 400, 1000);
		
		Thread.sleep(5000);
		
		//Slide the menu onto the screen
		JavascriptExecutor js = (JavascriptExecutor) driver;
	    HashMap<String, Double> swipeObject = new HashMap<String, Double>();
	    swipeObject.put("startX", 0.01);
	    swipeObject.put("startY", 0.5);
	    swipeObject.put("endX", 0.5);
	    swipeObject.put("endY", 0.6);
	    swipeObject.put("duration", 2.5);
	    HashMap[] params = { swipeObject };
	    js.executeScript("mobile: swipe", params);
		
		log.info("User navigated to various accounts");
		Thread.sleep(1000);
		
	}
	
	@Given("I have successfully performed Payment Transfer")	
	public void givenIHaveSuccessfullyPerformedPaymentTransfer() throws Exception {
		
		final String DATA_AMOUNT = excel.readFromExcel("TestData", 11, 1);
		
		WebElement MENU = driver
				.findElementByClassName("android.widget.TextView");
		MENU = driver.findElement(By.name("Menu"));
		MENU.click();
		
		Thread.sleep(1000);
		
		WebElement PAYMENTS = driver
				.findElementByClassName("android.widget.TextView");
		
		PAYMENTS = driver.findElement(By.name("Payments"));
		PAYMENTS.click();
		
		Thread.sleep(1000);
		
		WebElement IAT = driver
				.findElementByClassName("android.widget.TextView");
		
		IAT = driver.findElement(By.name("Transfers between your accounts"));
		IAT.click();
		
		Thread.sleep(1000);
		
		WebElement FROM = driver
				.findElementByClassName("android.widget.TextView");			
		FROM = driver.findElement(By.name("Please select an account to transfer from"));
		FROM.click();
		
		Thread.sleep(2000);
		
		WebElement ACCOUNT1 = driver
				.findElementByClassName("android.widget.TextView");			
		ACCOUNT1 = driver.findElement(By.name("Current Account Plus"));
		ACCOUNT1.click();
		
		Thread.sleep(2000);
		
		WebElement TO = driver
				.findElementByClassName("android.widget.TextView");			
		TO = driver.findElement(By.name("Please select an account to transfer to"));
		TO.click();
		
		Thread.sleep(2000);
		
		
		WebElement ACCOUNT2 = driver
				.findElementByClassName("android.widget.TextView");			
		ACCOUNT2 = driver.findElement(By.name("Deposit"));
		ACCOUNT2.click();
		
		Thread.sleep(1000);
		
		WebElement AMOUNT = driver
				.findElementByClassName("android.widget.EditText");			
		AMOUNT = driver.findElement(By.name("£0.00"));
		//AMOUNT.click();
		AMOUNT.sendKeys(DATA_AMOUNT);
		Thread.sleep(2000);
		
		//driver.sendKeyEvent(AndroidKeyCode.ENTER);
		
		Thread.sleep(2000);
		
		WebElement PROCEED = driver
				.findElementByClassName("android.widget.Button");			
		PROCEED = driver.findElement(By.name("Transfer"));
		PROCEED.click();
		Thread.sleep(4000);	
		
		WebElement CONFIRM = driver
  				.findElementByClassName("android.widget.Button");
          CONFIRM = driver.findElement(By.name("Confirm"));
          CONFIRM.click();
          
         
            
		Thread.sleep(8000);	
       try{    	   

			WebElement OK = driver
     				.findElementByClassName("android.widget.Button");
			OK = driver.findElement(By.name("OK"));
			OK.click();
    	   
    	   
       }
      catch(Exception e){
    	  
    	  log.info("Transfer is unsuccessful");
    	  
    	  WebElement DONE = driver
  				.findElementByClassName("android.widget.Button");
       DONE = driver.findElement(By.name("Done"));
       DONE.click();
}
       
		
	}
	
	@Given("I have successfully performed Bill Pay")	
	public void givenIHaveSuccessfullyPerformedBillPay() throws Exception {
		final String DATA_AMOUNT = excel.readFromExcel("TestData", 11, 1);
        
        WebElement MENU = driver.findElementByClassName("android.widget.TextView");
        MENU = driver.findElement(By.name("Menu"));
        MENU.click();
        
        Thread.sleep(1000);
        
        
        WebElement PAYMENTS = driver.findElementByClassName("android.widget.TextView");
        PAYMENTS = driver.findElement(By.name("Payments"));
        PAYMENTS.click();
        
        Thread.sleep(1000);
        
        WebElement PAYABILL = driver.findElement(By.name("Pay a bill or send money"));
        PAYABILL.click();
        
        WebElement CURRENTACCOUNT= driver.findElementByClassName("android.widget.TextView");
        CURRENTACCOUNT = driver.findElement(By.name("Current Account Plus"));
        CURRENTACCOUNT.click();
        
        Thread.sleep(1000);
        
        
        try{
    WebElement POPUP = driver.findElementByClassName("android.widget.TextView");
        }

               catch(Exception e)
               {
               System.out.println("Problem popup displayed");
               }

               finally {

               WebElement RETRY = driver.findElementByClassName("android.widget.Button");

               RETRY =driver.findElement(By.name("Retry"));

               //RETRY.click();

               }
        
        WebElement TOACCOUNT = driver.findElementByClassName("android.widget.TextView");
        
        TOACCOUNT = driver.findElement(By.name("Dan"));
        TOACCOUNT.click();
        
        WebElement AMOUNT = driver.findElementByClassName("android.widget.EditText");
        AMOUNT = driver.findElement(By.name("£0.00"));
        AMOUNT.sendKeys(DATA_AMOUNT);
        Thread.sleep(2000);
        
        WebElement SEND = driver.findElementByClassName("android.widget.Button");
        SEND = driver.findElement(By.name("Send"));
        SEND.click();
        
        WebElement CONFIRM = driver
                     .findElementByClassName("android.widget.Button");
    CONFIRM = driver.findElement(By.name("Confirm"));
    CONFIRM.click();
        
        
        WebElement DONE = driver.findElementByClassName("android.widget.Button");
        DONE = driver.findElement(By.name("Done"));
        DONE.click();
        
        
        log.info("User successfully performed BILL PAYMENT");
        Thread.sleep(1000);		
		
	}
		
	
	@AfterStories
	public void cleanup() throws Exception{
		
	 // Clean up
		
		Thread.sleep(5000);
		driver.quit();
		System.out.println("Over");

	}
	
	
		
}
