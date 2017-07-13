package config;
import static executionEngine.DriverScript.OR;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.w3.x2000.x09.xmldsig.impl.TransformDocumentImpl;

import executionEngine.DriverScript;
import utility.ExcelUtils;
import utility.Log;
import utility.SqlQuery;
import utility.Stats;

public class ActionKeywords {

	public static WebDriver driver;
	public static String Table_row_date;
	public static String Table_col_xpath;
	public static String newDateString ;
	public static String sample_value;
	public static String sample_value_ly;
	public static String smaple_value_final;
	public static String col;
	public static String row;
	public static String table_row_xpath;
	public static String trx;
	public static boolean nextstep;
	Stats st=new Stats();
	public static String dropdown_store;
	public static String table_name;
	public static boolean goal_percent=false;
	public static String final_db_value=null;
	public static String Time_Frame = "DA";
	public static String location_nbr;
	public static boolean ly=false;

	public static void openBrowser(String object, String data) throws IOException
	{		
		try{
			if(data.equals("InternetExplorer"))
			{
				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability("requireWindowFocus", true);
				System.setProperty("webdriver.ie.driver", "C:\\Users\\akatiyj3\\Desktop\\Automation\\Jars\\IEDriverServer_x64_3.3.0");
				driver=new InternetExplorerDriver(capabilities);
				Log.info("Opening Browser  ");
				driver.manage().window().maximize() ;
			}
			else if (data.equals("chrome"))
			{
				Log.info("KPI application is compatible with IE only   ");
			}
			int implicitWaitTime=(10);
			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
			capture_screenshot(object);
		}
		catch (Exception e)
		{
			capture_screenshot(object);
			Log.error("Not able to open Browser ---   " + e.getMessage());
			DriverScript.bResult = false;
		}
			try{
				Class.forName(Constants.JDBC_DRIVER);
				Constants.conn = DriverManager.getConnection(Constants.DB_URL, Constants.USER, Constants.PASS);
				Constants.stmt = Constants.conn.createStatement();
				Log.info("Connecting to database  ");

			} 
			catch (SQLException se)
			{
				se.printStackTrace();
				Log.error(se.getMessage());
			} 
			catch (Exception e1)
			{
				e1.printStackTrace();
				Log.error(e1.getMessage());
			} 
		}
	
	public static void navigate(String object, String data) throws IOException{	
		try{
			Log.info("Navigating to URL ");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(data);
			capture_screenshot(object);
			Log.info("Navigating To  " + data);
			DriverScript.bResult = true;
			Thread.sleep(2000);
		}catch(Exception e){

			capture_screenshot(object);
			Log.error("Not able to navigate ---  " + e.getMessage());
			DriverScript.bResult = false;
		}
	}

	public static void click(String object, String data) throws IOException{
		try{
		
			driver.findElement(By.xpath(OR.getProperty(object))).click();
			capture_screenshot(object);
			Thread.sleep(3000);
		}
		catch(Exception e){
			capture_screenshot(object);
			Log.error("Not able to click --- " + e.getMessage());
			DriverScript.bResult = false;

		}
	}
	public static void input(String object, String data) throws IOException{
		if(object.equals("txtbx_number"))
			
		{
			location_nbr=data;
		}
		try{
			if ((OR.getProperty(object).indexOf("html"))>0)
			{
			driver.findElement(By.xpath(OR.getProperty(object))).click();
			driver.findElement(By.xpath(OR.getProperty(object))).clear();
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			
		}		
			else
			{
				Thread.sleep(1000);
				driver.findElement(By.name(OR.getProperty(object))).click();
				driver.findElement(By.name(OR.getProperty(object))).clear();
				driver.findElement(By.name(OR.getProperty(object))).sendKeys(data + "\n");
			}
			}
		catch(Exception e){
			capture_screenshot(object);
			Log.error("Not able to enter " +object+ e.getMessage());
			DriverScript.bResult = false;
		}
	}


