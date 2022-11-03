package com.ssa.Klozerz.mailing;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssa.Klozerz.dao.GeneralDAO;
import com.ssa.Klozerz.dto.AppointmentMailDTO;
import com.ssa.Klozerz.dto.CalendarAppointmentDTO;
import com.ssa.Klozerz.util.AppUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class MailingClass {
	@Autowired
	GeneralDAO gen;
	
	String recurrence_start_time;
	String recurrence_end_time;
	String recurrence_duration;
	String recurrence_pattern;
	String recurrence_pattern_sub;
	String recurrence_days_count;
	String recurrence_weeks_count;
	String recurrence_week_days;
	String recurrence_month_type;
	String recurrence_month_date;
	String recurrence_month_count;
	String recurrence_monthly_day;
	String recurrence_monthly_name;
	String recurrence_monthly_the_count;
	String recurrence_startdate;
	String recurrence_end_date_option;
	String recurrence_end_count;
	String recurrence_enddate;
	String recurrence_rule;
	String meetingdate;
	String meetingendDate;
	String recurrence_flag;
	String meetingAddress;
	String email;
	String subject;
	String note;
	String zone;
	
	String emailList[];
	
	boolean success=false;
	
	/**
	 * Method to send recurrence mail, prepare mail and save status to DB
	 * 
	 * @return
	 */	
public	boolean mailsendingfunction(CalendarAppointmentDTO recurrenceInfo,String AppointmentId,String userId)
	{
	complileRecurrenceData(recurrenceInfo);
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setUsername("dev.dannycrm@gmail.com");
	    mailSender.setPassword("dev.dannycrm@123");
	    Properties properties = new Properties();
//	    properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	    properties.put("mail.smtp.auth", "true");
	    properties.put("mail.smtp.starttls.enable", "true");
	  // properties.put("mail.smtp.host", "smtp-mail.outlook.com");
	    properties.put("mail.smtp.host", "smtp.gmail.com");
//	    properties.put("mail.smtp.port", "25");
	    properties.put("mail.smtp.port", "587");
	    mailSender.setJavaMailProperties(properties);
	    
	    
	    CalendarService calendarService = new CalendarService(mailSender);
	    CalendarRequest cr;
	    
	    
	    CalendarRequest calendarRequest;
	    try {
	    	if(recurrence_flag.equalsIgnoreCase("true"))
		{
	    		emailList=email.split(",");
	    	    for(String eMail:emailList)
	    	    {		
	    	    	 cr=new CalendarRequest.Builder()
	    	                 //.withSubject("CRM Meeting")
	    	         .withSubject(subject)        
	    	         //.withBody("This is a test event")
	    	         .withBody(note)
	    	                 .withToEmail(eMail)
	    	                 //.withStartDate("20201220")
	    	                 .withStartDate(meetingdate)
	    	                 //.withMeetingStartTime("0900")
	    	                 .withMeetingStartTime(recurrence_start_time)
	    	                 //.withStartDate("20201221")
	    	                 .withEndDate(meetingendDate)
	    	                 //.withMeetingEndTime("0915")
	    	                 .withMeetingEndTime(recurrence_end_time)
	    	                 .withRRule(recurrence_rule)
	    	                 .withAddress(meetingAddress)
	    	                 .withZone(zone)
	    	                 .build();
	    	 	   
	    	    	success=calendarService.sendCalendarInvite(
				        "dev.dannycrm@gmail.com",cr
	    				);
//	    	    	System.out.println(cr.getUid());
//	    	    	System.out.println(userId+","+recurrenceInfo!=null+","+AppointmentId+","+cr.getUid()+","+eMail);
	    			if(success)
	    			gen.saveMailStatus(userId,recurrenceInfo,AppointmentId,cr.getUid(),"send",recurrence_rule,eMail);
	    			else {
	    				gen.saveMailStatus(userId,recurrenceInfo,AppointmentId,cr.getUid(),"failed",recurrence_rule,eMail);
	    			}
	    	    }
	    	    
	    return success;
		}
	    	else {
	    		emailList=email.split(",");
	    		for(String emailt:emailList)
	    		{
	    			calendarRequest=new CalendarRequest.Builder()
	                //.withSubject("CRM Meeting")
	        .withSubject(subject)        
	        //.withBody("This is a test event")
	        .withBody(note)
	                .withToEmail(emailt)
	                //.withStartDate("20201220")
	                .withStartDate(meetingdate)
	                //.withMeetingStartTime("0900")
	                .withMeetingStartTime(recurrence_start_time)
	                //.withStartDate("20201221")
	                .withEndDate(meetingdate)
	                //.withMeetingEndTime("0915")
	                .withMeetingEndTime(recurrence_end_time)
	                .withAddress(meetingAddress)
	                .withZone(zone)
	                .build();
	    			
	    			
	    			success=	calendarService.sendCalendarInviteWithOutRecc(
				        "dev.dannycrm@gmail.com",calendarRequest
				  
	    	);
	    			if(success)
	    				gen.saveMailStatus(userId,recurrenceInfo,AppointmentId,calendarRequest.getUid(),"send", "RRULE:FREQ=DAILY;INTERVAL=1;COUNT=1",emailt);
	    			else {
	    				gen.saveMailStatus(userId,recurrenceInfo,AppointmentId,calendarRequest.getUid(),"failed", "RRULE:FREQ=DAILY;INTERVAL=1;COUNT=1",emailt);
	    			}
	    		}

	    			return success;
	    	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		//return false;
	}


/**
 * Method to create Recurrence Rule according to provided data
 * 
 * @return
 */	
	private void complileRecurrenceData(CalendarAppointmentDTO jsonReccurence)
	{
		/*Rule Tips:
		 * link for rules understading: https://icalendar.org/iCalendar-RFC-5545/3-8-5-3-recurrence-rule.html
		 * 
		 * use 'INTERVAL' for recurrence_days_count,recurrence_weeks_count 
		 * use 'COUNT' for recurrence_end_count
		 * use 'UNTIL' for recurrence_enddate eg:UNTIL=19971224T000000Z
		 * use 'BYDAY' for recurrence_week_days
		 * use 'BYMONTHDAY' for recurrence_month_date
		 * use 'BYSETPOS' for recurrence_monthly_day
		 * BYSETPOS
		 * */


		try {
			subject=jsonReccurence.getSubject();
			note=jsonReccurence.getTaskNote();
			if(note==null || note.isEmpty()) {note=subject;}
			email=jsonReccurence.getAttendeesEmail();
			meetingAddress=jsonReccurence.getAddress();
			zone=jsonReccurence.getTimeZone();
		
			
			SimpleDateFormat meetingDatesFormat=new SimpleDateFormat("MM/dd/yyyyy");
		//	meetingDatesFormat.setTimeZone(TimeZone.getTimeZone(zone));
			
//			System.out.println("zone set:"+zone);
			recurrence_flag=jsonReccurence.getRecurr().getRecurrenceFlag();
			if(recurrence_flag.toLowerCase().equalsIgnoreCase("true"))
			{
				String meetingDates=jsonReccurence.getRecurr().getRecurrenceMeetingDates()+"";
				String dateArray[]=meetingDates.split(",");

				recurrence_duration=jsonReccurence.getRecurr().getRecurrenceDuration();
				recurrence_startdate=jsonReccurence.getRecurr().getRecurrenceStartDate();
				recurrence_end_date_option=jsonReccurence.getRecurr().getRecurrenceEndDateOption();
				recurrence_start_time=jsonReccurence.getRecurr().getRecurrenceStartTime();
				recurrence_end_time=jsonReccurence.getRecurr().getRecurrenceEndTime();
				meetingdate=jsonReccurence.getRecurr().getRecurrenceStartDate();
				recurrence_enddate=jsonReccurence.getRecurr().getRecurrenceEndDate();

//				SimpleDateFormat mdate=new SimpleDateFormat("EEE MM/dd/yyyy");
			//	mdate.setTimeZone(TimeZone.getTimeZone(zone));
				SimpleDateFormat mdate=new SimpleDateFormat("MM/dd/yyyy");

				
				SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			//	sdf.setTimeZone(TimeZone.getTimeZone(zone));
				
				SimpleDateFormat timeform=new SimpleDateFormat("h:mm a");
			//	timeform.setTimeZone(TimeZone.getTimeZone(zone));
				
				SimpleDateFormat timesd=new SimpleDateFormat("HHmm");
			//	timesd.setTimeZone(TimeZone.getTimeZone(zone));
				
//				SimpleDateFormat recDte=new SimpleDateFormat("EEE MM/dd/yyyy");
			//	recDte.setTimeZone(TimeZone.getTimeZone(zone));
				SimpleDateFormat recDte=new SimpleDateFormat("MM/dd/yyyy");

				
				try {
						//new start dates by dates array
					String newStartdate=mdate.format(meetingDatesFormat.parse(dateArray[0]));
					String newEnddate=mdate.format(meetingDatesFormat.parse(dateArray[dateArray.length-1]));
					meetingdate=newStartdate;
					//to change format from mm/dd/yyyy to yyyymmdd
						Date meetdate=mdate.parse(meetingdate);
						meetingdate=sdf.format(meetdate);
						Calendar c=Calendar.getInstance();
						c.setTime(meetdate);
						c.add(Calendar.DATE, 1);
						meetingendDate=sdf.format(c.getTime());
						//sdf.format(mdate.parse(meetingdate));
						recurrence_start_time=timesd.format(timeform.parse(recurrence_start_time));
						recurrence_end_time=timesd.format(timeform.parse(recurrence_end_time));
						if(recurrence_enddate!="")
						{
							recurrence_enddate=newEnddate;
							recurrence_enddate=sdf.format(recDte.parse(recurrence_enddate));//+"T"+recurrence_end_time+"00Z";
						}
					} catch (Exception e) {
						e.printStackTrace();
						}

				recurrence_pattern=jsonReccurence.getRecurr().getRecurrencePattern();
		
		
		
				if(recurrence_pattern.equalsIgnoreCase("Daily"))
					{
					String Dates=jsonReccurence.getRecurr().getRecurrenceMeetingDates()+"";
					String datesArr[]=meetingDates.split(",");
					if(datesArr.length<2)
					{
						Calendar c=Calendar.getInstance();
						c.setTime(sdf.parse(recurrence_enddate));
						c.add(Calendar.DATE, 1);
						recurrence_enddate=sdf.format(c.getTime());
					}
						recurrence_pattern_sub=jsonReccurence.getRecurr().getRecurrencePatternSub();
						if(recurrence_pattern_sub.toLowerCase().equalsIgnoreCase("every"))
							{
								recurrence_days_count=jsonReccurence.getRecurr().getRecurrenceDaysCount();
									if(recurrence_end_date_option.toLowerCase().equalsIgnoreCase("no_end_date"))
										{
											recurrence_rule="FREQ="+recurrence_pattern.toUpperCase()+";"+"INTERVAL="+recurrence_days_count;
										}else if(recurrence_end_date_option.toLowerCase().equalsIgnoreCase("end_after"))
											{
												recurrence_end_count=jsonReccurence.getRecurr().getRecurrenceEndCount();
												recurrence_rule="FREQ="+recurrence_pattern.toUpperCase()+";INTERVAL="+recurrence_days_count+";COUNT="+recurrence_end_count;
											}
										else if(recurrence_end_date_option.toLowerCase().equalsIgnoreCase("end_by"))
										{

										//	recurrence_enddate=jsonReccurence.getString("recurrence_enddate");
											recurrence_rule="FREQ="+recurrence_pattern.toUpperCase()+";INTERVAL="+recurrence_days_count+";UNTIL="+recurrence_enddate+"T235900Z";
										}
							}
						else {
								if(recurrence_end_date_option.toLowerCase().equalsIgnoreCase("no_end_date"))
								{
									recurrence_rule="FREQ="+recurrence_pattern.toUpperCase()+";"+"BYDAY=MO,TU,WE,TH,FR";
								}else if(recurrence_end_date_option.toLowerCase().equalsIgnoreCase("end_after"))
									{
										recurrence_end_count=jsonReccurence.getRecurr().getRecurrenceEndCount();
										recurrence_rule="FREQ="+recurrence_pattern.toUpperCase()+";"+"BYDAY=MO,TU,WE,TH,FR"+";COUNT="+recurrence_end_count;
									}
								else if(recurrence_end_date_option.toLowerCase().equalsIgnoreCase("end_by"))
									{

										//recurrence_enddate=jsonReccurence.getString("recurrence_enddate");
										recurrence_rule="FREQ="+recurrence_pattern.toUpperCase()+";"+"BYDAY=MO,TU,WE,TH,FR"+";UNTIL="+recurrence_enddate+"T235900Z";
									}
							}
					}else if(recurrence_pattern.toLowerCase().equalsIgnoreCase("weekly"))
						{
							recurrence_weeks_count=jsonReccurence.getRecurr().getRecurrenceWeeksCount();
							recurrence_week_days=jsonReccurence.getRecurr().getRecurrenceWeekDays();

							if(recurrence_end_date_option.toLowerCase().equalsIgnoreCase("no_end_date"))
								{
								recurrence_rule="FREQ="+recurrence_pattern.toUpperCase()+";INTERVAL="+recurrence_weeks_count+";BYDAY="+recurrence_week_days;
								}else if(recurrence_end_date_option.toLowerCase().equalsIgnoreCase("end_after"))
									{
										recurrence_end_count=jsonReccurence.getRecurr().getRecurrenceEndCount();
										recurrence_rule="INTERVAL="+recurrence_weeks_count+";FREQ="+recurrence_pattern.toUpperCase()+";BYDAY="+recurrence_week_days+";COUNT="+recurrence_end_count;
										//    			meetingendDate="";
									}
									else if(recurrence_end_date_option.toLowerCase().equalsIgnoreCase("end_by"))
										{
										//recurrence_enddate=jsonReccurence.getString("recurrence_enddate");
										recurrence_rule="FREQ="+recurrence_pattern.toUpperCase()+";BYDAY="+recurrence_week_days+";INTERVAL="+recurrence_weeks_count+";UNTIL="+recurrence_enddate+"T235900Z";
										meetingendDate=recurrence_enddate;
										}
						}
					else if(recurrence_pattern.toLowerCase().equalsIgnoreCase("monthly"))
						{
							recurrence_month_type=jsonReccurence.getRecurr().getRecurrenceMonthType();
							if(recurrence_month_type.toLowerCase().equalsIgnoreCase("day"))
								{
									recurrence_month_date=jsonReccurence.getRecurr().getRecurrenceMonthDate();
									recurrence_month_count=jsonReccurence.getRecurr().getRecurrenceMonthCount();
									if(recurrence_end_date_option.toLowerCase().equalsIgnoreCase("no_end_date"))
										{
										recurrence_rule="FREQ="+recurrence_pattern.toUpperCase()+";INTERVAL="+recurrence_month_count+";BYDAY="+recurrence_month_date;
										}else if(recurrence_end_date_option.toLowerCase().equalsIgnoreCase("end_after"))
										{
											recurrence_end_count=jsonReccurence.getRecurr().getRecurrenceEndCount();
											
											recurrence_rule="FREQ="+recurrence_pattern.toUpperCase()+";INTERVAL="+recurrence_month_count+";BYDAY="+recurrence_month_date+";COUNT="+recurrence_end_count;
											
										//	meetingendDate="20211231";
										}
										else if(recurrence_end_date_option.toLowerCase().equalsIgnoreCase("end_by"))
											{
											//recurrence_enddate=jsonReccurence.getString("recurrence_enddate");
											recurrence_rule="FREQ="+recurrence_pattern.toUpperCase()+";INTERVAL="+recurrence_month_count+";BYDAY="+recurrence_month_date+";UNTIL="+recurrence_enddate+"T235900Z";
											}
								}
							else if(recurrence_month_type.toLowerCase().equalsIgnoreCase("the"))
								{
									String day="",MnameABB="";
        		
									recurrence_monthly_day=jsonReccurence.getRecurr().getRecurrenceMonthlyDay();
									if(recurrence_monthly_day.toLowerCase().equalsIgnoreCase("first"))
									{
										day="1";
									}
									else if(recurrence_monthly_day.toLowerCase().equalsIgnoreCase("second"))
									{
										day="2";
									}else if(recurrence_monthly_day.toLowerCase().equalsIgnoreCase("third"))
									{
										day="3";
									}else if(recurrence_monthly_day.toLowerCase().equalsIgnoreCase("fourth"))
									{
										day="4";
									}else if(recurrence_monthly_day.toLowerCase().equalsIgnoreCase("last"))
									{
										day="-1";
									}
        		
									recurrence_monthly_name=jsonReccurence.getRecurr().getRecurrenceMonthlyName();
									if(recurrence_monthly_name.equalsIgnoreCase("Sunday"))
										{
											MnameABB="SU";
										}else if(recurrence_monthly_name.equalsIgnoreCase("Monday"))
											{
												MnameABB=day+"MO";
										}else if(recurrence_monthly_name.equalsIgnoreCase("Tuesday"))
											{
												MnameABB=day+"TU";
										}else if(recurrence_monthly_name.equalsIgnoreCase("Wednesday"))
											{
												MnameABB=day+"WE";
										}else if(recurrence_monthly_name.equalsIgnoreCase("Thursday"))
											{
												MnameABB=day+"TH";
										}else if(recurrence_monthly_name.equalsIgnoreCase("Friday"))
											{
												MnameABB=day+"FR";
										}else if(recurrence_monthly_name.equalsIgnoreCase("Saturday"))
											{	
												MnameABB=day+"SA";
										}else if(recurrence_monthly_name.equalsIgnoreCase("Day"))
											{
												MnameABB=day+"SU,"+"MO,"+day+"TU,"+day+"WE,"+day+"TH,"+day+"FR,"+day+"SA";
										}else if(recurrence_monthly_name.equalsIgnoreCase("Weekday"))
											{
												MnameABB=day+"MO,"+day+"TU,"+day+"WE,"+day+"TH,"+day+"FR";
										}else if(recurrence_monthly_name.equalsIgnoreCase("Weekend day"))
											{
												MnameABB=day+"SU,"+day+"SA";
											}
									recurrence_monthly_the_count=jsonReccurence.getRecurr().getRecurrenceMonthlyTheCount();
               	
									if(recurrence_end_date_option.toLowerCase().equalsIgnoreCase("no_end_date"))
										{
										//recurrence_rule="FREQ="+recurrence_pattern.toUpperCase()+";INTERVAL="+recurrence_monthly_the_count+";BYSETPOS="+day+";BYDAY="+MnameABB;
										recurrence_rule="INTERVAL="+recurrence_monthly_the_count+";FREQ="+recurrence_pattern.toUpperCase()+";BYDAY="+MnameABB;
										}else if(recurrence_end_date_option.toLowerCase().equalsIgnoreCase("end_after"))
											{
											recurrence_end_count=jsonReccurence.getRecurr().getRecurrenceEndCount();
											recurrence_rule="INTERVAL="+recurrence_monthly_the_count+";FREQ="+recurrence_pattern.toUpperCase()+";COUNT="+recurrence_end_count+";BYDAY="+MnameABB;
											}
										else if(recurrence_end_date_option.toLowerCase().equalsIgnoreCase("end_by"))
											{
											//recurrence_enddate=jsonReccurence.getString("recurrence_enddate");
											recurrence_rule="INTERVAL="+recurrence_monthly_the_count+";FREQ="+recurrence_pattern.toUpperCase()+";UNTIL="+recurrence_enddate+"T235900Z"+";BYDAY="+MnameABB;
											}
								}
    		 
        	
						}
				}else {
					String medate=jsonReccurence.getTaskDate();
//					SimpleDateFormat mdateformat=new SimpleDateFormat("yyyy-MM-dd hh:mm a");
					SimpleDateFormat mdateformat=new SimpleDateFormat("MM/dd/yyyy hh:mm a");
					mdateformat.setTimeZone(TimeZone.getTimeZone(zone));
					String meetingend=jsonReccurence.getTaskEnd();
					SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
					sdf.setTimeZone(TimeZone.getTimeZone(zone));
					
					SimpleDateFormat timesd=new SimpleDateFormat("HHmm");
					timesd.setTimeZone(TimeZone.getTimeZone(zone));
					
					try {
							//to change format from mm/dd/yyyy to yyyymmdd
						Date meetiDate=mdateformat.parse(medate);
						Date meetienddate=mdateformat.parse(meetingend);
							meetingdate=sdf.format(meetiDate);
							recurrence_start_time=timesd.format(meetiDate);
							recurrence_end_time=timesd.format(meetienddate);
							meetingendDate=meetingdate;
						} catch (Exception e) {
							e.printStackTrace();
							}
						}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/**
	 * Method to update recurrence mail,it prepares mail and save status to DB
	 * 
	 * @return
	 */		
	public	boolean mailUpdateAllfunction(CalendarAppointmentDTO recurrenceInfo,String AppointmentId,String userId,List<AppointmentMailDTO> mails)
	{
	complileRecurrenceData(recurrenceInfo);
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setUsername("dev.dannycrm@gmail.com");
	    mailSender.setPassword("dev.dannycrm@123");
	    Properties properties = new Properties();
//	    properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	    properties.put("mail.smtp.auth", "true");
	    properties.put("mail.smtp.starttls.enable", "true");
	  // properties.put("mail.smtp.host", "smtp-mail.outlook.com");
	    properties.put("mail.smtp.host", "smtp.gmail.com");
//	    properties.put("mail.smtp.port", "25");
	    properties.put("mail.smtp.port", "587");
	    mailSender.setJavaMailProperties(properties);
	    
	    
	    CalendarService calendarService = new CalendarService(mailSender);
	    CalendarRequest cr;
	    
	    
	    CalendarRequest calendarRequest;
	    try {
	    	if(recurrence_flag.equalsIgnoreCase("true"))
		{
	    		emailList=email.split(",");
	    	    for(String eMail:emailList)
	    	    {		
	    	    	 cr=new CalendarRequest.Builder()
	    	                 //.withSubject("CRM Meeting")
	    	         .withSubject(subject)        
	    	         //.withBody("This is a test event")
	    	         .withBody(note)
	    	                 .withToEmail(eMail)
	    	                 //.withStartDate("20201220")
	    	                 .withStartDate(meetingdate)
	    	                 //.withMeetingStartTime("0900")
	    	                 .withMeetingStartTime(recurrence_start_time)
	    	                 //.withStartDate("20201221")
	    	                 .withEndDate(meetingendDate)
	    	                 //.withMeetingEndTime("0915")
	    	                 .withMeetingEndTime(recurrence_end_time)
	    	                 .withRRule(recurrence_rule)
	    	                 .withAddress(meetingAddress)
	    	                 .withZone(zone)
	    	                 .build();
	    	    /**/
	    	    	 for(AppointmentMailDTO m:mails)	 
	    	 	 {
	    	    	if(m.getMailTo().contentEquals(eMail))
	    	    	cr.setUid(m.getUuid());
	    	    }
	    	    /**/
	    	    	success=calendarService.updateAllCalendarInvite(
				        "dev.dannycrm@gmail.com",cr,recurrenceInfo
	    				);
//	    	    	System.out.println(cr.getUid());
//	    	    	System.out.println(userId+","+recurrenceInfo!=null+","+AppointmentId+","+cr.getUid()+","+eMail);
	    			if(success)
	    			{	
	    				if(!mails.isEmpty() && mails!=null)
	    					mails.forEach(mail->{
	    							if(mail.getMailTo().contentEquals(eMail))
	    								{
	    									gen.updateMailStatus(userId,recurrenceInfo,"send",recurrence_rule,eMail);
	    								}
	    								});
	    					else {
	    				gen.saveMailStatus(userId,recurrenceInfo,AppointmentId,cr.getUid(),"send",recurrence_rule,eMail);
	    					}
	    			}	
	    			else {
	    				gen.saveMailStatus(userId,recurrenceInfo,AppointmentId,cr.getUid(),"failed",recurrence_rule,eMail);
	    			}
	    	    }
	    	    
	    return success;
		}
	    	else {
	    		emailList=email.split(",");
	    		for(String emailt:emailList)
	    		{
	    			calendarRequest=new CalendarRequest.Builder()
	                //.withSubject("CRM Meeting")
	        .withSubject(subject)        
	        //.withBody("This is a test event")
	        .withBody(note)
	                .withToEmail(emailt)
	                //.withStartDate("20201220")
	                .withStartDate(meetingdate)
	                //.withMeetingStartTime("0900")
	                .withMeetingStartTime(recurrence_start_time)
	                //.withStartDate("20201221")
	                .withEndDate(meetingdate)
	                //.withMeetingEndTime("0915")
	                .withMeetingEndTime(recurrence_end_time)
	                .withAddress(meetingAddress)
	                .withZone(zone)
	                .build();
	    			
	    			/**/
	    	    	 for(AppointmentMailDTO m:mails)	 
	    	 	 {
	    	    	if(m.getMailTo().contentEquals(emailt))
	    	    		calendarRequest.setUid(m.getUuid());
	    	    }
	    	    /**/	
	    			success=	calendarService.sendCalendarInviteWithOutRecc(
				        "dev.dannycrm@gmail.com",calendarRequest
				  
	    	);
	    			if(success)
	    			{	
	    				if(!mails.isEmpty() && mails!=null)
	    					mails.forEach(mail->{
	    							if(mail.getMailTo().contentEquals(emailt))
	    								{
	    									gen.updateMailStatus(userId,recurrenceInfo,"send", "RRULE:FREQ=DAILY;INTERVAL=1;COUNT=1",emailt);
	    								}
	    								});
	    					else {
	    				gen.saveMailStatus(userId,recurrenceInfo,AppointmentId,calendarRequest.getUid(),"send", "RRULE:FREQ=DAILY;INTERVAL=1;COUNT=1",emailt);
	    					}
	    			}else {
	    				gen.saveMailStatus(userId,recurrenceInfo,AppointmentId,calendarRequest.getUid(),"failed", "RRULE:FREQ=DAILY;INTERVAL=1;COUNT=1",emailt);
	    			}
	    		}

	    			return success;
	    	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		//return false;
	}
	
	
	/**
	 * Method to send update single meeting in recurrence, prepare mail and save status to DB
	 * 
	 * @return
	 */	
	public	boolean mailUpdatefunction(List<CalendarAppointmentDTO> recurrenceInfo,String AppointmentId,String userId,List<AppointmentMailDTO> mails,CalendarAppointmentDTO orgAppointment)
	{
	complileRecurrenceData(recurrenceInfo.get(0));
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setUsername("dev.dannycrm@gmail.com");
	    mailSender.setPassword("dev.dannycrm@123");
	    Properties properties = new Properties();
//	    properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	    properties.put("mail.smtp.auth", "true");
	    properties.put("mail.smtp.starttls.enable", "true");
	  // properties.put("mail.smtp.host", "smtp-mail.outlook.com");
	    properties.put("mail.smtp.host", "smtp.gmail.com");
//	    properties.put("mail.smtp.port", "25");
	    properties.put("mail.smtp.port", "587");
	    mailSender.setJavaMailProperties(properties);
	    
	    
	    CalendarService calendarService = new CalendarService(mailSender);
	    CalendarRequest cr;
	    
	    
	    CalendarRequest calendarRequest;
	    try {
	    		emailList=email.split(",");
	    	    for(String eMail:emailList)
	    	    {		
	    	    	 cr=new CalendarRequest.Builder()
	    	         .withSubject(subject)        
	    	         .withBody(note)
	    	                 .withToEmail(eMail)
	    	                 .withStartDate(meetingdate)
	    	                 .withMeetingStartTime(recurrence_start_time)
	    	                 .withEndDate(meetingendDate)
	    	                 .withMeetingEndTime(recurrence_end_time)
	    	                 .withRRule(recurrence_rule)
	    	                 .withAddress(meetingAddress)
	    	                 .withZone(zone)
	    	                 .build();
	    	    /**/
	    	    	 for(AppointmentMailDTO m:mails)	 
	    	 	 {
	    	    	if(m.getMailTo().contentEquals(eMail))
	    	    	cr.setUid(m.getUuid());
	    	    }
	    	    /**/
	    	    	success=calendarService.editCalendarInviteForChild(
				        "dev.dannycrm@gmail.com",cr,orgAppointment,recurrenceInfo
	    				);
	    			if(success)
	    			{	
	    				if(!mails.isEmpty() && mails!=null)
	    					mails.forEach(mail->{
	    							if(mail.getMailTo().contentEquals(eMail))
	    								{
	    									gen.updateMailStatus(userId,recurrenceInfo.get(0),"send",recurrence_rule,eMail);
	    								}
	    								});
	    					else {
	    				gen.saveMailStatus(userId,recurrenceInfo.get(0),AppointmentId,cr.getUid(),"send",recurrence_rule,eMail);
	    					}
	    			}	
	    			else {
	    				gen.saveMailStatus(userId,recurrenceInfo.get(0),AppointmentId,cr.getUid(),"failed",recurrence_rule,eMail);
	    			}
	    	    }
	    	    
	    return success;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		//return false;
	}
	

}
