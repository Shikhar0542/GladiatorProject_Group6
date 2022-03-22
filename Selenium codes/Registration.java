import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;

public class Registration {
	WebDriver driver;
	FileInputStream fis; 
	Properties prop;
	ExtentHtmlReporter ml;
	ExtentReports extent;
	PomClass pom;
	ExtentTest t;
  @Test(dataProvider = "dp")
  public void f(String n, String e, String pass, String num) {
	  
	  pom.register(e, pass, n, num);
	  String b=driver.findElement(By.xpath(prop.getProperty("check"))).getText();
	  
	  if(b.equals("Booking Status"))
	  {
		  System.out.println("registration unsuccessful");
		  t.log(Status.FAIL, "Test get failed as invalid credentials");
	  }
	  else
	  {
		  System.out.println("Registration successful");
		  t.log(Status.PASS, "Test get passed as valid credentials");
	  }
  }

  @DataProvider
  public Object[][] dp() {
    return new Object[][] {
      new Object[] { "Shelly poonia", "lifeisdamngreat@gmail.com", "shellypoonia@123", "9871477078" },
      //right   
      new Object[] { "Shivang Singh", "shivang0542", "password@123", "9335618966" },
      //wrong
      new Object[] { "Shivang@1great", "shivangsinghblw@gmail.com", "password@123", "9335618966" },
      //wrong
      new Object[] { "Poonam Sharma", "sharmahaikyaudhar@gmail.com", "passwordcheck", "9818941859" },
      //right
      new Object[] { "Rohit Kumar", "rohithaphysicska@gmail.com", "89567##@%", "8750721151" },
      //right
      new Object[] { "Shikhar Singh", "shikhardlw@gmail.com", "hello", "9506707875" },
      //wrong
      new Object[] { "Rekha Singh", "rekhadlw@gmail.com", "beautiful@5678", "95067" },
      //wrong
      new Object[] { "Shivang Singh", "shivangsinghblw@gmail.com", "World@hi", "Hello6767#" },
      //wrong
      new Object[] { "Shivang Singh", "shivangsinghblw@gmail.com", "password@123", "5645787688" },
      //wrong
      new Object[] { "", "", "", "" },
      //wrong
      //new Object[] { "Shikhar Singh", "shikhardlw@gmail.com", "passowrd@123", "9977711990" },
      //wrong
      //new Object[] { "Shashank Prakash", "musicalengineermanit@gmail.com", "hello@123", "9506707875" },
      //wrong
      
    };
  }
  @BeforeMethod
  public void beforeMethod() throws IOException {
	  fis=new FileInputStream(new File("C:\\Users\\shikh\\workspace\\PROJESCT_WORK\\src\\test\\resources\\Data.properties"));
		prop=new Properties();
		prop.load(fis);
			System.setProperty(prop.getProperty("key"),prop.getProperty("chrome"));
			driver = new ChromeDriver();
			driver.get(prop.getProperty("url"));
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.findElement(By.id(prop.getProperty("nothanks"))).click();
			ml=new ExtentHtmlReporter("./reports/registration.html");
			extent=new ExtentReports();
			extent.attachReporter(ml);
			t=extent.createTest("Registration report with valid credentials");
			pom=new PomClass(driver, fis, prop, t);
  }

  @AfterMethod
  public void afterMethod() {
	  driver.close();
	  extent.flush();
  }

}
