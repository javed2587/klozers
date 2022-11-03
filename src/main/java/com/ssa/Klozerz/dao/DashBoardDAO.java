package com.ssa.Klozerz.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssa.Klozerz.dto.CalendarAppointmentDTO;
import com.ssa.Klozerz.dto.DashBoardDTO;
import com.ssa.Klozerz.dto.RecurrenceDTO;
import com.ssa.Klozerz.util.AppUtil;
import com.ssa.Klozerz.util.DataBaseConfig;



@Repository
public class DashBoardDAO {
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Autowired
	private DataBaseConfig database;
	
	@Autowired
	private AppUserDAO appUser;
	/**
	 * This method is used to init the JdbcTemplate
	 * 
	 * @return
	 */
	public JdbcTemplate getJdbcTemplate(String userId) {
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		String databaseId=appUser.getDatabaseName(Integer.parseInt(userId));
			
		String url=database.getUrl()+"module_"+databaseId+"?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8";
		dataSource.setUrl(url);
		dataSource.setUsername(database.getUsername());
		dataSource.setPassword(database.getPassword());
		namedJdbcTemplate.getJdbcTemplate().setDataSource(dataSource);
		return namedJdbcTemplate.getJdbcTemplate();
	}
	
	/**
	 * This Query is used to get the all the roles from the users_roles table
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public ArrayList<CalendarAppointmentDTO> getAccountCalenderUpcomingCampaigns(long userId) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("account_calendar.id as id,");
		sql.append("account_calendar.created_by as createdBy,");
		sql.append("account_calendar.account_id as moduleId,");
		sql.append("account_calendar.user_id as userId,");
		sql.append("account_calendar.\"Subject\" as subject,");
		sql.append("TO_CHAR(account_calendar.\"Task Date\"::timestamp,'MM/dd/yyyy hh12:mi AM') as taskDate,");
		sql.append("TO_CHAR(account_calendar.taskend::timestamp,'MM/dd/yyyy hh12:mi AM') as taskEnd,");
		sql.append("account_calendar.\"Task Note\" as taskNote,");
		sql.append("account_calendar.\"Status\" as status,");
		sql.append("account_calendar.\"Priority\" as periority,");
		sql.append("account_calendar.modifiedby as modefiedBy,");
		sql.append("account_calendar.modified_date as modefiedDate,");
		sql.append("account_calendar.archived_by as archivedBy,");
		sql.append("account_calendar.archived_date as archivedDate,");
		sql.append("account_calendar.\"GPS\" as gps,");
		sql.append("account_calendar.created_date as createdDate,");
		sql.append("account_calendar.archive_status as archivedStatus,");
		sql.append("account.\"Facility Name\" as facilityName,");
		sql.append("account.\"Rating\" as rating, ");
		sql.append("account_calendar.\"recurr_json\" as recurrenceString, ");
		sql.append("TO_CHAR(account_calendar.\"org_taskDate\"::timestamp,'MM/dd/yyyy hh12:mi AM')  as orgTaskDate, ");
		sql.append("TO_CHAR(account_calendar.\"org_taskend\"::timestamp,'MM/dd/yyyy hh12:mi AM') as orgTaskEnd, ");
		sql.append("account_calendar.\"recurrence_id\" as recurrenceId, ");
		sql.append("account_calendar.\"parent_id\" as parentId ");
		sql.append("FROM account_calendar , account ");
		sql.append("WHERE account_calendar.account_id = account.id ");
		sql.append("AND ( account_calendar.archive_status='new' or account_calendar.archive_status='archived') ");
		sql.append("AND account_calendar.user_id ="+userId);
		sql.append(" AND account_calendar.\"Task Date\"::date  >= now()::date ");
		sql.append("AND account_calendar.\"Task Date\"::date  <= now()::date + 730");
		

		List<CalendarAppointmentDTO> resultList = this.getJdbcTemplate(String.valueOf(userId)).query(sql.toString(),
//				new Object[] { userId }, new int[] { Types.BIGINT },
				new BeanPropertyRowMapper<CalendarAppointmentDTO>(CalendarAppointmentDTO.class));
          System.out.println(sql.toString());
          resultList.forEach(appointmentObj->{
        	  
//        	  if(AppUtil.isNullOrEmpty(appointmentObj.getRecurrenceId())) {
//        		  appointmentObj.setRecurrenceId(appointmentObj.getTaskDate());
//        	  }
        	  
		    	RecurrenceDTO recurr=new RecurrenceDTO();
//		    	System.out.println(appointmentObj.getRecurrenceString());
		    	if(appointmentObj.getRecurrenceString()!=null) {
		    	recurr.setRecurrenceFlag(appointmentObj.getRecurrenceString().get("recurrence_flag")+""); 
		    	
		    	if( recurr.getRecurrenceFlag().equals("true")) {
		    		recurr.setRecurrencePattern(appointmentObj.getRecurrenceString().get("recurrence_pattern")+"");
		    		recurr.setRecurrenceStartDate(appointmentObj.getRecurrenceString().get("recurrence_startdate")+"");
		    		recurr.setRecurrenceStartTime(appointmentObj.getRecurrenceString().get("recurrence_start_time")+"");
		    		recurr.setRecurrenceEndTime(appointmentObj.getRecurrenceString().get("recurrence_end_time")+"");
		    		recurr.setRecurrenceDuration(appointmentObj.getRecurrenceString().get("recurrence_duration")+"");
		    		recurr.setRecurrenceEndDateOption(appointmentObj.getRecurrenceString().get("recurrence_end_date_option")+"");
		    		recurr.setRecurrenceEndDate(appointmentObj.getRecurrenceString().get("recurrence_enddate")+"");
		    		switch(recurr.getRecurrencePattern())
		    		{
		    			case "daily":
		    			{
		    				recurr.setRecurrencePatternSub(appointmentObj.getRecurrenceString().get("recurrence_pattern_sub")+"");
		    				if(recurr.getRecurrencePatternSub().equalsIgnoreCase("every"))
		    				recurr.setRecurrenceDaysCount(appointmentObj.getRecurrenceString().get("recurrence_days_count")+"");
		    				break;
		    			}
		    			case "weekly":{
		    				recurr.setRecurrenceWeekDays(appointmentObj.getRecurrenceString().get("recurrence_week_days")+"");
		    				recurr.setRecurrenceWeeksCount(appointmentObj.getRecurrenceString().get("recurrence_weeks_count")+"");
		    				break;
		    			}
		    			case "monthly":{
		    				recurr.setRecurrenceMonthType(appointmentObj.getRecurrenceString().get("recurrence_month_type")+"");
		    				recurr.setRecurrenceMonthCount(appointmentObj.getRecurrenceString().get("recurrence_month_count")+"");
		    				recurr.setRecurrenceMonthDate(appointmentObj.getRecurrenceString().get("recurrence_month_date")+"");		
		    				break;
		    			}
		    			default:
		    			{
		    				break;
		    			}
		    		}
		    	}}
		    	appointmentObj.setRecurrenceString(null);
		    	appointmentObj.setRecurr(recurr);
		    });
          //System.out.println(resultList);
		return (ArrayList<CalendarAppointmentDTO>) resultList;
	}
	
	public ArrayList<CalendarAppointmentDTO> getLeadCalenderUpcomingCampaigns(long userId) throws Exception {
		String sql = "SELECT lead_calendar.id as id,lead_calendar.created_by as createdBy, lead_calendar.lead_id as moduleId, lead_calendar.user_id as userId,"
					+ " lead_calendar.\"Subject\" as subject, TO_CHAR(lead_calendar.\"Task Date\"::timestamp,'MM/dd/yyyy hh12:mi AM') as taskDate, lead_calendar.\"Task Note\" as taskNote,"
					+ " TO_CHAR(lead_calendar.taskend::timestamp,'MM/dd/yyyy hh12:mi AM') as taskEnd,lead_calendar.\"Status\" as status, lead_calendar.\"Priority\" as periority, lead_calendar.modifiedby as modefiedBy,"
					+ " lead_calendar.modified_date as modefiedDate, lead_calendar.archived_by as archivedBy, lead_calendar.archived_date as archivedDate,"
					+ " lead_calendar.\"GPS\"  as gps, lead_calendar.created_date as createdDate, lead_calendar.archive_status as archivedStatus,"
					+ " lead.\"Facility Name\" as facilityName,lead.\"Rating\" as rating,lead_calendar.\"recurr_json\" as recurrenceString,"
					+ "TO_CHAR(lead_calendar.\"org_taskDate\"::timestamp,'MM/dd/yyyy hh12:mi AM')  as orgTaskDate,TO_CHAR(lead_calendar.\"org_taskend\"::timestamp,'MM/dd/yyyy hh12:mi AM') as orgTaskEnd,"
					+ "lead_calendar.\"recurrence_id\" as recurrenceId, lead_calendar.\"parent_id\" as parentId FROM lead_calendar, lead"
					+ " WHERE lead_calendar.lead_id = lead.id AND lead_calendar.\"Status\"='Open' "
					+ " AND lead.\"Status\"<>'Completed'"
					+ " AND ( lead_calendar.archive_status='new' or lead_calendar.archive_status='archived') "
					+ " AND lead_calendar.\"Task Date\"::date  >= now()::date "
					+ " AND lead_calendar.\"Task Date\"::date  <= now()::date + 730 "
					+ " AND lead_calendar.user_id =" + userId;
					
		List<CalendarAppointmentDTO> resultList = this.getJdbcTemplate(String.valueOf(userId)).query(sql,new BeanPropertyRowMapper<CalendarAppointmentDTO>(CalendarAppointmentDTO.class));
		 System.out.println(sql.toString());
		 resultList.forEach(appointmentObj->{
			
//			 if(AppUtil.isNullOrEmpty(appointmentObj.getRecurrenceId())) {
//       		  appointmentObj.setRecurrenceId(appointmentObj.getTaskDate());
//       	  }
			 
		    	RecurrenceDTO recurr=new RecurrenceDTO();
//		    	System.out.println(appointmentObj.getRecurrenceString());
		    	if(appointmentObj.getRecurrenceString()!=null) {
		    	recurr.setRecurrenceFlag(appointmentObj.getRecurrenceString().get("recurrence_flag")+""); 
		    	
		    	if( recurr.getRecurrenceFlag().equals("true")) {
		    		recurr.setRecurrencePattern(appointmentObj.getRecurrenceString().get("recurrence_pattern")+"");
		    		recurr.setRecurrenceStartDate(appointmentObj.getRecurrenceString().get("recurrence_startdate")+"");
		    		recurr.setRecurrenceStartTime(appointmentObj.getRecurrenceString().get("recurrence_start_time")+"");
		    		recurr.setRecurrenceEndTime(appointmentObj.getRecurrenceString().get("recurrence_end_time")+"");
		    		recurr.setRecurrenceDuration(appointmentObj.getRecurrenceString().get("recurrence_duration")+"");
		    		recurr.setRecurrenceEndDateOption(appointmentObj.getRecurrenceString().get("recurrence_end_date_option")+"");
		    		recurr.setRecurrenceEndDate(appointmentObj.getRecurrenceString().get("recurrence_enddate")+"");
		    		switch(recurr.getRecurrencePattern())
		    		{
		    			case "daily":
		    			{
		    				recurr.setRecurrencePatternSub(appointmentObj.getRecurrenceString().get("recurrence_pattern_sub")+"");
		    				if(recurr.getRecurrencePatternSub().equalsIgnoreCase("every"))
		    				recurr.setRecurrenceDaysCount(appointmentObj.getRecurrenceString().get("recurrence_days_count")+"");
		    				break;
		    			}
		    			case "weekly":{
		    				recurr.setRecurrenceWeekDays(appointmentObj.getRecurrenceString().get("recurrence_week_days")+"");
		    				recurr.setRecurrenceWeeksCount(appointmentObj.getRecurrenceString().get("recurrence_weeks_count")+"");
		    				break;
		    			}
		    			case "monthly":{
		    				recurr.setRecurrenceMonthType(appointmentObj.getRecurrenceString().get("recurrence_month_type")+"");
		    				recurr.setRecurrenceMonthCount(appointmentObj.getRecurrenceString().get("recurrence_month_count")+"");
		    				recurr.setRecurrenceMonthDate(appointmentObj.getRecurrenceString().get("recurrence_month_date")+"");		
		    				break;
		    			}
		    			default:
		    			{
		    				break;
		    			}
		    		}
		    	}
		    	}
		    	appointmentObj.setRecurrenceString(null);
		    	appointmentObj.setRecurr(recurr);
		    });
		return (ArrayList<CalendarAppointmentDTO>) resultList;
	}
	public ArrayList<CalendarAppointmentDTO> getPersonalCalenderUpcomingCampaigns(long userId) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("personal_calendar.id as id,");
		sql.append("personal_calendar.created_by as createdBy,");
		sql.append("personal_calendar.personal_id as moduleId,");
		sql.append("personal_calendar.user_id as userId,");
		sql.append("personal_calendar.\"Subject\" as subject,");
		sql.append("TO_CHAR(personal_calendar.\"Task Date\"::timestamp,'MM/dd/yyyy hh12:mi AM') as taskDate,");
		sql.append("TO_CHAR(personal_calendar.taskend::timestamp,'MM/dd/yyyy hh12:mi AM') as taskEnd,");
		sql.append("personal_calendar.\"Task Note\" as taskNote,");
		sql.append("personal_calendar.\"Status\" as status,");
		sql.append("personal_calendar.\"Priority\" as periority,");
		sql.append("personal_calendar.modifiedby as modefiedBy,");
		sql.append("personal_calendar.modified_date as modefiedDate,");
		sql.append("personal_calendar.archived_by as archivedBy,");
		sql.append("personal_calendar.archived_date as archivedDate,");
		sql.append("personal_calendar.\"GPS\" as gps,");
		sql.append("personal_calendar.created_date as createdDate,");
		sql.append("personal_calendar.archive_status as archivedStatus, ");
		sql.append("personal_calendar.\"recurr_json\" as recurrenceString, ");
		sql.append("TO_CHAR(personal_calendar.\"org_taskDate\"::timestamp,'MM/dd/yyyy hh12:mi AM')  as orgTaskDate, ");
		sql.append("TO_CHAR(personal_calendar.\"org_taskend\"::timestamp,'MM/dd/yyyy hh12:mi AM') as orgTaskEnd, ");
		sql.append("personal_calendar.\"recurrence_id\" as recurrenceId, ");
		sql.append("personal_calendar.\"parent_id\" as parentId ");
		sql.append("FROM personal_calendar ");
		sql.append("WHERE personal_calendar.personal_id = "+ userId);
		sql.append(" AND ( personal_calendar.archive_status='new' or personal_calendar.archive_status='archived')");
		sql.append(" AND personal_calendar.\"Task Date\"::date  >= now()::date");
		sql.append(" AND personal_calendar.\"Task Date\"::date  <= now()::date + 730");
		sql.append(" AND personal_calendar.user_id ="+userId);

		List<CalendarAppointmentDTO> resultList = this.getJdbcTemplate(String.valueOf(userId)).query(sql.toString(), new BeanPropertyRowMapper<CalendarAppointmentDTO>(CalendarAppointmentDTO.class));
		 System.out.println(sql.toString());
		 
		 resultList.forEach(appointmentObj->{
		    
//			 if(AppUtil.isNullOrEmpty(appointmentObj.getRecurrenceId())) {
//       		  appointmentObj.setRecurrenceId(appointmentObj.getTaskDate());
//       	  }
			 
			 RecurrenceDTO recurr=new RecurrenceDTO();
//		    	System.out.println(appointmentObj.getRecurrenceString());
			 if(appointmentObj.getRecurrenceString()!=null) {
		    	recurr.setRecurrenceFlag(appointmentObj.getRecurrenceString().get("recurrence_flag")+""); 
		    	
		    	if( recurr.getRecurrenceFlag().equals("true")) {
		    		recurr.setRecurrencePattern(appointmentObj.getRecurrenceString().get("recurrence_pattern")+"");
		    		recurr.setRecurrenceStartDate(appointmentObj.getRecurrenceString().get("recurrence_startdate")+"");
		    		recurr.setRecurrenceStartTime(appointmentObj.getRecurrenceString().get("recurrence_start_time")+"");
		    		recurr.setRecurrenceEndTime(appointmentObj.getRecurrenceString().get("recurrence_end_time")+"");
		    		recurr.setRecurrenceDuration(appointmentObj.getRecurrenceString().get("recurrence_duration")+"");
		    		recurr.setRecurrenceEndDateOption(appointmentObj.getRecurrenceString().get("recurrence_end_date_option")+"");
		    		recurr.setRecurrenceEndDate(appointmentObj.getRecurrenceString().get("recurrence_enddate")+"");
		    		switch(recurr.getRecurrencePattern())
		    		{
		    			case "daily":
		    			{
		    				recurr.setRecurrencePatternSub(appointmentObj.getRecurrenceString().get("recurrence_pattern_sub")+"");
		    				if(recurr.getRecurrencePatternSub().equalsIgnoreCase("every"))
		    				recurr.setRecurrenceDaysCount(appointmentObj.getRecurrenceString().get("recurrence_days_count")+"");
		    				break;
		    			}
		    			case "weekly":{
		    				recurr.setRecurrenceWeekDays(appointmentObj.getRecurrenceString().get("recurrence_week_days")+"");
		    				recurr.setRecurrenceWeeksCount(appointmentObj.getRecurrenceString().get("recurrence_weeks_count")+"");
		    				break;
		    			}
		    			case "monthly":{
		    				recurr.setRecurrenceMonthType(appointmentObj.getRecurrenceString().get("recurrence_month_type")+"");
		    				recurr.setRecurrenceMonthCount(appointmentObj.getRecurrenceString().get("recurrence_month_count")+"");
		    				recurr.setRecurrenceMonthDate(appointmentObj.getRecurrenceString().get("recurrence_month_date")+"");		
		    				break;
		    			}
		    			default:
		    			{
		    				break;
		    			}
		    		}
		    	}
			 }
		    	appointmentObj.setRecurrenceString(null);
		    	appointmentObj.setRecurr(recurr);
		    });
		 
		return (ArrayList<CalendarAppointmentDTO>) resultList;
	}
	
	
	
}
