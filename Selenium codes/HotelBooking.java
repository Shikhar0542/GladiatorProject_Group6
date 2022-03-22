import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

public class HotelBooking {
	WebDriver driver;
	FileInputStream fis; 
	Properties prop;
	ExtentHtmlReporter ml;
	ExtentReports extent;
	PomClass pom;
	ExtentTest t;
  @Test(dataProvider = "dp")
  public void f(String dest, String Fname, String Lname, String pan, String num, String email ) throws InterruptedException {
	  ExtentTest t=extent.createTest("Hotel Report with blank Guest details in the page");
	  driver.findElement(By.id("Hotels")).click();
	  WebDriverWait wt=new WebDriverWait(driver, 20);
	  wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='destination']")));
	  pom.HotelSearch(dest);
	  Thread.sleep(5000);
	  if(driver.findElement(By.xpath("//div[@class='search-hotel u_vertAlignMiddle u_floatR']")).isDisplayed())
	  t.log(Status.FAIL, "Enter valid details to search");
	  else
	  { t.log(Status.PASS, "You have successfully searched");
	  wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='filterHotel isFixed']")));
	  driver.findElement(By.xpath("//*[@id='4']/div[3]/div[3]/div[1]")).click();
	  wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='roomHotel4']/div/div")));
	  driver.findElement(By.xpath("//*[@id='roomHotel4']/div/div/div[3]/div[2]")).click();
	  Select p=new Select(driver.findElement(By.name("Room0AdultTitle0")));
	  p.selectByIndex(2);
	  driver.findElement(By.name("Room0AdultFirstName0")).sendKeys(Fname);
	  driver.findElement(By.name("Room0AdultLastName0")).sendKeys(Lname);
	  driver.findElement(By.name("panNumber")).sendKeys(pan);
	  Select q=new Select(driver.findElement(By.id("selectSSR")));
	  q.selectByIndex(3);
	  driver.findElement(By.name("WOMEN_TRAVELLER")).sendKeys("Please take care of my mom");
	  driver.findElement(By.name("contactPhone")).sendKeys(num);
	  driver.findElement(By.name("contactEmail")).sendKeys(email);
	  driver.findElement(By.id("read_terms_label")).click();
	  driver.findElement(By.id("makePayCTA")).click();
	  Thread.sleep(5000);
	  if(driver.findElement(By.id("read_terms_label")).isDisplayed())
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
	  ml=new ExtentHtmlReporter("./reports/hotel4.html");
		extent=new ExtentReports();
		extent.attachReporter(ml);
		t=extent.createTest("Hotel Booking with valid credentials");
		pom=new PomClass(driver, fis, prop, t);

  }

  @AfterMethod
  public void afterMethod() {
	  extent.flush(); 
  }


  @DataProvider
  public Object[][] dp() {
    return new Object[][] {
      new Object[] { "Varanasi", "Shikhar", "Singh", "NXTPS2622M", "9506707875", "shikhardlw@gmail.com" },
      new Object[] { "hsdysd77%^", "Shikhar", "Singh", "NXTPS2622M", "9506707875", "shikhardlw@gmail.com" },
      new Object[] { "Varanasi", "Shikhar0542", "Singh", "NXTPS2**M", "9506", "shikhardlw@gmail.com" },
      new Object[] { "Varanasi", "", "Singh", "", "", "" },
    };
  }
}
