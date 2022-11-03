package com.ssa.Klozerz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.ssa.Klozerz.dto.AppointmentMailDTO;
import com.ssa.Klozerz.dto.CalendarAppointmentDTO;
import com.ssa.Klozerz.dto.ContactDTO;
import com.ssa.Klozerz.dto.ProgressNoteDTO;
import com.ssa.Klozerz.util.AppUtil;
import com.ssa.Klozerz.util.DataBaseConfig;
import com.ssa.Klozerz.util.DateUtil;

@Repository
public class GeneralDAO {

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
	
	public List<ProgressNoteDTO> getNotesByModuleNameAndId(String userId,String moduleName,String moduleId) throws Exception
	{
		
//		String sql="SELECT DISTINCT ON (a.\"Subject\",a.\"Visit Note\",a.\"Date\",a.date_time)a.\"id\", \"lead_id\", a.\"Subject\", "
//				+ "a.\"Visit Note\",a.\"Date\", a.\"created_by\", a.\"created_date\", a.\"modifiedby\",a.\"modified_date\", a.\"archived_by\","
//				+ " a.\"archive_status\", a.\"user_id\",l.\"id\",a.\"GPS\",a.date_time,l.\"Facility Name\" FROM lead_note a INNER JOIN lead l "
//				+ "ON a.lead_id = l.id  AND a.archive_status = 'new' AND a.lead_id =" + moduleId ;
		String sql="SELECT DISTINCT ON (a.\"Subject\",a.\"Visit Note\",a.\"Date\",a.date_time)a.\"id\", \""+moduleName+"_id\" as moduleId, a.\"Subject\" as subject, "
				+ "a.\"Visit Note\" as visitNote,a.\"Date\" as dateTimep, a.\"created_by\", CAST(a.\"created_date\" as timestamp without time zone) , a.\"modifiedby\",CAST(a.\"modified_date\" as timestamp without time zone), a.\"archived_by\","
				+ " a.\"archive_status\", a.\"user_id\",a.\"GPS\" as gps,a.date_time as noteTime,l.\"Facility Name\" as faciltyName FROM "+moduleName+"_note a INNER JOIN "+moduleName+" l "
				+ "ON a."+moduleName+"_id = l.id  AND a.archive_status = 'new' AND a."+moduleName+"_id =" + moduleId ;
		List<ProgressNoteDTO> notesList = getJdbcTemplate(userId).query(sql.toString(),new BeanPropertyRowMapper<ProgressNoteDTO>(ProgressNoteDTO.class));
		System.out.println(notesList.size());
		return notesList;
	}
	
	
    /**/
    public String createCalendarTask(String userId,CalendarAppointmentDTO appointment) {
        try {
        	  KeyHolder keyHolder = new GeneratedKeyHolder();
        	String parent_id="0";
             
            String addrec=appointment.getRecurr().getRecurrenceFlag();//jsonData.get("recurrence_flag").toString();
            JSONObject recurrenceJson =new JSONObject();
            recurrenceJson.put("recurrence_flag", appointment.getRecurr().getRecurrenceFlag());
            
            if(!addrec.equalsIgnoreCase("true"))
            {
                /*code for time zone*/
                
             	String [] dbDate=this.meetingDateTimeConvertion(appointment,appointment.getTaskDate(),appointment.getTaskEnd());
               
                
                 	  StringBuilder sql = new StringBuilder();
        	  sql.append( "INSERT INTO " + appointment.getModuleName() + "_calendar(created_by," + appointment.getModuleName() + "_id,"
        	  		+ "user_id,\"Subject\", \"Task Date\", \"Task Note\", \"Status\",\"GPS\",\"Priority\",\"appointment_title\","
        	  		+ "\"taskend\",\"attendees_email\",\"appointment_location\",\"time_zone_type\",\"recurr_json\",\"org_taskDate\","
        	  		+ "\"org_taskend\",\"profile_zone\",\"parent_id\")");
        	  sql.append(" VALUES('"+appointment.getUserName()+"',"+ appointment.getModuleId() + "," + appointment.getUserId() + ",'"+appointment.getSubject()+"','"+
              dbDate[0]+"',"+(!AppUtil.isNullOrEmpty(appointment.getTaskNote())?"'"+appointment.getTaskNote()+"'":null)+","+(!AppUtil.isNullOrEmpty(appointment.getStatus())?"'"+appointment.getStatus()+"'":"'New'")+","+
              (!AppUtil.isNullOrEmpty(appointment.getGps())?"'"+appointment.getGps()+"'":null)+","+(!AppUtil.isNullOrEmpty(appointment.getPeriority())?"'"+appointment.getPeriority()+"'":"'Low'")+","+
              (!AppUtil.isNullOrEmpty(appointment.getTitle())?"'"+appointment.getTitle()+"'":"'"+appointment.getSubject()+"'")+",'"+dbDate[1]+"',"+
              (!AppUtil.isNullOrEmpty(appointment.getAttendeesEmail())?"'"+appointment.getAttendeesEmail()+"'":null)+","+(!AppUtil.isNullOrEmpty(appointment.getAddress())? "'"+appointment.getAddress()+"'":null)+
              ",'"+appointment.getTimeZone()+"','"+recurrenceJson.toString()+"','"+dbDate[2]+"','"+dbDate[3]+"','"+appointment.getProfileZone()+"',"+parent_id+")");		
        	 System.out.println(sql);
        	  getJdbcTemplate(String.valueOf(appointment.getUserId())).update(
        				new PreparedStatementCreator() {
        				    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        				      return connection.prepareStatement(sql.toString(), new String[] {"id"});
        				    }
        				  }, keyHolder
        				);
        	  ////////////////////////////
                    if(keyHolder.getKey()!=null && parent_id=="0")
                    {
                    parent_id=keyHolder.getKey().intValue()+"";
                    }  
                    return parent_id;
                }
            else if(addrec.equalsIgnoreCase("true"))
            {
            	return insertReccurenceRecord(appointment,keyHolder,parent_id);
            } 
        }
        catch (Exception e4) {
            e4.printStackTrace();
        }
        return "0";
    }
	
    String insertReccurenceRecord(CalendarAppointmentDTO info,KeyHolder keyHolder,String parent_id)
    {
        SimpleDateFormat form=new SimpleDateFormat("MM/dd/yyyy hh:mm a");

        
        
    	try {
            String mZone=info.getTimeZone();
//            
    		String meetingDates=info.getRecurr().getRecurrenceMeetingDates()+"";
			String dateArray[]=meetingDates.split(",");
			
            
            //recurrence json
            String addrec=info.getRecurr().getRecurrenceFlag();
            JSONObject recurrenceJson=this.generateRecurrenceJSON(info,addrec);
        
            
	     	 for(int list=0;list<dateArray.length;list++)
        	 {
          
			 /*code for time zone*/
            System.out.println("meeting zone:"+mZone);
            
            System.out.println("Original time:"+dateArray[list]+" "+info.getRecurr().getRecurrenceStartTime());
            
            form.setTimeZone(TimeZone.getTimeZone(mZone));
            
            String [] dbDate=this.meetingDateTimeConvertion(info,dateArray[list]+" "+info.getRecurr().getRecurrenceStartTime(),dateArray[list]+" "+info.getRecurr().getRecurrenceEndTime());
            
            
            StringBuilder sql = new StringBuilder();
    	   sql.append( "INSERT INTO " + info.getModuleName() + "_calendar(created_by," + info.getModuleName() + "_id,"
    	  		+ "user_id,\"Subject\", \"Task Date\", \"Task Note\", \"Status\",\"GPS\",\"Priority\",\"appointment_title\","
    	  		+ "\"taskend\",\"attendees_email\",\"appointment_location\",\"time_zone_type\",\"recurr_json\",\"org_taskDate\","
    	  		+ "\"org_taskend\",\"profile_zone\",\"parent_id\",\"recurrence_id\")");
    	  sql.append(" VALUES('"+info.getUserName()+"',"+ info.getModuleId() + "," + info.getUserId() + ",'"+info.getSubject()+"','"+
          dbDate[0]+"',"+(!AppUtil.isNullOrEmpty(info.getTaskNote())?"'"+info.getTaskNote()+"'":null)+","+(!AppUtil.isNullOrEmpty(info.getStatus())?"'"+info.getStatus()+"'":"'New'")+","+
          (!AppUtil.isNullOrEmpty(info.getGps())?"'"+info.getGps()+"'":null)+","+(!AppUtil.isNullOrEmpty(info.getPeriority())?"'"+info.getPeriority()+"'":"'Low'")+","+
          (!AppUtil.isNullOrEmpty(info.getTitle())?"'"+info.getTitle()+"'":"'"+info.getSubject()+"'")+",'"+dbDate[1]+"',"+
          (!AppUtil.isNullOrEmpty(info.getAttendeesEmail())?"'"+info.getAttendeesEmail()+"'":null)+","+(!AppUtil.isNullOrEmpty(info.getAddress())? "'"+info.getAddress()+"'":null)+
          ",'"+info.getTimeZone()+"','"+recurrenceJson.toString()+"','"+dbDate[2]+"','"+dbDate[3]+"','"+info.getProfileZone()+"',"+parent_id+",'"+dbDate[0]+"')");		
//    	  System.out.println(sql);          ",'"+info.getTimeZone()+"','"+recurrenceJson.toString()+"','"+dbDate[2]+"','"+dbDate[3]+"','"+info.getProfileZone()+"',"+parent_id+")");		
//    	  System.out.println(sql);
    	  getJdbcTemplate(String.valueOf(info.getUserId())).update(
    				new PreparedStatementCreator() {
    				    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
    				      return connection.prepareStatement(sql.toString(), new String[] {"id"});
    				    }
    				  }, keyHolder
    				);
    	  ////////////////////////////
                if(keyHolder.getKey()!=null)
                {
                	if(list==0)
           		parent_id=keyHolder.getKey().intValue()+"";
                }  
                
        	 }
	     	 }  catch (Exception e4) {
                 e4.printStackTrace();
             }
	     	 
                return parent_id;
    }
	     	 
	private JSONObject generateRecurrenceJSON(CalendarAppointmentDTO info, String addrec) {
	    JSONObject Json =new JSONObject();
        
        Json.put("recurrence_flag", addrec);
        if(addrec.toLowerCase().equalsIgnoreCase("true"))
        {Json.put("recurrence_start_time", info.getRecurr().getRecurrenceStartTime());
        Json.put("recurrence_end_time", info.getRecurr().getRecurrenceEndTime());
        Json.put("recurrence_duration", info.getRecurr().getRecurrenceDuration());
        String pattern=info.getRecurr().getRecurrencePattern();
        Json.put("recurrence_pattern",pattern);
        
        if(pattern!=null && pattern!="")
		{
			
			if(pattern.equalsIgnoreCase("Daily"))
			{
				String pattern_sub= info.getRecurr().getRecurrencePatternSub();
			Json.put("recurrence_pattern_sub", pattern_sub);
				if(pattern_sub.toLowerCase().equalsIgnoreCase("every"))
				{
					//recurrence on every number of day
					Json.put("recurrence_days_count", info.getRecurr().getRecurrenceDaysCount());
				}
				else{
					//code for weekday
					Json.put("recurrence_days_count", info.getRecurr().getRecurrenceDaysCount());
				}
			}
			else if(pattern.toLowerCase().equalsIgnoreCase("weekly"))
			{
				// on count/number e.g 3rd or 4th
				Json.put("recurrence_weeks_count", info.getRecurr().getRecurrenceWeeksCount());
				// week days array like monday,tuesday
				String weekdays=info.getRecurr().getRecurrenceWeekDays();
				
				Json.put("recurrence_week_days",weekdays);
				
			}
			else if(pattern.toLowerCase().equalsIgnoreCase("monthly"))
			{
				String recurrence_month_type=info.getRecurr().getRecurrenceMonthType();
				if(recurrence_month_type.toLowerCase().equalsIgnoreCase("day"))
				{
					// month_date:2nd of every month_count:4th month
					Json.put("recurrence_month_type", recurrence_month_type);
					Json.put("recurrence_month_date", info.getRecurr().getRecurrenceMonthDate());
					Json.put("recurrence_month_count", info.getRecurr().getRecurrenceMonthCount());
				}
				else if(recurrence_month_type.toLowerCase().equalsIgnoreCase("the"))
				{
					//monthly_day:second ,monthly_name:Monday, on every monthly_the_count:4th
					Json.put("recurrence_monthly_day", info.getRecurr().getRecurrenceMonthlyDay());
					Json.put("recurrence_monthly_name", info.getRecurr().getRecurrenceMonthlyName());
					Json.put("recurrence_monthly_the_count", info.getRecurr().getRecurrenceMonthlyTheCount());
					
				}
				else
				{
					//log.log("empty value of monthly pattern");
				}
			}
		}
		//range
        Json.put("recurrence_startdate", info.getRecurr().getRecurrenceStartDate());
		String recurrence_end_date_option=info.getRecurr().getRecurrenceEndDateOption();
		Json.put("recurrence_end_date_option", recurrence_end_date_option);
		
		if(recurrence_end_date_option.toLowerCase().equalsIgnoreCase("no_end_date"))
		{
			// nothing to code
		}
		else if(recurrence_end_date_option.toLowerCase().equalsIgnoreCase("end_after"))
		{
			Json.put("recurrence_end_count", info.getRecurr().getRecurrenceEndCount());
		}
		else if(recurrence_end_date_option.toLowerCase().equalsIgnoreCase("end_by"))
		{
			Json.put("recurrence_enddate", info.getRecurr().getRecurrenceEndDate());
		}
		else{
			//log.log("Issue in the recurrence range");
		}
	}	
	return Json;
		}
	
	     	 
	     	 
    
   private String[] meetingDateTimeConvertion(CalendarAppointmentDTO appointment,String meeting_start,String meeting_end) {
	   
  	 String meeting_time_start="";
     String meeting_time_end="";

       Date mDate;
       Date eDate;

	   String profileZone=appointment.getProfileZone();//jsonData.getString("profile_zone");
       String meetingZone=appointment.getTimeZone();//jsonData.getString("timezone");
    
	   //input zone
       String[] DBdates= new String[4];
	   SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy hh:mm a");
       format.setTimeZone(TimeZone.getTimeZone(meetingZone));
       
       //profile zone
       SimpleDateFormat meetingformat=new SimpleDateFormat("MM/dd/yyyy hh:mm a");  
       meetingformat.setTimeZone(TimeZone.getTimeZone(profileZone));
       
       //db format
       SimpleDateFormat saveformat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
       saveformat.setTimeZone(TimeZone.getTimeZone(profileZone));
       String dbst="";
       String dbe="";
       
       System.out.println("meeting zone:"+meetingZone);
       
       System.out.println("Original time:"+appointment.getTaskDate());

       try {
			// parse from original format(input zone)
    	   
//			mDate = format.parse(appointment.getTaskDate());
//			eDate = format.parse(appointment.getEndTime());

    	   mDate = format.parse(meeting_start);
			eDate = format.parse(meeting_end);
    	   
			//time conversion from input zone to profile zone
			meeting_time_start=meetingformat.format(mDate);
			meeting_time_end=meetingformat.format(eDate);
			
			Date convertedDate=format.parse(meeting_time_start);
			
			meeting_time_start=format.format(convertedDate);
			meeting_time_start=saveformat.format(convertedDate);
			
			convertedDate=format.parse(meeting_time_end);
			meeting_time_end=format.format(convertedDate);
			meeting_time_end=saveformat.format(convertedDate);

			
			dbst=saveformat.format(mDate);
			dbe=saveformat.format(eDate);

			DBdates[0]=meeting_time_start;
			DBdates[1]=meeting_time_end;
			DBdates[2]=dbst;
			DBdates[3]=dbe;
			
           System.out.println("profile zone:"+profileZone);
			System.out.println("after zone convert:"+meeting_time_start);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DBdates[0]="";
			DBdates[1]="";
			DBdates[2]="";
			DBdates[3]="";
		}
		
       return DBdates;
   }
   
   
   public String saveMailStatus(String userId,CalendarAppointmentDTO appointment,String Appointment_id,String uuid,String status,String rrule,String mailTo)
   {
   		
     	  StringBuilder sql = new StringBuilder();	  
      	sql.append( "INSERT INTO " + appointment.getModuleName() + "_appointment_mail(\"calendar_id\",\"uuid\",\"status\",\"rrule\",\"mail_to\")");
      	sql.append(" VALUES("+Appointment_id+",'"+uuid+"','"+status+"','"+rrule+"','"+mailTo+"')");
      	System.out.println(sql);
      	return String.valueOf(this.getJdbcTemplate(userId).update(sql.toString()));
	
	
   }
	public List<CalendarAppointmentDTO> getModuleViseCampaigns(long userId, long moduleId, String moduleName) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("mod.id as id,");
		sql.append("mod.created_by as createdBy,");
		sql.append("mod."+moduleName+"_id as moduleId,");
		sql.append("mod.user_id as userId,");
		sql.append("mod.\"Subject\" as subject,");
		sql.append("TO_CHAR(mod.\"Task Date\"::timestamp,'MM/dd/yyyy hh12:mi AM') as taskDate,");
		sql.append("TO_CHAR(mod.taskend::timestamp,'MM/dd/yyyy hh12:mi AM') as taskEnd,");
