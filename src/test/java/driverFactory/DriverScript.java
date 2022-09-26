package driverFactory;


import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commanFunctions.FunctionLibrary;
import utilites.ExcelFileUtil;

public class DriverScript {
WebDriver driver;
String inputpath="C:\\ECPLLISE2022\\ERP_Maven\\TestInput\\DataEngine.xlsx";
String outputpath="C:\\ECPLLISE2022\\ERP_Maven\\TestOutput\\HybridResult.xlsx";
ExtentReports report;
ExtentTest test;

@Test
public void starttest()throws Throwable
{
	
//creating excel object to access excel utilities
	ExcelFileUtil xl=new ExcelFileUtil(inputpath);
	//iterate all rows in master test cases
	for(int i=1;i<=xl.rowCount("MasterTestCase");i++)
	{
		String moduleStatus="";
		if(xl.getCellData("MasterTestCase", i, 2).equalsIgnoreCase("Y"))
		{
			//read  in testcases cell
			String TCModule=xl.getCellData("MasterTestCase", i, 1);
			report=new ExtentReports("./ExtentReports/"+TCModule+FunctionLibrary.generatDate()+".html");
		
		//itrate all rows in TCModule
			for(int j=1;j<=xl.rowCount(TCModule);j++)
			{
				test=report.startTest(TCModule);
				test.assignAuthor("mogal", "juinor tester");
				
				//read cells in TCModule
				String Description=xl.getCellData(TCModule, j, 0);
				String ObjectType=xl.getCellData(TCModule, j, 1);
				String LocatorName=xl.getCellData(TCModule, j, 2);
				String LocatorValue=xl.getCellData(TCModule, j, 3);
				String TestData=xl.getCellData(TCModule, j, 4);
				try {
					if(ObjectType.equalsIgnoreCase("StartBrower"))
					{
						driver=FunctionLibrary.startBrowser();
						test.log(LogStatus.INFO, Description);
					}
					else if(ObjectType.equalsIgnoreCase("appurl"))
					{
						FunctionLibrary.appUrl(driver);
						test.log(LogStatus.INFO,Description);
					}
					else if(ObjectType.equalsIgnoreCase("WaitForElement"))
					{
                    FunctionLibrary.waitForElement(driver, LocatorName, LocatorValue, TestData);
                   test.log(LogStatus.INFO, Description); 
					}
					else if(ObjectType.equalsIgnoreCase("typeAction"))
					{
						FunctionLibrary.typeAction(driver, LocatorName, LocatorValue, TestData);
						test.log(LogStatus.INFO, Description);
					}
					else if(ObjectType.equalsIgnoreCase("ClickAction"))
					{
						FunctionLibrary.clickAction(driver, LocatorName, LocatorValue);
						test.log(LogStatus.INFO,Description);
					}
					else if(ObjectType.equalsIgnoreCase("validateTitle"))
					{
					FunctionLibrary.validateTitle(driver, TestData);
					test.log(LogStatus.INFO,Description);
					}
					else if(ObjectType.equalsIgnoreCase("CloseBrowser"))
					{
						FunctionLibrary.closeBrowser(driver);
						test.log(LogStatus.INFO, Description);
						
					}
					else if(ObjectType.equalsIgnoreCase("captureData"))
					{
					FunctionLibrary.captureData(driver, LocatorName, LocatorValue);
					test.log(LogStatus.INFO, Description);
					
					}
					else if(ObjectType.equalsIgnoreCase("supplierTable"))
					{
					FunctionLibrary.supplierTable(driver, TestData);
					test.log(LogStatus.INFO, Description);
					
				 }
				//write as pass into status cell in TCModule
					xl.setCellData(TCModule, j, 5, "pass",outputpath);
					moduleStatus="True";
					test.log(LogStatus.INFO, "pass");
					
				}catch (Exception e) 
				{
					//write as fail into status cell in TCModule
					xl.setCellData(TCModule, j, 5, "fail", outputpath);
					moduleStatus="false";
					test.log(LogStatus.INFO, "fail");
					
				}}
			if(moduleStatus.equalsIgnoreCase("True"))
			{
				//write as pass into master testcasesheet
				xl.setCellData("MasterTestCase", i, 3,"pass", outputpath);
			}
			if(moduleStatus.equalsIgnoreCase("false"))
			{
		//write as fail into master testcasesheet
	xl.setCellData("MasterTestCase", i, 3, "fail", outputpath);
			}
		report.endTest(test);
		report.flush();
	         }
			else
			{
				//write as blocked in status cell which are flag to N
              xl.setCellData("MasterTestCase", i, 3, "blocked",outputpath);								
			}
	}}}