	public static void compare_sample_value(String object, String data) throws IOException, SQLException
	{ 
	nextstep=true;
	String first_part =OR.getProperty(object) +"[";	
	String second_part = "]/td[";		 
	String third_part = "]";
	
	int i=1;
	
	int Row_count = driver.findElements(By.xpath(OR.getProperty(object))).size();	
	
		if (object.contentEquals("total_fe_sales"))
		{
			 Table_col_xpath=first_part+i+second_part+2+third_part;
			 //JavascriptExecutor jse = (JavascriptExecutor) driver;
			 //jse.executeScript("arguments[0].style.border='3px solid red'",driver.findElement(By.xpath(Table_col_xpath)));
			 trx=second_part+2+third_part;
			 Stats.getMetrics(dropdown_store, object);
			 ly=false;
		}
		else if (object.contentEquals("total_fe_sales_ly"))
		{
			 Table_col_xpath=first_part+i+second_part+3+third_part;
			 //JavascriptExecutor jse = (JavascriptExecutor) driver;
			 //jse.executeScript("arguments[0].style.border='3px solid red'",driver.findElement(By.xpath(Table_col_xpath)));
			 trx=second_part+3+third_part;
			 Stats.getMetrics(dropdown_store, object);
			 ly=true;
		}
		else if(object.contentEquals("ss_sales"))
		{
			 Table_col_xpath=first_part+i+second_part+4+third_part;
			 trx=second_part+4+third_part;
			 Stats.getMetrics(dropdown_store, object);
			 ly=false;
		}
		else if(object.contentEquals("ss_sales_ly"))
		{
			 Table_col_xpath=first_part+i+second_part+5+third_part;
			 trx=second_part+5+third_part;
			 Stats.getMetrics(dropdown_store, object);
			 ly=true;
		}
		else if(object.contentEquals("cig_sales"))
		{
			 Table_col_xpath=first_part+i+second_part+6+third_part;
			 trx=second_part+6+third_part;
			 Stats.getMetrics(dropdown_store, object);
			 ly=false;
		}
		else if(object.contentEquals("cig_sales_ly"))
		{
			 Table_col_xpath=first_part+i+second_part+7+third_part;
			 trx=second_part+7+third_part;
			 Stats.getMetrics(dropdown_store, object);
			 ly=true;
		}
		else if(object.contentEquals("liq_sales"))
		{
			 Table_col_xpath=first_part+i+second_part+8+third_part;
			 trx=second_part+8+third_part;
			 Stats.getMetrics(dropdown_store, object);
			 ly=false;
		}
		else if(object.contentEquals("liq_sales_ly"))
		{
			 Table_col_xpath=first_part+i+second_part+9+third_part;
			 trx=second_part+9+third_part;
			 Stats.getMetrics(dropdown_store, object);
			 ly=true;
		}
		else if (object.contentEquals("beauty_goal"))
		{
			 Table_col_xpath=first_part+i+second_part+8+third_part;
			 trx=second_part+8+third_part;
			 Stats.getMetrics(dropdown_store, object);
			 ly=false;
			 goal_percent=false;
		}
		else if (object.contentEquals("percent_goal"))
		{
			 Table_col_xpath=first_part+i+second_part+10+third_part;
			 trx=second_part+10+third_part;
			 Stats.getMetrics(dropdown_store, object);
			 goal_percent=true;
			 ly=false;
			 
		}
		
	 
		for ( i=1; i<Row_count;)
		{	
			if (nextstep==true)
			{
			table_row_xpath=first_part+i+second_part+1+third_part;
			scrollingToElementofAPage(table_row_xpath);
							
						Table_row_date =driver.findElement(By.xpath(table_row_xpath)).getText();
						
						
								if (Table_row_date.indexOf("WkEnd") > 0)
								{
									Time_Frame= "WK";
								}
								else if(Table_row_date.indexOf("PdEnd") > 0)
								{
									Time_Frame= "PR";
								}
								else if(Table_row_date.indexOf("MdEnd") > 0)
								{
									Time_Frame= "MD";
								}
								else
								{
									Time_Frame= "DA";
								}
								
						convert_date( Table_row_date);
		if((newDateString !=null && newDateString.equals(data)))
						{
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].style.border='3px solid red'",driver.findElement(By.xpath(table_row_xpath)));
			
						
						String final_xpath = first_part+i+trx;	
						Constants.Table_data = driver.findElement(By.xpath(final_xpath)).getText();
							
						if (Constants.Table_data.isEmpty())
							{
							Log.info(Constants.Metric + " Sales is empty");
							ly=false;
							break;
							}
							else
							{
								
								jse.executeScript("arguments[0].style.border='3px solid red'",driver.findElement(By.xpath(final_xpath)));
								
								//Log.info(Constants.Metric + Constants.Table_data);
								compare(data, Constants.Table_data, Constants.Metric);
								
							}
						
						}	
						
						
		 else if (newDateString==null)
		{
			
			i=i+1;
		}
		 else if(newDateString != data)
		 {
			 i=i+1;
		 }
						
	}
			else 
			{
				break;
			}
		}
		
