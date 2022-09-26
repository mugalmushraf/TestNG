package commanFunctions;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utilites.PropertyUtileFlie;

public class FunctionLibrary {
public static WebDriver driver;
//method for launch browser
public static WebDriver startBrowser() throws Throwable
{
	if(PropertyUtileFlie.getValueForKey("Browser").equalsIgnoreCase("chrome"))
	{
		System.setProperty("Webdriver.chromedriver","chromedriver.exe");
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}
   else if(PropertyUtileFlie.getValueForKey("Browser").equalsIgnoreCase("firefox"))
	{
		driver = new FirefoxDriver();
		driver.manage().deleteAllCookies();
	}
	else
	{
		System.out.println("Browser Value is Not Matching");
	}
	return driver;
}
//method for launch url
public static void appUrl(WebDriver driver)throws Throwable
{
	driver.get(PropertyUtileFlie.getValueForKey("url"));
}
//method for wait for element
public static void waitForElement(WebDriver driver,String locatorName,String LocatorValue,String testData)
{
	WebDriverWait mywait = new WebDriverWait(driver, Integer.parseInt(testData));
	if(locatorName.equalsIgnoreCase("name"))
	{
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
	}
	else if(locatorName.equalsIgnoreCase("xpath"))
	{
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
	}
	else if(locatorName.equalsIgnoreCase("id"))
	{
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
	}
	else
	{
		System.out.println("UInable to Execute waitforelement method");
	}
			
}
//method for textbox
public static void typeAction(WebDriver driver,String locatorname,String locatorvalue,String testData)
{
	if(locatorname.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(locatorvalue)).clear();
		driver.findElement(By.id(locatorvalue)).sendKeys(testData);
	}
	else if(locatorname.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(locatorvalue)).clear();
		driver.findElement(By.xpath(locatorvalue)).sendKeys(testData);
	}
	else if(locatorname.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(locatorvalue)).clear();
		driver.findElement(By.name(locatorvalue)).sendKeys(testData);
	}
	else
	{
		System.out.println("Unable to Execute typeAction method");
	}
}
//method for buttons
public static void clickAction(WebDriver driver,String locatorname,String locatorvalue)
{
	if(locatorname.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(locatorvalue)).click();
	}
	else if(locatorname.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(locatorvalue)).click();
	}
	else if(locatorname.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(locatorvalue)).sendKeys(Keys.ENTER);
	}
	else
	{
		System.out.println("Unbale to execute click Action method");
	}
}
//method for validate title
public static void validateTitle(WebDriver driver,String testData)
{
	String expectedtitle =driver.getTitle();
	try {
	Assert.assertEquals(testData, expectedtitle,"Title is Not Matching");
	}catch(Throwable t)
	{
		System.out.println(t.getMessage());
	}
}
//method for close browsewr
public static void closeBrowser(WebDriver driver)
{
	driver.close();
}
//method for capture supplier number
public static void captureData(WebDriver driver,String LocatorName,String LocatorValue)throws Throwable
{
	String sNumber ="";
	if(LocatorName.equalsIgnoreCase("id"))
	{
		sNumber =driver.findElement(By.id(LocatorValue)).getAttribute("value");
	}
	else if(LocatorName.equalsIgnoreCase("xpath"))
	{
		sNumber =driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
	}
	//create file
	File f = new File("C:\\ECPLLISE2022\\Hybrid_Framework\\CaptureData\\supplier.txt");
	FileWriter fw = new FileWriter(f);
	BufferedWriter bw = new BufferedWriter(fw);
	bw.write(sNumber);
	bw.flush();
	bw.close();
}

//method supplier table vaiidation
public static void supplierTable(WebDriver driver,String testData) throws Throwable
{
	
	//read supplier number from note pad
	FileReader fr=new FileReader("C:\\ECPLLISE2022\\Hybrid_Framework\\CaptureData\\supplier.txt");
	BufferedReader br =new BufferedReader(fr);
	String ExpectedSnumber=br.readLine();
	//convert test data cell in to integer type
	int CloumnNum= Integer.parseInt(testData);

	if(!driver.findElement(By.xpath(PropertyUtileFlie.getValueForKey("search_textbox"))).isDisplayed());
//click search panel butt
	driver.findElement(By.xpath(PropertyUtileFlie.getValueForKey("search_panel"))).click();
	Thread.sleep(4000);
	driver.findElement(By.xpath(PropertyUtileFlie.getValueForKey("search_textbox"))).clear();
	Thread.sleep(4000);
	driver.findElement(By.xpath(PropertyUtileFlie.getValueForKey("search_textbox"))).sendKeys(ExpectedSnumber);
	Thread.sleep(4000);
	driver.findElement(By.xpath(PropertyUtileFlie.getValueForKey("search_button"))).click();
	Thread.sleep(4000);
	//capture supplier number from table
	String ActualsNumber= driver.findElement(By.xpath("[@id='tbl_a_supplierlist']/tbody/tr[1]/td[6]/div/span/span")).getText();
	System.out.println(ExpectedSnumber+" "+ActualsNumber);
	Assert.assertEquals(ExpectedSnumber, ActualsNumber, "supplier number is not Matching");
}
//method for dataformata
public static String generatDate()
{
	Date date= new Date(0);
DateFormat df=new SimpleDateFormat("YYYY_MM_dd");
return df.format(date);
}




//method for captureData1 cumster number
public static void captureData1(WebDriver driver,String LocaterName,String LocatarValue)throws Throwable
{
String sNumber="";
{
	if(LocaterName.equalsIgnoreCase("id"))
	{
		sNumber=driver.findElement(By.id(LocatarValue)).getAttribute("value");
}
	else if(LocaterName.equalsIgnoreCase("xapth"))
{
         sNumber=driver.findElement(By.xpath(LocatarValue)).getAttribute("value");
}
//create file
	File f=new File("./captureData1/customer.txt");
	FileWriter fw=new FileWriter(f);
	BufferedWriter bw=new BufferedWriter(fw);
	bw.write(sNumber);
	bw.flush();
	bw.close();
}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
		
}

