//		sql.append("TO_CHAR(mod.taskend::timestamp,'MM/dd/yyyy hh12:mi AM') as endTime,");
		sql.append("mod.\"Task Note\" as taskNote,");
		sql.append("mod.\"Status\" as status,");
		sql.append("mod.\"Priority\" as periority,");
		sql.append("mod.modifiedby as modefiedBy,");
		sql.append("mod.modified_date as modefiedDate,");
		sql.append("mod.archived_by as archivedBy,");
		sql.append("mod.archived_date as archivedDate,");
		sql.append("mod.\"GPS\" as gps,");
		sql.append("mod.created_date as createdDate,");
		sql.append("mod.archive_status as archivedStatus,");
		sql.append("m.\"Facility Name\" as facilityName,");
		sql.append("m.\"Rating\" as rating,");
		sql.append("m.\"City\" as city, ");
		sql.append("m.\"Zip Code\" as zipCode,");
		sql.append("m.\"State\" as state,");
		sql.append("m.\"Main Fax\" as mainFax,");
		sql.append("m.\"Street\" as street, ");
		sql.append("mod.\"recurr_json\" as recurrenceString, ");
		sql.append("TO_CHAR(mod.\"org_taskDate\"::timestamp,'MM/dd/yyyy hh12:mi AM')  as orgTaskDate, ");