		capture_screenshot(object);		
	}

	public static String convert_date(String Table_row_header){
		newDateString=null;
		String ui_date=Table_row_header ;
		int ret=0;
		DateFormat df = new SimpleDateFormat("EEE, MMM dd, yyyy"); 
		df.setLenient(false);
		try
		{
			df.parse(ui_date.trim());
			ret=1;
		}
		
		catch (ParseException pe) {
		    ret=0;  
		    }
		
	if(ret==1)
	{
		DateFormat formatted=new SimpleDateFormat("dd-MMM-yyyy");
		Date startDate;
		try {
			startDate = df.parse(ui_date);
			newDateString = formatted.format(startDate);
	}
		catch (ParseException e) {
	}
	}
	 return newDateString;
	}
	public static void compare(String data, String Table_data, String Metric) throws SQLException , NullPointerException
	{
		try 	
		{ 
			
			if(goal_percent==true)
			{
				smaple_value_final = Table_data.replaceAll("%", "");
				double d;
				double de;
				SqlQuery.getquery(table_name, Metric, data, location_nbr, Time_Frame);
				System.out.println(SqlQuery.sql);
				Log.info("Executing the query-- " + SqlQuery.sql);
				ResultSet rs = Constants.stmt.executeQuery(SqlQuery.sql);
				while (rs.next()) {
				sample_value = rs.getString(1);
				d = Double.parseDouble(sample_value); 
				de= d*100;
				int i=(int)de;
				final_db_value= Integer.toString(i);
				Log.info("Goal Percent from front end for  "+ Metric +" for  "+ data + "  "+ smaple_value_final);
				Log.info("                                        ");
				Log.info("Goal Percent from database(dev) for "+ Metric+ "  for  "+  data+ "  "+ final_db_value);
				Log.info("                                        ");
				}
				
			}
			else if (ly==true)
			{
				smaple_value_final = Table_data.replaceAll("%","");
				double d;
				double d_ly;
				double de;
				SqlQuery.getquery(table_name, Metric, data, location_nbr, Time_Frame);
				//System.out.println(SqlQuery.sql);
				System.out.println(SqlQuery.sql_ly);
				Log.info("Executing the query-- " + SqlQuery.sql_ly);
				//ResultSet rs = Constants.stmt.executeQuery(SqlQuery.sql);
				ResultSet rs_ly= Constants.stmt.executeQuery(SqlQuery.sql_ly);
				//while (rs.next())
				//sample_value = rs.getString(1);
				while (rs_ly.next())
				sample_value_ly= rs_ly.getString(1);
				d = Double.parseDouble(sample_value); 
				d_ly=Double.parseDouble(sample_value_ly); 
				de=((d-d_ly)/d_ly)*100;
				DecimalFormat df = new DecimalFormat("#.#");
				final_db_value=df.format(de);
				Log.info("LY % from front end for  "+ Metric +" for  "+ data + "  "+ smaple_value_final);
				Log.info("                                        ");
				Log.info("LY % from database(dev) for "+ Metric+ "  for  "+  data+ "  "+ final_db_value);
				Log.info("                                        ");
			}
			else
			{ 
			
				smaple_value_final = Table_data.replaceAll(",", "").substring(1);			
		double d;
		double de;
		
		SqlQuery.getquery(table_name, Metric, data,location_nbr, Time_Frame);
		System.out.println(SqlQuery.sql);
		Log.info("Executing the query-- " + SqlQuery.sql);
		ResultSet rs = Constants.stmt.executeQuery(SqlQuery.sql);
		while (rs.next()) {
		sample_value = rs.getString(1);
		d = Double.parseDouble(sample_value); 
		de= Math.round(d);
		int i=(int)de;
		final_db_value= Integer.toString(i);
		Log.info("Sample value from front end for  "+ Metric +" for  "+ data + "  "+ smaple_value_final);
		Log.info("                                        ");
		Log.info("Sample value from database(dev) for "+ Metric+ "  for  "+  data+ "  "+ final_db_value);
		Log.info("                                        ");
		}
			}
			
		
		
		if (final_db_value!=null)
		{
		if (final_db_value.equals(smaple_value_final)) {
		DriverScript.bResult = true;
		
		}
		else 
		{
			DriverScript.bResult=false;
		}
		}
		else 
		{
			DriverScript.bResult = false;
			nextstep=false;
		}

		nextstep=false;
		Thread.sleep(1000);
		}
	catch(Exception e)
	{
		DriverScript.bResult = false;
		nextstep=false;
	}
}

