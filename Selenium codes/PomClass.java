import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class PomClass {
WebDriver driver;
FileInputStream fis; 
Properties prop;
ExtentTest tt;
public PomClass(WebDriver driver, FileInputStream fis, Properties prop, ExtentTest tt) throws IOException
{
	this.driver=driver;
	this.fis=fis;
	this.prop=prop;
	prop.load(fis);
	this.tt=tt;
}
public void OpenApp()
{
	driver.get(prop.getProperty("url"));
	driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	driver.manage().window().maximize();
	driver.findElement(By.id(prop.getProperty("nothanks"))).click();
}
public void HotelSearch(String dest)
{
	 Actions act=new Actions(driver);
	  WebElement dstn=driver.findElement(By.xpath("//*[@id='destination']"));
	  dstn.sendKeys(dest);
	  act.moveToElement(dstn).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).click().build().perform();
	  driver.findElement(By.xpath("//div[@class='panel u_floatL']/div/div[2]/div/div")).click();
	  driver.findElement(By.xpath("//*[@id='depart-cal']/div[4]/div[2]/div[5]/div[4]")).click();
	  driver.findElement(By.xpath("//div[@class='panel u_floatL']/div/div[3]/div/div")).click();
	  driver.findElement(By.xpath("//*[@id='return-cal']/div[3]/div[2]/div[5]/div[5]")).click();
	  driver.findElement(By.xpath("//div[@class='hotelRoomDropDown  js-room']/span[3]")).click();
	  driver.findElement(By.xpath("//*[@id='room1']/div[2]/div/div[1]")).click();
	  driver.findElement(By.xpath("//div[@class='roomConfigFooter']/div[2]")).click();
	  driver.findElement(By.xpath("//div[@class='search-hotel u_vertAlignMiddle u_floatR']")).click();
}
public void login(String e, String p)
{
	  driver.findElement(By.xpath(prop.getProperty("signin"))).click();
	  tt.log(Status.INFO, "Checking the login credentials");
	  driver.findElement(By.id("loginIdText")).sendKeys(e);
	  driver.findElement(By.id("passwordText")).sendKeys(p);
	  driver.findElement(By.id("loginValidate")).click();
}
public void register(String e, String pass, String n, String num)
{
	  driver.findElement(By.xpath(prop.getProperty("signin"))).click();
	  driver.findElement(By.xpath("//*[@id='SignInContent']/div[1]/div[1]/div[2]/span/label/span")).click();
	  driver.findElement(By.id("emailIdSignUp")).sendKeys(e);
	  driver.findElement(By.id("passwordSignUp")).sendKeys(pass);
	  driver.findElement(By.id("nameSignUp")).sendKeys(n);
	  driver.findElement(By.id("mobileNoSignUp")).sendKeys(num);
	  tt.log(Status.INFO, "Clicked on sign up button");
	  //clicking on register button
	  driver.findElement(By.id("signUpValidate")).click();
}
public void TwoPersondetails(String Fname, String Lname, String num, String email)
{
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
}
}