//		sql.append("TO_CHAR(mod.\"org_taskend\"::timestamp,'MM/dd/yyyy hh12:mi AM') as orgTaskEnd, ");
		sql.append("mod.\"recurrence_id\" as recurrenceId, ");
		sql.append("mod.\"parent_id\" as parentId ");
		sql.append("FROM "+moduleName+"_calendar mod,"+moduleName+" m");
		sql.append(" WHERE mod."+moduleName+"_id = m.id ");
		sql.append("AND mod.archive_status='new' ");
		sql.append("AND mod."+moduleName+"_id ="+moduleId);
		sql.append(" ORDER By mod.\"Task Date\"::date ASC");

		
		List<CalendarAppointmentDTO> resultList = this.getJdbcTemplate(String.valueOf(userId)).query(sql.toString(),
//				new Object[] { userId }, new int[] { Types.BIGINT },
				new BeanPropertyRowMapper<CalendarAppointmentDTO>(CalendarAppointmentDTO.class));
          System.out.println(sql.toString());
          System.out.println(resultList);
		return  resultList;
	}
	
	public String updateCalendarTask(String userId,CalendarAppointmentDTO appointment) {

		SimpleDateFormat sdfDB=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat sdfComplete=new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		String recurrenceId="";
		// storing recurrenceId with DB format, as most of existing record do not have recurrence id, this is safest way to populate it  
		try {
			recurrenceId=(
					AppUtil.isNullOrEmpty(appointment.getRecurrenceId())?
					sdfDB.format(sdfComplete.parse(appointment.getTaskDate())):
						sdfDB.format(sdfComplete.parse(appointment.getRecurrenceId())));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			recurrenceId=appointment.getRecurrenceId();

		}
		
    	String sql = "";
        final String l_task_duedate = appointment.getTaskDate();
        /*new fields*/
        final String endTime=appointment.getTaskEnd();
//        final String attendees_email=appointment.getAttendeesEmail();
        
        /*code for time zone*/
        String dates[]=this.meetingDateTimeConvertion(appointment, l_task_duedate, endTime);

		/**/

        sql = "UPDATE " + appointment.getModuleName()+ "_calendar SET \"Subject\" = '"+appointment.getSubject() + "',"+
        	"\"Task Date\" = '"+dates[0]+"',\"Task Note\" = "+(!AppUtil.isNullOrEmpty(appointment.getTaskNote())?"'"+appointment.getTaskNote()+"'":null)+",\"Status\" = "+
        	(!AppUtil.isNullOrEmpty(appointment.getStatus())?"'"+appointment.getStatus()+"'":"'New'")+",\"GPS\" = "+
        	(!AppUtil.isNullOrEmpty(appointment.getGps())?"'"+appointment.getGps()+"'":null)+",\"Priority\" = " +(!AppUtil.isNullOrEmpty(appointment.getPeriority())?"'"+appointment.getPeriority()+"'":"'Low'")+
        	",\"appointment_title\" = " +(!AppUtil.isNullOrEmpty(appointment.getTitle())?"'"+appointment.getTitle()+"'":"'"+appointment.getSubject()+"'")+
        	",\"taskend\" = '" +dates[1]+"',\"attendees_email\" = "+(!AppUtil.isNullOrEmpty(appointment.getAttendeesEmail())?"'"+appointment.getAttendeesEmail()+"'":null)+
        	",\"appointment_location\" = " +(!AppUtil.isNullOrEmpty(appointment.getAddress())? "'"+appointment.getAddress()+"'":null)+
        	",\"time_zone_type\" = '"+appointment.getTimeZone()+"',\"org_taskDate\" = '"+dates[2]+"',\"org_taskend\" = '" + dates[3]+
        	"', modified_date = NOW(),modifiedby= '" + appointment.getUserName() + "',\"recurrence_id\"='"
        	+recurrenceId
        	+"' WHERE id=" + appointment.getId();
        
        return getJdbcTemplate(String.valueOf(appointment.getUserId())).update(sql.toString())+"";
   
	}
	
	
	/**
	 * This method is used to fetch sibling of appointments
	 * 
	 * @return appointment list with same parent id
	 */
	public List<CalendarAppointmentDTO> getChildAppointmentsByParentId(String userId,String parentId,String taskDate,String moduleName) {
		String sql="select id,id as moduleId,\"Subject\" as subject,\"Task Date\" as taskDate, \"Task Note\" as taskNote, \"Status\" as status,\"GPS\" as gps,"
				+ "\"Priority\" as periority,appointment_title as title,taskend as taskEnd,attendees_email as attendeesEmail,appointment_location as address,"
				+ "time_zone_type as timeZone,TO_CHAR(\"org_taskDate\"::timestamp,'MM/dd/yyyy hh12:mi AM')  as orgTaskDate,TO_CHAR(\"org_taskend\"::timestamp,'MM/dd/yyyy hh12:mi AM') as orgTaskEnd,"
				+ "recurr_json as recurrJson,\"recurrence_id\" as recurrenceId from "+moduleName+"_calendar WHERE parent_id=" + parentId+ 
				" and Cast(\"Task Date\" as date) > Cast('"+taskDate+"' as date) and archive_status='new' order by id asc";
		List<CalendarAppointmentDTO> resultList = this.getJdbcTemplate(userId).query(sql.toString(),
				new BeanPropertyRowMapper<CalendarAppointmentDTO>(CalendarAppointmentDTO.class));
		return  resultList;
	}
	
	public Long addNote(ProgressNoteDTO noteDTO) throws Exception{
		  KeyHolder keyHolder = new GeneratedKeyHolder();
		  StringBuilder sql = new StringBuilder();
		  try {
			  System.out.println("-------------"+sql);
			  sql.append("INSERT INTO "+noteDTO.getModuleName()+"_note(");
			  sql.append(noteDTO.getModuleName()+"_id,");
			  sql.append("\"Subject\",");
			  sql.append("\"Visit Note\",");
			  sql.append("\"Personal Visit\",");
			  sql.append("created_by,");
			  sql.append("\"archive_status\",");
			  sql.append("created_date,");
			  sql.append(" user_id, ");
			  sql.append("\"GPS\",");
			  sql.append("\"Date\", ");
			  sql.append("date_time )");
			  
			  sql.append("VALUES(");
			  sql.append(noteDTO.getModuleId()+",");
			  sql.append("'"+noteDTO.getSubject()+"',");
			  sql.append("'"+noteDTO.getVisitNote()+"',");
			  sql.append("'"+noteDTO.getPersonalVisit()+"',");
			  sql.append("'"+noteDTO.getCreated_by()+"',");
			  sql.append("'New',");
			  sql.append("Now(),");
			  sql.append(noteDTO.getUserId()+",");
			  sql.append("'"+noteDTO.getGps()+"','");
			  sql.append(DateUtil.stringToStringFormatChange(noteDTO.getNoteDate(),DateUtil.FOR_API_RESPONSE_FORMAT)+"',");
			  sql.append("'"+noteDTO.getNoteTime()+"')");
			  System.out.println("=============="+sql);
			  System.out.println(sql.toString());
			  getJdbcTemplate(noteDTO.getUserId()).update(
						new PreparedStatementCreator() {
						    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						      return connection.prepareStatement(sql.toString(), new String[] {"id"});
						    }
						  }, keyHolder
						);
			  System.out.println(sql.toString());
			  System.out.println(keyHolder.getKey().intValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
		  return keyHolder.getKey().longValue();// .intValue();
	}
	
	public Boolean deleteOrArchivedRecord(ProgressNoteDTO noteDTO) throws Exception {
		 StringBuilder sql = new StringBuilder();
		 try {
			 sql.append("UPDATE "+noteDTO.getModuleName()+"_"+noteDTO.getModuleSuffix());
			 sql.append(" SET archived_by = '"+noteDTO.getUserName()+"',");
			 sql.append("archive_status = '"+noteDTO.getArchive_status()+"',");
			 sql.append("archived_date = Now(),");
			 sql.append("modified_date = Now(), ");
			 sql.append("modifiedby = '"+noteDTO.getUserName()+"'");
			 sql.append(" Where id ="+noteDTO.getId());
//			 sql.append(" and archive_status ILIKE \'New\' ");
			 System.out.println(sql);
			 System.out.println(sql.toString());
			 getJdbcTemplate(noteDTO.getUserId()).update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					return con.prepareStatement(sql.toString());
				}
			});
			 System.out.println(sql.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	public Boolean editNote(ProgressNoteDTO noteDTO) throws Exception{
		  StringBuilder sql = new StringBuilder();
		  try {
			  sql.append("UPDATE "+noteDTO.getModuleName()+"_note");
			  sql.append(" SET \"Subject\"= '"+noteDTO.getSubject()+"',");
			  sql.append("\"Visit Note\"= '"+noteDTO.getVisitNote()+"',");
			  sql.append("\"Personal Visit\"= '"+noteDTO.getPersonalVisit()+"', ");
			  sql.append("modifiedby = '"+noteDTO.getModifiedby()+"',");
			  sql.append("\"archive_status\"= '"+noteDTO.getArchive_status()+"',");
			  sql.append("modified_date = Now(),");
			  sql.append(" user_id = "+noteDTO.getUserId()+", ");
			  sql.append("\"GPS\"= '"+noteDTO.getGps()+"',");
			  sql.append("\"Date\"= '"+DateUtil.stringToStringFormatChange(noteDTO.getNoteDate(), DateUtil.FOR_DB_SAVE_FORMAT)+"', ");
			  sql.append("date_time = '"+noteDTO.getNoteTime()+"'");
			  sql.append(" WHERE id ="+noteDTO.getId());

			  System.out.println(sql.toString());
			  getJdbcTemplate(noteDTO.getUserId()).update(
						new PreparedStatementCreator() {
						    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						      return connection.prepareStatement(sql.toString());
						    }
						  }
						);
			  System.out.println(sql.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		  return true;
	}
	public Boolean editLead(ProgressNoteDTO noteDTO) throws Exception{
		  StringBuilder sql = new StringBuilder();
		  try {
			  sql.append("UPDATE "+noteDTO.getModuleName()+"_note");
			  sql.append(" SET \"Subject\"= '"+noteDTO.getSubject()+"',");
			  sql.append("\"Visit Note\"= '"+noteDTO.getVisitNote()+"',");
			  sql.append("\"Personal Visit\"= '"+noteDTO.getPersonalVisit()+"', ");
			  sql.append("modifiedby = '"+noteDTO.getModifiedby()+"',");
			  sql.append("\"archive_status\"= '"+noteDTO.getArchive_status()+"',");
			  sql.append("modified_date = Now(),");
			  sql.append(" user_id = "+noteDTO.getUserId()+", ");
			  sql.append("\"GPS\"= '"+noteDTO.getGps()+"',");
			  sql.append("\"Date\"= '"+DateUtil.stringToStringFormatChange(noteDTO.getNoteDate(), DateUtil.FOR_DB_SAVE_FORMAT)+"', ");
			  sql.append("date_time = '"+noteDTO.getNoteTime()+"'");
			  sql.append(" WHERE id ="+noteDTO.getModuleId());

			  System.out.println(sql.toString());
			  getJdbcTemplate(noteDTO.getUserId()).update(
						new PreparedStatementCreator() {
						    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						      return connection.prepareStatement(sql.toString());
						    }
						  }
						);
			  System.out.println(sql.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		  return true;
	}
	/**
	 * This method is used to fetch mail record by appointment/calendar id
	 * 
	 * @return list of mail record for recurr rule and uuid
	 */
	public List<AppointmentMailDTO> getMailDTO(String userId,String calendarId,String moduleName) {
		String sql="Select id,\"calendar_id\"as calendarId,uuid,\"status\" as status,rrule,\"mail_to\" as mailTo from "+moduleName+"_appointment_mail WHERE calendar_id ="+calendarId;
		List<AppointmentMailDTO> result = this.getJdbcTemplate(userId).query(sql.toString(),
				new BeanPropertyRowMapper<AppointmentMailDTO>(AppointmentMailDTO.class));
		return  result;
	}
	/**
	 * This method is used to fetch sibling of appointments
	 * 
	 * @return appointment list with same parent id
	 */
	public CalendarAppointmentDTO getOriginalAppointmentsById(String userId,String Id,String moduleName) {
		String sql="select \"Subject\" as subject,TO_CHAR(\"Task Date\"::timestamp,'MM/dd/yyyy hh12:mi AM') as taskDate, \"Task Note\" as taskNote, \"Status\" as status,\"GPS\" as gps,"
				+ "\"Priority\" as periority,appointment_title as title,TO_CHAR(taskend::timestamp,'MM/dd/yyyy hh12:mi AM') as taskEnd,attendees_email as attendeesEmail,appointment_location as address,"
				+ "time_zone_type as timeZone,\"org_taskDate\" as orgTaskDate,\"recurr_json\" as recurrJson,\"org_taskend\" as orgTaskEnd,\"recurrence_id\" as recurrenceId from "+moduleName+"_calendar WHERE id=" + Id+ 
				" and archive_status='new'";
		CalendarAppointmentDTO result = this.getJdbcTemplate(userId).queryForObject(sql.toString(),
				new BeanPropertyRowMapper<CalendarAppointmentDTO>(CalendarAppointmentDTO.class));
		return  result;
	}
	
	/**
	 * This method is used to fetch recurrence appointments
	 * 
	 * @return appointments list by parent id
	 */
	public List<CalendarAppointmentDTO> getChildAppointmentsById(String userId,String Id,String moduleName) {
		String sql="select id,id as moduleId,\"Subject\" as subject,\"Task Date\" as taskDate, \"Task Note\" as taskNote, \"Status\" as status,\"GPS\" as gps,"
				+ "\"Priority\" as periority,appointment_title as title,taskend as taskEnd,attendees_email as attendeesEmail,appointment_location as address,"
				+ "time_zone_type as timeZone,TO_CHAR(\"org_taskDate\"::timestamp,'MM/dd/yyyy hh12:mi AM') as orgTaskDate,TO_CHAR(\"org_taskend\"::timestamp,'MM/dd/yyyy hh12:mi AM') as orgTaskEnd,"
				+ "recurr_json as recurrJson,\"recurrence_id\" as recurrenceId from "+moduleName+"_calendar WHERE parent_id=" + Id+" and archive_status='new' order by id asc";
		List<CalendarAppointmentDTO> resultList = this.getJdbcTemplate(userId).query(sql.toString(),
				new BeanPropertyRowMapper<CalendarAppointmentDTO>(CalendarAppointmentDTO.class));
		return  resultList;
	}
	public String updateMailStatus(String userId,CalendarAppointmentDTO appointment,String status,String rrule,String mailTo)
	   {
	     	StringBuilder sql = new StringBuilder();	  
	      	sql.append( "update " + appointment.getModuleName() + "_appointment_mail Set\"status\"='"+status+"',\"rrule\"= '"+rrule+"',\"mail_to\"='"+mailTo+"'");
	      	System.out.println(sql);
	      	return String.valueOf(this.getJdbcTemplate(userId).update(sql.toString()));
 }
	
	
	/**
	 * This method is used to fetch all appointments in recurrence
	 * 
	 * @return appointment list with same parent id
	 */
	public List<CalendarAppointmentDTO> getAllAppointmentsByParentId(String userId,String parentId,long appointmentId,String moduleName) {
		String sql="select id,id as moduleId,\"Subject\" as subject,\"Task Date\" as taskDate, \"Task Note\" as taskNote, \"Status\" as status,\"GPS\" as gps,"
				+ "\"Priority\" as periority,appointment_title as title,taskend as taskEnd,attendees_email as attendeesEmail,appointment_location as address,"
				+ "time_zone_type as timeZone,TO_CHAR(\"org_taskDate\"::timestamp,'MM/dd/yyyy hh12:mi AM')  as orgTaskDate,TO_CHAR(\"org_taskend\"::timestamp,'MM/dd/yyyy hh12:mi AM') as orgTaskEnd,"
				+ "recurr_json as recurrJson,\"recurrence_id\" as recurrenceId from "+moduleName+"_calendar WHERE (parent_id=" + parentId+ " OR id=" + parentId+")"+
				" and id NOT IN("+appointmentId+") and archive_status='new' order by id asc";
		List<CalendarAppointmentDTO> resultList = this.getJdbcTemplate(userId).query(sql.toString(),
				new BeanPropertyRowMapper<CalendarAppointmentDTO>(CalendarAppointmentDTO.class));
		return  resultList;
	}
	public List<ContactDTO> fetchConctactList(String userId) throws Exception{
		//List<ContactDTO> resultList = null;
		String sql = "select contact.id as id, lead.\"Facility Name\" as leadName, contact.\"First Name\" as fname,"
				+ " contact.\"Last Name\" as lname, contact.\"Contact Role\" as contactRole,contact.\"Address\" as address,"
				+ " contact.\"Mobile Phone Number\" as phone, contact.\"Email Id\" as email, contact.lead_id as leadId,"
				+ " contact.created_by as createdBy, contact.modified_by as modifiedBy , contact.\"modified_date\" as modifiedDate "
				+ "from contact, lead  where contact.lead_id = lead.id and contact.user_id = "+userId+"  order by id asc ";
			System.out.println(sql);
			 List<ContactDTO> resultList = getJdbcTemplate(userId).query(sql.toString(),new BeanPropertyRowMapper<ContactDTO>(ContactDTO.class));
			System.out.println("ResultSet : "+resultList);
		return resultList;
}
	public ContactDTO fetchContactDetailById(String userId, int id)  throws Exception {
		String sql = "select contact.id as id, lead.\"Facility Name\" as leadName, contact.\"First Name\" as fname,"
				+ "contact.\"Last Name\" as lname, contact.\"Contact Role\" as contactRole,contact.\"Address\" as address,"
				+ "contact.\"Mobile Phone Number\" as phone, contact.\"Email Id\" as email, contact.lead_id as leadId,"
				+ "contact.created_by as createdBy, contact.modified_by as modifiedBy , "
				+ "contact.\"modified_date\" as modifiedDate "
				+ "from contact, lead  where contact.lead_id = lead.id and "
				+ "contact.user_id = "+userId+" and contact.id = "+id;
		System.out.println(sql.toString());	
		ContactDTO result = this.getJdbcTemplate(userId).queryForObject(sql.toString(),
				new BeanPropertyRowMapper<ContactDTO>(ContactDTO.class));
		return  result;
	}
	public Boolean deleteOrArchivedContact(ContactDTO contactDTO) throws Exception {
		 StringBuilder sql = new StringBuilder();
		 try {
			 sql.append("UPDATE contact");
			 sql.append(" SET archived_by = '"+contactDTO.getUserName()+"',");
			 sql.append("archive_status = '"+contactDTO.getArchivedStatus()+"',");
			 sql.append("archived_date = Now(),");
			 sql.append("modified_date = Now(), ");
			 sql.append("modified_by = '"+contactDTO.getUserName()+"'");
			 sql.append(" Where id ="+contactDTO.getId());
//			 sql.append(" and archive_status ILIKE \'New\' ");
			 System.out.println(sql);
			 getJdbcTemplate(contactDTO.getUserId().toString()).update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					return con.prepareStatement(sql.toString());
				}
			});
			 System.out.println(sql.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	public Boolean deleteOrArchivedContactAsIds(ContactDTO contactDTO) throws Exception {
		 StringBuilder sql = new StringBuilder();
		 try {
			 sql.append("UPDATE contact_ids");
			 sql.append(" SET archived_by = '"+contactDTO.getUserName()+"',");
			 sql.append("archive_status = '"+contactDTO.getArchivedStatus()+"',");
			 sql.append("archived_date = Now() ");
			 sql.append(" Where id ="+contactDTO.getContactIdsAsid());
			 System.out.println(sql);

			 getJdbcTemplate(contactDTO.getUserId().toString()).update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					return con.prepareStatement(sql.toString());
				}
			});
			 System.out.println(sql.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	public Long addContact(ContactDTO dto, long contactAs_id) throws Exception {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		StringBuilder sql = new StringBuilder();
		  System.out.println("Dao class fuction addContact entered.");
		sql.append("INSERT INTO contact (");
		/* sql.append("  \"Facility Name\","); */
		/* sql.append(" \"Account Type\","); */
		sql.append(" \"First Name\", \"Last Name\",");
		sql.append(" \"Contact Role\",");
		sql.append(" \"Address\",");
		sql.append("\"Mobile Phone Number\",");
		sql.append(" \"Email Id\",");
		sql.append(" lead_id,account_id,user_id,");
		sql.append(" created_by,");
		sql.append(" \"archive_status\",");
		sql.append(" \"Mailing Street\",\"Mailing City\",");
		sql.append(" \"Mailing State\",\"Mailing Zip\",\"Mailing Country\",");
		sql.append(" \"Description\",");
		sql.append(" \"contact_ids_id\",");
		sql.append(" \"lead_contact_role\",");
		sql.append(" \"uuid\",");
		sql.append(" \"Decision Maker\",");
		sql.append(" created_date )");
		sql.append(" Values (");
		/* sql.append("'"+dto.getUserName()+"',"); */
		/* sql.append(""+dto.getAccountType()+","); */
		sql.append("'"+dto.getFname()+"' , '"+dto.getLname()+"',");
		sql.append("'"+dto.getContactRole()+"',");
		sql.append("'"+dto.getAddress() +"',");
        sql.append("'"+dto.getPhone()+"',");
		sql.append("'"+dto.getEmail()+"',");
		sql.append(""+dto.getLeadId()+","+dto.getAccountId()+", "+dto.getUserId()+",");
		sql.append("'"+dto.getCreatedBy()+"', ");
		sql.append("'"+dto.getArchivedStatus()+"',");
		sql.append("'"+dto.getMailingStreet()+"', '"+dto.getMailingCity()+"',");
		sql.append("'"+dto.getMailingState()+"',"+dto.getMailingZipCode()+", '"+dto.getMailingCountry()+"', ");
		sql.append("'"+dto.getDescription()+"', ");
		sql.append(""+contactAs_id+",");
		sql.append("'"+dto.getLeadContactRole()+"',");
		sql.append("'"+AppUtil.generateRandomStringValueAsUuid()+"',");
		sql.append(""+dto.getDecisionMaker()+",");
		sql.append("Now() )");
		 System.out.println("contactAs_id  : "+contactAs_id);
		  System.out.println(sql.toString());
		  getJdbcTemplate(dto.getUserId().toString()).update(
					new PreparedStatementCreator() {
					    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					      return connection.prepareStatement(sql.toString(), new String[] {"id"});
					    }
					  }, keyHolder
					);
		  System.out.println(sql.toString());
		  System.out.println(keyHolder.getKey().intValue());
		  return keyHolder.getKey().longValue();
	}
	public Long addContactIds(ContactDTO dto) throws Exception{
		KeyHolder keyHolder = new GeneratedKeyHolder();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO contact_ids (");
			sql.append(" \"First Name\", \"Last Name\",");
			sql.append("user_id,");
			sql.append(" \"archive_status\",");
			sql.append(" \"uuid\",");
			sql.append(" created_date )");
			sql.append(" Values (");
			sql.append("'"+dto.getFname()+"' , '"+dto.getLname()+"',");
			sql.append(""+dto.getUserId()+",");
			sql.append("'"+dto.getArchivedStatus()+"',");
			sql.append("'"+AppUtil.generateRandomStringValueAsUuid()+"',");
			sql.append("Now() )");
		
			  System.out.println(sql.toString());
			  getJdbcTemplate(dto.getUserId().toString()).update(
						new PreparedStatementCreator() {
						    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						      return connection.prepareStatement(sql.toString(), new String[] {"id"});
						    }
						  }, keyHolder
						);
			  System.out.println(sql.toString());
			  System.out.println(keyHolder.getKey().intValue());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e);
		}
		
		  return keyHolder.getKey().longValue();
	}
	
	/**
	 * This method is used to fetch mail record by appointment/calendar id
	 * 
	 * @return list of mail record for recurr rule and uuid
	 */