//	public static void waitFor(String object, String data) throws Exception{
//		try{
//			
//			Thread.sleep(2000);
//			DriverScript.bResult=true;
//		}
//		catch(Exception e){
//			capture_screenshot(object);
//			Log.error("Page Not loaded --- " + e.getMessage());
//			DriverScript.bResult = false;
//		}
//	}
	public static void verify_Links(String Object,String data) throws IOException

	{
		try
		{ 
			
		WebElement links = driver.findElement(By.xpath(OR.getProperty(Object)));
		Thread.sleep(5000);
		String link_name=driver.findElement(By.xpath(OR.getProperty(Object))).getText();
				links.click();
				
				
				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			    
		   
						if (driver.getTitle().equals("The webpage cannot be displayed ")) 
				{
					System.out.println("\"" + link_name + "\""
									+ " The webpage cannot be displayed.");
					Log.info("Link"+ link_name +"is not working"+ "The webpage cannot be displayed");
					DriverScript.bResult = false;
					capture_screenshot(Object);
					driver.navigate().back();
				} 
						
						else if(driver.getTitle().equals("404 Not Found"))
						{
							System.out.println("\"" + link_name + "\"is not working"
											+ " 404 Not Found Error.");
							Log.info("Link"+ link_name +"is not working"+ "404 Not Found Error.");
							DriverScript.bResult = false;
							capture_screenshot(Object);
							driver.navigate().back();
						} 
							
					else
								{
						if(driver.getPageSource().contains("No Data Found for the requested report.")||driver.getPageSource().contains("Database error for this report"))
						//if(driver.findElement(By.className("ErrorMess")).equals("No Data Found for the requested report.")||driver.findElement(By.xpath(".//html/body/div[2]/table/tbody/tr/td/div[2]/table/tbody/tr/td/div/div[2]/table/tbody/tr[2]/td")).getText().equals("Database error for this report"))
						{
							System.out.println("\"" + link_name + "\"is not working"
									+ "Data Issues.");
					Log.info("Link"+ link_name +"is not working"+ "Data Issues.");
					DriverScript.bResult = false;
					capture_screenshot(Object);
					driver.navigate().back();}
						
						else{	System.out.println("\"" + link_name + "\""
							        + " is working.");
							
							Log.info("Link "+ link_name+ "  is working");
							
 							DriverScript.bResult = true;
  							capture_screenshot(Object);
							driver.navigate().back();
							}
						 
								}
		  		}
		catch(Exception e){
			Log.error("Not able to verify links --- " + e.getMessage());
			DriverScript.bResult = false;
	}
 	}
	
	public static void verifyLinksTab(String Object,String data) throws IOException {
		
		String parentHandle = driver.getWindowHandle();
		
		try{  
			WebElement links = driver.findElement(By.xpath(OR.getProperty(Object)));
			Thread.sleep(5000);
			String link_name=driver.findElement(By.xpath(OR.getProperty(Object))).getText();
					links.click();
					Thread.sleep(5000);
					for (String winHandle : driver.getWindowHandles()) {
						Thread.sleep(5000);
					    driver.switchTo().window(winHandle); 
					}
										
					if(driver.getTitle().equals("The webpage cannot be displayed"))
										
					{
						System.out.println("\"" + link_name + "\"is not working"
										+ " The webpage cannot be displayed.");
						Log.info("Link"+ link_name +"is not working" +"The webpage cannot be displayed");
						DriverScript.bResult = false;
						capture_screenshot(Object);
						driver.close();
						driver.switchTo().window(parentHandle);
						
					} 
					
					else if(driver.getTitle().equals("This page can't be displayed"))
					{
						System.out.println("\"" + link_name + "\"is not working"
										+ " This page can't be displyed.");
						Log.info("Link"+ link_name +"is not working" +"This page cannot be displayed");
						DriverScript.bResult = false;
						capture_screenshot(Object);
						driver.close();
						driver.switchTo().window(parentHandle);
										} 
					
						else
									{ 
							
	       							System.out.println("\"" + link_name + "\""
								        + " is working.");
								
								Log.info("Link "+ link_name+ "  is working");
						
	 							DriverScript.bResult = true;
	  							capture_screenshot(Object);
	  							driver.close();
	  							driver.switchTo().window(parentHandle);
							} 
		}
		catch (Exception e){
			Log.error("Not able to verify links Tab--- " + e.getMessage());
			DriverScript.bResult = false;
		
		}
	}

	
	
	
	
	public static void select_dropdown(String object, String data) throws Exception
	{
		try
		{ 
			if(object.equals("drpdwn_Timeframe"))
			{

				Select dropdown= new Select(driver.findElement(By.xpath((OR.getProperty(object)))));
				dropdown.selectByVisibleText(data);
				DriverScript.bResult=true;
				Log.info("Timeframe "+ data+ "  is selected");
				
				Thread.sleep(1000);
			}

			else if (object.equals("drpdwn_Stores"))				
			{
				Select dropdown= new Select(driver.findElement(By.name((OR.getProperty(object)))));
				dropdown.selectByVisibleText(data);
				dropdown_store=data;
				DriverScript.bResult=true;
				Log.info("Stores Type  "+ data+ "  is selected");
				
				Thread.sleep(1000);
				
			}
			else if (object.equals("drpdwn_Location"))
			{
				Select dropdown= new Select(driver.findElement(By.name((OR.getProperty(object)))));
				dropdown.selectByVisibleText(data);
				DriverScript.bResult=true;
				Log.info("Location Type  "+ data+ "  is selected");
				table_name=data;
				
				Thread.sleep(1000);
			}

			else if (object.equals("drpdwn_Day"))
			{
				Select dropdown= new Select(driver.findElement(By.xpath((OR.getProperty(object)))));
				dropdown.selectByVisibleText(data);
				DriverScript.bResult=true;
				Log.info("Compare Type  " + data+ " is selected");
				
				Thread.sleep(1000);
			}
			else if(object.equals("drpdwn_Report"))
			{
				Select dropdown= new Select(driver.findElement(By.name((OR.getProperty(object)))));
				dropdown.selectByVisibleText(data); 
				DriverScript.bResult=true;
				Log.info("Report Type  "+ data+ " is selected");
				
				Thread.sleep(1000);
			}
			else if(object.equals("drpdwn_Department"))
			{
				Select dropdown= new Select(driver.findElement(By.name((OR.getProperty(object)))));
				dropdown.selectByVisibleText(data);
				DriverScript.bResult=true;
				Log.info("Department Type  "+ data+ " is selected");
				
				Thread.sleep(1000);
			}
			capture_screenshot(object);
		}
		

		catch(Exception e){
			Log.error(OR.getProperty(object)+  "  dropdown not working " + e.getMessage());
			DriverScript.bResult = false;
			capture_screenshot(object);
			

		}
	}


	public static void closeBrowser(String object) throws IOException{
		try{
			Log.info("Closing the browser");
			driver.quit();
		}catch(Exception e){
			Log.error("Not able to Close the Browser --- " + e.getMessage());
			DriverScript.bResult = false;
			
		}
	}
	public static void capture_screenshot(String object) throws IOException
	{
		if(DriverScript.bResult==false)
		{
	 	
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File("C:\\workspace\\PortalApps_KPI\\test-output_Fail\\"+DriverScript.sTestCaseID + "_"+object +".png"));	
	}
		else if (DriverScript.bResult==true)
		{
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File("C:\\workspace\\PortalApps_KPI\\test-output\\"+DriverScript.sTestCaseID + "_"+object +".png"));
		}
		
 }
	
