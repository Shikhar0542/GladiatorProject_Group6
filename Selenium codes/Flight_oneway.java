import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

public class Flight_oneway {
	WebDriver driver;
	FileInputStream fis; 
	Properties prop;
	ExtentHtmlReporter ml;
	ExtentReports extent;
	PomClass pom;
	ExtentTest t;
  @Test(dataProvider = "one")
  public void f(String from, String to, String Fname, String Lname, String num, String email ) throws InterruptedException {
	  WebElement src=driver.findElement(By.name("source"));
	  src.sendKeys(from);
	  Actions act=new Actions(driver);
	 act.moveToElement(src).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).click().build().perform();
	  WebElement dstn=driver.findElement(By.name("destination"));
	  dstn.sendKeys((to));
	  act.moveToElement(dstn).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).click().build().perform();
	  driver.findElement(By.xpath(prop.getProperty("departcal"))).click();
	  driver.findElement(By.xpath(prop.getProperty("returncal"))).click();
	  driver.findElement(By.id("search-flight-btn")).click();
	  Thread.sleep(5000);
	  if(driver.findElement(By.id("search-flight-btn")).isDisplayed())
	  t.log(Status.FAIL, "Enter valid details to search");
	  else
	  { t.log(Status.PASS, "You have successfully searched");
	  WebDriverWait wt=new WebDriverWait(driver, 10);
	  wt.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(prop.getProperty("filtertab"))));
	  driver.findElement(By.xpath("//*[@id='searchResultContainer']/div[7]/div/div[3]/div[1]/div[3]/div[2]/button")).click();
	  pom.TwoPersondetails(Fname, Lname, num, email);
	  Thread.sleep(7000);
	  driver.findElement(By.id("makePayCTA")).click();
	  Thread.sleep(5000);
	  if(driver.findElement(By.xpath("//div[@class='fltFareRules']")).isDisplayed())
		  t.log(Status.FAIL, "Not on payments page");
	  else
	  { t.log(Status.PASS, "You are payments page");
	  driver.findElement(By.id("confirmProceedPayBtn")).click();
	  }
	  }
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
		ml=new ExtentHtmlReporter("./reports/onewayvalid.html");
		extent=new ExtentReports();
		extent.attachReporter(ml);
		t=extent.createTest("One Way Flight Booking with valid credentials");
		pom=new PomClass(driver, fis, prop, t);
  }

  @AfterMethod
  public void afterMethod() {
	  extent.flush();
	  
  }


  @DataProvider
  public Object[][] one() {
    return new Object[][] {
      new Object[] { "Varanasi", "Delhi", "Shikhar", "Singh", "9506707875", "shikhardlw@gmail.com"  },
      new Object[] { "4234234", "$%$%#3", "Shikhar", "Singh", "9506707875", "shikhardlw@gmail.com" },
      new Object[] { "Varanasi", "Delhi", "4234242%%^", "S*&^34234", "9506707875", "shikhardlw@gmail.com"  },
      new Object[] { "Varanasi", "Delhi", "Shikhar", "Singh", "dsdh54534", "shikhar54345##."  },
      new Object[] { "Varaansi", "Delhi", "", "", "", ""  },
    };
  }
}
