import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;

public class LoginCheck {
	WebDriver driver;
	FileInputStream fis; 
	Properties prop;
	ExtentHtmlReporter ml;
	ExtentReports extent;
	ExtentTest t;
	PomClass pom;
  @Test(dataProvider = "dp")
  public void Test(String email, String pass) {
	  pom.login(email, pass);

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
      new Object[] { "shikhardlw@gmail.com", "password@123" },
      new Object[] { "Shikhar Singh", "passowrd@123" },
      new Object[] { "shikhardlw@gmail.com", "passowrd" },
      new Object[] { "", "" },
    };
  }
  @BeforeClass
  public void beforeClass() throws IOException {
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

  @AfterClass
  public void afterClass() {
	  driver.close();
	  extent.flush();
  }

}
