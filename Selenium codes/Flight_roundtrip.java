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

public class Flight_roundtrip {
	WebDriver driver;
	FileInputStream fis; 
	Properties prop;
	ExtentHtmlReporter ml;
	ExtentReports extent;
	ExtentTest t;
	PomClass pom;
  @Test(dataProvider = "dp")
  public void f(String from, String to, String Fname, String Lname, String num, String email) throws InterruptedException {
	  ExtentTest t=extent.createTest("Round Trip report with blank as details in name and number of travellers");
	  driver.findElement(By.xpath("//label[@for='round-trip']")).click();
	  WebElement src=driver.findElement(By.name("source"));
	  src.sendKeys((from));
	  Actions act=new Actions(driver);
	  act.moveToElement(src).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).click().build().perform();
	  WebElement dstn=driver.findElement(By.name("destination"));
	  dstn.sendKeys((to));
	  act.moveToElement(src).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).click().build().perform();
	  driver.findElement(By.xpath(prop.getProperty("departcal1"))).click();
	  driver.findElement(By.xpath("//*[@id='depart-cal']/div[4]/div[2]/div[5]/div[4]")).click();
	  driver.findElement(By.xpath(prop.getProperty("returncal1"))).click();
	  driver.findElement(By.xpath("//*[@id='return-cal']/div[3]/div[2]/div[5]/div[5]")).click();
	  driver.findElement(By.xpath("/html/body/div[4]/div[3]/div/form/div[4]/div[4]/div[1]/div/div[3]")).click();
	  driver.findElement(By.id("search-flight-btn")).click();
	  Thread.sleep(5000);
	  if(driver.findElement(By.id("search-flight-btn")).isDisplayed())
	  t.log(Status.FAIL, "Enter valid details to search");
	  else
	  { t.log(Status.PASS, "You have successfully searched");
	  WebDriverWait wt=new WebDriverWait(driver, 10);
	  wt.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(prop.getProperty("filtertab"))));
	  driver.findElement(By.xpath("//*[@id='searchResultContainer']/div[5]/div[1]/div[8]/div[1]/div[2]/div[2]/button")).click();
	  driver.findElement(By.xpath("/html/body/div[12]/div/div/div[5]/div[2]/div[15]/div[1]/div[2]/div[2]/button")).click();
	  driver.findElement(By.xpath("//div[@class='bookCTA return']")).click();
	  wt.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("repriceMessageBox")));
	  driver.findElement(By.id("repiceContBook")).click();
	  pom.TwoPersondetails(Fname, Lname, num, email);
	  driver.findElement(By.id("msgInfoChkBox_label")).click();
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
		pom=new PomClass(driver, fis, prop, t);
	  System.setProperty(prop.getProperty("key"),prop.getProperty("chrome"));
		driver = new ChromeDriver();
		driver.get(prop.getProperty("url"));
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.findElement(By.id(prop.getProperty("nothanks"))).click();
		ml=new ExtentHtmlReporter("./reports/RoundTrip4.html");
		extent=new ExtentReports();
		extent.attachReporter(ml);
		t=extent.createTest("Round Trip Flight Booking with valid credentials");
		
  }

  @AfterMethod
  public void afterMethod() {
	  driver.close();
	  extent.flush();

  }


  @DataProvider
  public Object[][] dp() {
    return new Object[][] {
    	new Object[] { "Varanasi", "Delhi", "Shivang", "Singh", "9335618966", "shivangsinghblw@gmail.com"  },
        new Object[] { "342332dsdsa", "&^&sads4", "Shikhar", "Singh", "9506707875", "shikhardlw@gmail.com" },
        new Object[] { "Varanasi", "Delhi", "^*ds35ffd", "Singh", "95456fdder%%", "shikhardlw@gmail.com"  },
        new Object[] { "Varaansi", "Delhi", "", "", "", ""  },
    };
  }
}
