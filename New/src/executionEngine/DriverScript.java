package executionEngine;
 
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import config.ActionKeywords;
import config.Constants;
import utility.ExcelUtils;
import utility.Log;
 
public class DriverScript { 
 
	public static Properties OR;
	public static ActionKeywords actionKeywords;
	public static String sActionKeyword;
	public static String sPageObject;
	public static Method method[];
	public static int iTestStep;
	public static int iTestLastStep;
	public static String sTestCaseID;
	public static String sRunMode;
	public static boolean bResult;
	public static String sData;
	public static int finalResult;
 
	public DriverScript() throws NoSuchMethodException, SecurityException{
		actionKeywords = new ActionKeywords();
		method = actionKeywords.getClass().getMethods();
	}
	

    public static void main(String[] args) throws Exception {
    	
    	ExcelUtils.setExcelFile(Constants.Path_TestData);
    	DOMConfigurator.configure("log4j.xml");
    	//String Path_OR = Constants.Path_OR;
    	FileInputStream fs = new FileInputStream(Constants.Path_OR);
		OR= new Properties(System.getProperties());
		OR.load(fs);
		
		DriverScript startEngine = new DriverScript();
		startEngine.execute_TestCase();
		
    }
	
    private void execute_TestCase() throws Exception {
	    	int iTotalTestCases = ExcelUtils.getRowCount(Constants.Sheet_TestCases);
			for(int iTestcase=1;iTestcase<iTotalTestCases;iTestcase++){
				//Setting the value of bResult variable to 'true' before starting every test case
				bResult = true;
				sTestCaseID = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseID, Constants.Sheet_TestCases); 
				sRunMode = ExcelUtils.getCellData(iTestcase, Constants.Col_RunMode,Constants.Sheet_TestCases);
				if (sRunMode.equals("Yes")){
					iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, Constants.Sheet_TestSteps);
					iTestLastStep = ExcelUtils.getTestStepsCount(Constants.Sheet_TestSteps, sTestCaseID, iTestStep);
					Log.startTestCase(sTestCaseID);
					//Setting the value of bResult variable to 'true' before starting every test step
					bResult=true;
					for (;iTestStep<iTestLastStep;iTestStep++){
			    		sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword,Constants.Sheet_TestSteps);
			    		sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObject, Constants.Sheet_TestSteps);
			    		sData= ExcelUtils.getCellData(iTestStep, Constants.Col_DataSet, Constants.Sheet_TestSteps); 
			    		execute_Actions();
			    		bResult=true;
			    	
			    				    		
			    		
						if(bResult==true)
						{ 
						}
						else if(bResult==false)
						{ finalResult= 1;
						
						}
					}
						if(finalResult==1){
							
							ExcelUtils.setCellData(Constants.KEYWORD_FAIL,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
							}
						
						if(finalResult==0)
						{
							ExcelUtils.setCellData(Constants.KEYWORD_PASS,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
							Log.endTestCase(sTestCaseID);	
						}
							
				}
    		}	
    }
     private static void execute_Actions() throws Exception {
 
		for(int i=0;i<method.length;i++){
 
			if(method[i].getName().equals(sActionKeyword)){
				method[i].invoke(actionKeywords,sPageObject, sData);
			
				if(bResult==true){
					
					ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
					break;
				}else{
					 
					
					ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
					
				
				//	ActionKeywords.closeBrowser("");
					break;
					}
				}
			}
     }
 
 }