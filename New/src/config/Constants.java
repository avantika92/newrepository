package config;

import java.sql.Connection;
import java.sql.Statement;

public class Constants {
 
	//List of System Variables
	public static final String URL = "";
	public static final String Path_TestData = "C://Users//akatiyj3//Desktop//Automation//PortalApps KPI//PortalApps KPI//src//dataEngine//DataEngine.xlsx";
	
	
	public static final String Path_OR = "C://Users//akatiyj3//Desktop//Automation//PortalApps KPI//PortalApps KPI//src//config//OR.txt";
	public static final String File_TestData = "DataEngine.xlsx";
 
	//List of Data Sheet Column Numbers
	public static final int Col_TestCaseID = 0;	
	public static final int Col_TestScenarioID =1 ;
	public static final int Col_PageObject =3 ;
	public static final int Col_ActionKeyword =4 ;
	//New entry in Constant variable
	public static final int Col_RunMode =2 ;
	public static final int Col_LinksNotWorking=7;
	public static final int Col_LinksWorking=8;
 
	//List of Data Engine Excel sheets
	public static final String Sheet_TestSteps = "Test Steps";
	//New entry in Constant variable
    public static final String Sheet_TestCases = "Test Cases";
   
 
	//List of Test Data
	public static final String UserName = "";
	public static final String Password = "";
	
	//Constants for results column
	public static final int Col_Result =3 ;
	public static final int Col_DataSet =5 ;
	public static final int Col_TestStepResult =6 ;
	public static final String KEYWORD_FAIL = "FAIL";
	public static final String KEYWORD_PASS = "PASS";
	
	//Constants for DB connectivity
	public static String JDBC_DRIVER = "";
	public static String DB_URL = "";
	public static String USER = "kpi";
	public static String PASS = "prdkpi01";
	public static Connection conn = null;
	public static Statement stmt = null;
	
	//Data Table 
	public static String Table_row_header=null;
	public static String Table_data=null;
	public static String Metric=null;
	public static String Metric1=null;
	public static String Metric2=null;
	
	
 
}