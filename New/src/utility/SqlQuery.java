package utility;

import executionEngine.DriverScript;

public class SqlQuery {
	
	public static String sql;
	public static String sql_ly;
	
	public static void getquery(String location,String Metric, String Date, String Location_nbr, String Time_Frame)
	{
		if(location.equalsIgnoreCase("chain"))
		{
		sql= "select sample_value from "+location+"_dtl where stat_id in ('"+Metric+"') and dttm_seq_id in (select dttm_seq_id from time_period where sample_dttm = '"+Date+"' and time_frame='DA')";
		sql_ly= "select prev_sample_value from "+location+"_dtl where stat_id in ('"+Metric+"') and dttm_seq_id in (select dttm_seq_id from time_period where sample_dttm = '"+Date+"' and time_frame='DA')";
		}
		else if(location.equalsIgnoreCase("operation"))
		{
			sql= "select sample_value from division_dtl where "+"division_nbr='"+Location_nbr+"' and stat_id in ('"+Metric+"') and dttm_seq_id in (select dttm_seq_id from time_period where sample_dttm = '"+Date+"' and time_frame='DA')";
			sql_ly= "select prev_sample_value from division_dtl where "+"division_nbr='"+Location_nbr+"' and stat_id in ('"+Metric+"') and dttm_seq_id in (select dttm_seq_id from time_period where sample_dttm = '"+Date+"' and time_frame='DA')";
		}
		else if(location.equalsIgnoreCase("region"))
		{
			sql= "select sample_value from "+location+"_dtl where "+location+"_nbr='"+Location_nbr+"' and stat_id in ('"+Metric+"') and dttm_seq_id in (select dttm_seq_id from time_period where sample_dttm = '"+Date+"' and time_frame='DA')";
			sql_ly= "select prev_sample_value from "+location+"_dtl where "+location+"_nbr='"+Location_nbr+"' and stat_id in ('"+Metric+"') and dttm_seq_id in (select dttm_seq_id from time_period where sample_dttm = '"+Date+"' and time_frame='DA')";
		}
		else if(location.equalsIgnoreCase("area"))
		{
			sql= "select sample_value from "+location+"_dtl where "+location+"_nbr='"+Location_nbr+"' and stat_id in ('"+Metric+"') and dttm_seq_id in (select dttm_seq_id from time_period where sample_dttm = '"+Date+"' and time_frame='DA')";
			sql_ly= "select prev_sample_value from "+location+"_dtl where "+location+"_nbr='"+Location_nbr+"' and stat_id in ('"+Metric+"') and dttm_seq_id in (select dttm_seq_id from time_period where sample_dttm = '"+Date+"' and time_frame='DA')";
		}
		else if(location.equalsIgnoreCase("district"))
		{
			sql= "select sample_value from "+location+"_dtl where "+"dist_nbr='"+Location_nbr+"' and stat_id in ('"+Metric+"') and dttm_seq_id in (select dttm_seq_id from time_period where sample_dttm = '"+Date+"' and time_frame='DA')";
			sql_ly= "select prev_sample_value from "+location+"_dtl where "+"dist_nbr='"+Location_nbr+"' and stat_id in ('"+Metric+"') and dttm_seq_id in (select dttm_seq_id from time_period where sample_dttm = '"+Date+"' and time_frame='DA')";
		}
		else if(location.equalsIgnoreCase("store"))
		{
			if(DriverScript.sTestCaseID.startsWith("Sales"))
			{
				sql= "select sample_value from "+location+"_dtl_sales where "+location+"_nbr='"+Location_nbr+"' and stat_id in ('"+Metric+"') and dttm_seq_id in (select dttm_seq_id from time_period where sample_dttm = '"+Date+"' and time_frame='DA')";
				sql_ly= "select prev_sample_value from "+location+"_dtl_sales where "+location+"_nbr='"+Location_nbr+"' and stat_id in ('"+Metric+"') and dttm_seq_id in (select dttm_seq_id from time_period where sample_dttm = '"+Date+"' and time_frame='DA')";
			}
			else if(DriverScript.sTestCaseID.startsWith("Beauty"))
			{
				sql= "select sample_value from "+location+"_dtl_cos where "+location+"_nbr='"+Location_nbr+"' and stat_id in ('"+Metric+"') and dttm_seq_id in (select dttm_seq_id from time_period where sample_dttm = '"+Date+"' and time_frame='DA')";
				sql_ly= "select prev_sample_value from "+location+"_dtl_cos where "+location+"_nbr='"+Location_nbr+"' and stat_id in ('"+Metric+"') and dttm_seq_id in (select dttm_seq_id from time_period where sample_dttm = '"+Date+"' and time_frame='DA')";
			}
			
		}
	}
}


