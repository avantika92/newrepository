package utility;

import config.Constants;

public class Stats {
	
	
	//Sales Metrics
	//All Stores
	public static String WBALTFES;
	public static String WBALCRSS;
	public static String WBALCRLI;
	public static String WBALSACI;
	//Established Stores
	public static String WBESCRSS;
	public static String WBESCRCI;
	public static String WBESCRLI;
	public static String WBESTFES;
	//Non Established Stores
	public static String WBNECRCI;
	public static String WBNECRLI;
	public static String WBNECRSS;
	//Comparable Stores
	public static String WBCOCRCI;
	public static String WBCOCRLI; 
	public static String WBCOCRSS;
	//Non Comparable Stores
	public static String WBNCCRCI;
	public static String WBNCCRLI;
	public static String WBNCCRSS;
	//Well Experienced Stores
	public static String WBWECRCI;
	public static String WBWECRLI;
	
	//Beauty Metrics
	
	public static void getMetrics(String Store, String Metrics)
	
	{
		try
		{
			if ((Store.equalsIgnoreCase("All") && Metrics.equalsIgnoreCase("total_fe_sales"))||(Store.equalsIgnoreCase("All") &&Metrics.equalsIgnoreCase("total_fe_sales_ly")))
			{
				Constants.Metric="WBALTFES";
			}
			else if ((Store.equalsIgnoreCase("Established") && Metrics.equalsIgnoreCase("total_fe_sales"))||(Store.equalsIgnoreCase("Established") &&Metrics.equalsIgnoreCase("total_fe_sales_ly")))
			{
				Constants.Metric="WBESTFES";
			}
			
			else if ((Store.equalsIgnoreCase("All") && Metrics.equalsIgnoreCase("ss_sales"))||(Store.equalsIgnoreCase("All") && Metrics.equalsIgnoreCase("ss_sales_ly")))
			{
				Constants.Metric="WBALCRSS";
			}
			else if((Store.equalsIgnoreCase("Established") && Metrics.equalsIgnoreCase("ss_sales"))||(Store.equalsIgnoreCase("Established") && Metrics.equalsIgnoreCase("ss_sales_ly")))
			{
				Constants.Metric="WBESCRSS";
			}

			else if ((Store.equalsIgnoreCase("All") && Metrics.equalsIgnoreCase("cig_sales"))||(Store.equalsIgnoreCase("All") && Metrics.equalsIgnoreCase("cig_sales_ly")))
			{
				Constants.Metric="WBALSACI";
			}
			else if ((Store.equalsIgnoreCase("Established") && Metrics.equalsIgnoreCase("cig_sales"))||(Store.equalsIgnoreCase("Established") && Metrics.equalsIgnoreCase("cig_sales_ly")))
			{
				Constants.Metric="WBESCRCI";
			}


			else if ((Store.equalsIgnoreCase("All") && Metrics.equalsIgnoreCase("liq_sales"))||(Store.equalsIgnoreCase("All") && Metrics.equalsIgnoreCase("liq_sales_ly")))
			{
				Constants.Metric="WBALCRLI";
			}
			else if ((Store.equalsIgnoreCase("Established") && Metrics.equalsIgnoreCase("liq_sales"))||(Store.equalsIgnoreCase("Established") && Metrics.equalsIgnoreCase("liq_sales_ly")))
			{
				Constants.Metric="WBESCRLI";
			}
			else if (Store.equalsIgnoreCase("All") && Metrics.equalsIgnoreCase("beauty_goal"))
			{
				Constants.Metric="COCFSGL";
			}
			else if (Store.equalsIgnoreCase("All") && Metrics.equalsIgnoreCase("percent_goal"))
			{
				Constants.Metric="COPGRP";
			}

		}
		catch (Exception e)
		{
			
		}
		
	}

}