//	public List<AppointmentMailDTO> getMailDTO(String userId,String calendarId,String moduleName) {
//		String sql="Select id,\"calendar_id\"as calendarId,uuid,\"status\" as status,rrule,\"mail_to\" as mailTo from "+moduleName+"_appointment_mail WHERE calendar_id ="+calendarId;
//		List<AppointmentMailDTO> result = this.getJdbcTemplate(userId).query(sql.toString(),
//				new BeanPropertyRowMapper<AppointmentMailDTO>(AppointmentMailDTO.class));
//		return  result;
//	}
	
	
//	List<CalendarAppointmentDTO> getOtherMeetingsId() {
//	String sql="select \"Subject\" as subject,\"Task Date\" as taskDate, \"Task Note\" as taskNote, \"Status\" as status,\"GPS\" as gps,"
//			+ "\"Priority\" as periority,appointment_title as title,taskend as endTime,attendees_email as attendeesEmail,appointment_location as address,"
//			+ "time_zone_type as timeZone,org_taskDate as orgTaskDate,recurr_json as recurrJson from account_calendar where (id=1362 or parent_id=" + 
//			"case when(select distinct parent_id>0 from account_calendar where id=1362) " + 
//			"then (select parent_id from account_calendar where id=1362) " + 
//			"else (select distinct parent_id from account_calendar where parent_id=1362) " + 
//			"end) and Cast(\"Task Date\" as date) >= Cast('2021-02-09' as date) and archive_status='new'";
//	return null;
//}