public static void scrollingToElementofAPage(String Object) {
				JavascriptExecutor js=	(JavascriptExecutor) driver;
	           js.executeScript("arguments[0].scrollIntoView();",driver.findElement(By.xpath(Object)));
	}
	
public static void verifyLinks(String object,String data)
{	
try{
	Log.info("Verifying Links");


	WebElement table_Elements = driver.findElement(By.xpath(OR.getProperty(object)));
	List <WebElement> links = table_Elements.findElements(By.tagName("a"));
	String[] link_url = new String[links.size()]; 
	String[] link_name = new String[links.size()];
	String links_NotWorking= "";
	String links_Working="";
	
	for(int i=0; i < links.size() ; i++)
	{
		link_url[i]=	links.get(i).getAttribute("href") ;
		link_name[i]=	links.get(i).getText();
	
	}
	
	
	
	
	for(int i=0;i<links.size();i++)
	{
	if(link_name[i].equals("* NEW * Operating Statements – November 2014 and after (Win7)"))
		{
				i++;
		}
	else{
		driver.navigate().to(link_url[i]);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		
		if (driver.getTitle().equals("404 Not Found")||driver.getTitle().equals("The page can't be displayed")||driver.getTitle().equals("500 Internal Server Error")||driver.getTitle().equals("Web Server Error")||driver.getTitle().equals("Certificate Error: Navigation Blocked")||driver.getTitle().equals("User Not Authorized")||driver.getTitle().equals("This webpage cannot be displayed")||driver.getTitle().equals("Error")||driver.getTitle().equals("WebSphere Error"))
		
		{
			System.out.println("\"" + link_name[i]  +  "\""
					+ driver.getTitle() +  "  IS NOT WORKING. ");
			Log.info("Link "  +   link_name[i]  + "    IS NOT WORKING" );
			if (links_NotWorking.isEmpty())
			{
				links_NotWorking= link_name[i] ;
				}
			
			else
			{
				links_NotWorking=links_NotWorking + System.lineSeparator() +  link_name[i];
			}
			
			DriverScript.bResult = false;
			DriverScript.finalResult= 1;
			capture_screenshot(link_name[i]);
		} 
		if(driver.getPageSource().contains("No Data Found for the requested report.")||driver.getPageSource().contains("Database error for this report"))
			//if(driver.findElement(By.className("ErrorMess")).equals("No Data Found for the requested report.")||driver.findElement(By.xpath(".//html/body/div[2]/table/tbody/tr/td/div[2]/table/tbody/tr/td/div/div[2]/table/tbody/tr[2]/td")).getText().equals("Database error for this report"))
			{
				System.out.println("\"" + link_name[i] + "\"is not working"
						+ "  Data Issues.");
		Log.info("Link"+ link_name[i] +"is not working"+ "  Data Issues.");
		if (links_NotWorking.isEmpty())
		{
			links_NotWorking= link_name[i] ;
			}
		
		else
		{
			links_NotWorking=links_NotWorking + System.lineSeparator() +  link_name[i];
		}
		DriverScript.bResult = false;
		capture_screenshot(link_name[i]);
			}
		else		
			{ 
				System.out.println("\"" + link_name[i] + "\""
						+ driver.getTitle() +" is working.");
					
					Log.info("Link "+ link_name[i]+ "    is working");
				if(links_Working.isEmpty())
				{
					links_Working=link_name[i];
				}
				else
				{
					links_Working=links_Working + System.lineSeparator() + link_name[i];
				}
				
				capture_screenshot(link_name[i]);
				}
	
		}
	ExcelUtils.setCellData(links_NotWorking,DriverScript.iTestStep,Constants.Col_LinksNotWorking,Constants.Sheet_TestSteps);//Working Links
	ExcelUtils.setCellData(links_Working, DriverScript.iTestStep, Constants.Col_LinksWorking,Constants.Sheet_TestSteps);//Not Working Links
}  
}
 		catch(Exception e){
 			Log.error("Not able to verify links --- " + e.getMessage());
 			DriverScript.bResult = false;
 		}

     }
}
	






