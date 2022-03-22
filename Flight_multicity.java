 import org.testng.annotations.Test;
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

public class Flight_multicity {
	WebDriver driver;
	FileInputStream fis; 
	Properties prop;
  @Test(dataProvider = "dp")
  public void f(String from, String to, String Fname, String Lname, String num, String email) {
	  driver.findElement(By.xpath("//label[@for='multi-city']")).click();
	  WebElement src0=driver.findElement(By.name("source_0"));
	  src0.sendKeys((from));
	  Actions act=new Actions(driver);
	  act.moveToElement(src0).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).click().build().perform();
	  WebElement dstn0=driver.findElement(By.name("destination_0"));
	  dstn0.sendKeys((to));
	  act.moveToElement(dstn0).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).click().build().perform();
	  driver.findElement(By.xpath("//*[@id='multi-city-panel']/div[2]/div[3]/div/div")).click();
	  driver.findElement(By.xpath("//*[@id='depart-cal-0']/div[4]/div[2]/div[5]/div[4]")).click();
	  driver.findElement(By.id("multi-city-label-1")).click();
	  WebElement src1=driver.findElement(By.name("source_1"));
	  src1.sendKeys(("Mumbai"));
	  act.moveToElement(src1).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).click().build().perform();
	  WebElement dstn1=driver.findElement(By.name("destination_1"));
	  dstn1.sendKeys(("Bhopal"));
	  act.moveToElement(dstn1).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).click().build().perform();
	  driver.findElement(By.xpath("//*[@id='multi-city-panel']/div[3]/div[3]/div/div")).click();
	  driver.findElement(By.xpath("//*[@id='depart-cal-1']/div[3]/div[2]/div[5]/div[5]")).click();
	  driver.findElement(By.xpath("//div[@class='counter-element adult']/div/div[3]")).click();
	  driver.findElement(By.id("search-flight-btn")).click();
	  WebDriverWait wt=new WebDriverWait(driver, 10);
	  wt.until(ExpectedConditions.visibilityOfElementLocated(By.id("filterBody")));
	  driver.findElement(By.xpath("//*[@id='searchResultContainer']/div[4]/div[1]/div[3]/div[1]/div[2]/div[2]/button")).click();
	  driver.findElement(By.xpath("//*[@id='searchResultContainer']/div[4]/div[2]/div[3]/div[1]/div[2]/div[2]/button")).click();
	  driver.findElement(By.xpath("//div[@class='bookCTA return']")).click();
	  Select p=new Select(driver.findElement(By.id("adult1Title")));
	  p.selectByIndex(1);
	  driver.findElement(By.id("adult1FirstName")).sendKeys(Fname);
	  driver.findElement(By.id("adult1Surname")).sendKeys(Lname);
	  driver.findElement(By.xpath("//*[@id='adult2']/p/span[3]")).click();
	  Select q=new Select(driver.findElement(By.id("adult2Title")));
	  q.selectByIndex(2);
	  driver.findElement(By.id("adult2FirstName")).sendKeys("Rekha");
	  driver.findElement(By.id("adult2Surname")).sendKeys("Singh");
	  driver.findElement(By.name("contactPhone")).sendKeys(num);
	  driver.findElement(By.name("contactEmail")).sendKeys(email);
	  driver.findElement(By.id("msgInfoChkBox_label")).click();
	  wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), '15,490')]")));
	  driver.findElement(By.id("makePayCTA")).click();
	  driver.findElement(By.id("confirmProceedPayBtn")).click();
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
		
  }

  @AfterMethod
  public void afterMethod() {
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