///**
// * This method is used to fetch recurrence appointments
// * 
// * @return appointments list by parent id
// */
//public List<CalendarAppointmentDTO> getChildAppointmentsById(String userId,String Id,String moduleName) {
//	String sql="select id,id as moduleId,\"Subject\" as subject,\"Task Date\" as taskDate, \"Task Note\" as taskNote, \"Status\" as status,\"GPS\" as gps,"
//			+ "\"Priority\" as periority,appointment_title as title,taskend as taskEnd,attendees_email as attendeesEmail,appointment_location as address,"
//			+ "time_zone_type as timeZone,TO_CHAR(\"org_taskDate\"::timestamp,'MM/dd/yyyy hh12:mi AM') as orgTaskDate,TO_CHAR(\"org_taskend\"::timestamp,'MM/dd/yyyy hh12:mi AM') as orgTaskEnd,"
//			+ "recurr_json as recurrJson,TO_CHAR(\"recurrence_id\"::timestamp,'MM/dd/yyyy hh12:mi AM') as recurrenceId from "+moduleName+"_calendar WHERE parent_id=" + Id+" and archive_status='new' order by id asc";
//	List<CalendarAppointmentDTO> resultList = this.getJdbcTemplate(userId).query(sql.toString(),
//			new BeanPropertyRowMapper<CalendarAppointmentDTO>(CalendarAppointmentDTO.class));
//	return  resultList;
//}
}
