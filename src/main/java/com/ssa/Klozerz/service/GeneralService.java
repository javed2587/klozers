package com.ssa.Klozerz.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ssa.Klozerz.common.JsonResponse;
import com.ssa.Klozerz.dao.GeneralDAO;
import com.ssa.Klozerz.dto.AppointmentMailDTO;
import com.ssa.Klozerz.dto.CalendarAppointmentDTO;
import com.ssa.Klozerz.dto.ContactDTO;
import com.ssa.Klozerz.dto.ProgressNoteDTO;
import com.ssa.Klozerz.dto.RecurrenceDTO;
import com.ssa.Klozerz.mailing.MailingClass;
import com.ssa.Klozerz.util.AppUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class GeneralService {
	private static final Logger logger = LogManager.getLogger(AppUserService.class);

	@Autowired
	GeneralDAO general;
	@Autowired
	MailingClass sender;
	@Autowired
	GeneralDAO generalDao;
	
	/**
	 * This method is used to fetch notes by moduleName (lead or account) by lead_id or account_id
	 * 
	 * moduleName required
	 * moduleId required
	 * userId required
	 * 
	 * @return note list with same moduleId (leadId or accountId)
	 */
	public JsonResponse getProgressNotesByModuleName(ProgressNoteDTO note) {
		JsonResponse response=new JsonResponse();
		List<ProgressNoteDTO> notesList;
		try {
		notesList=generalDao.getNotesByModuleNameAndId(note.getUserId(), note.getModuleName().toLowerCase(), note.getModuleId());
		notesList.forEach(action -> {
			action.setModuleName(note.getModuleName().toLowerCase());
		});
		response.setData(notesList);
		response.setTotalRecords(notesList.size());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * This method to create appointments + calculate meeting startTime and endTime by timeZone + calculate recurrence + mail
	 * 
	 * moduleName (account/lead/personal) required
	 * moduleId (leadId/accountId/userId) required
	 * taskDate required
	 * taskEnd required
	 * recurrenceFlag required
	 * status required
	 * userId required
	 * timeZone required
	 * profileZone required
	 * 
	 * @return appointment id
	 */
	public JsonResponse addAppointment(CalendarAppointmentDTO appointment) {
		JsonResponse response=new JsonResponse();
		boolean ok=false;
		try {
			if(AppUtil.isNullOrEmpty(appointment.getRecurr().getRecurrenceFlag())) {
				appointment.getRecurr().setRecurrenceFlag("false");
			}
			String responseText=general.createCalendarTask(String.valueOf(appointment.getUserId()), appointment);
		if(!AppUtil.isNullOrEmpty(appointment.getAttendeesEmail()))
		{
			if(!responseText.equalsIgnoreCase("0"))
            {ok=sender.mailsendingfunction(appointment,responseText,String.valueOf(appointment.getUserId()));
            if(ok)
            	responseText="1";
            else
            	responseText="0";
//            response.setData(responseText);
            }
		}else {		
			if(Integer.parseInt(AppUtil.getSafeStr(responseText, "0"))>0)
				{responseText="1";}else {responseText="0";}
			}
		response.setErrorMessage("Record Saved");
		response.setData(responseText);
		}catch(Exception e) {
			e.printStackTrace();
			response.setErrorMessage("Server Error");
		}
		return response;
	}
	
	/**
	 * This method is used to fetch appointments
	 * 
	 * moduleName (lead/account) required
	 * moduleId (leadId/accountId) required
	 * userId required
	 * 
	 * @return appointment list
	 */
	public List<CalendarAppointmentDTO> fetchAppointments(CalendarAppointmentDTO calDto) {
		//CalendarAppointmentDTO dashBoardDTO = new CalendarAppointmentDTO();
		List<CalendarAppointmentDTO> list=null; //= new List<CalendarAppointmentDTO>();
		try {
		    list=generalDao.getModuleViseCampaigns(calDto.getUserId(),calDto.getModuleId(), calDto.getModuleName());
		    list.forEach(appointmentObj->{
		    	RecurrenceDTO recurr=new RecurrenceDTO();
//		    	System.out.println(appointmentObj.getRecurrenceString());
		    	if(appointmentObj.getRecurrenceString()!=null)
		    	{
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
		} catch (Exception e) {
			logger.error("Exception# GeneralService->fetchAppointments# ",e);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * This method is used to create note or progressNote
	 * 
	 * moduleName (lead,account) required
	 * moduleId (leadId,accountId) required
	 * noteDate required
	 * noteTime required
	 * subject/visit note required
	 * 
	 * @return note id
	 */
	public long addProgressNote(ProgressNoteDTO noteDTO) {
		long noteId;
		try {
			System.out.println("noteDTO: "+noteDTO);
			noteId =	generalDao.addNote(noteDTO);
			System.out.println("noteId :"+noteId);
		} catch (Exception e) {
			logger.error("Exception# GeneralService->addProgressNote# ",e);
			e.printStackTrace();
			return 0;
		}
		return noteId;
		}
	public boolean deleteOrArchived(ProgressNoteDTO noteDTO) {
		boolean isUpdate = false;
		try {
			 
			isUpdate =  generalDao.deleteOrArchivedRecord(noteDTO);
			System.out.println(isUpdate);
			} catch (Exception e) {
			logger.error("Exception# GeneralService->deleteOrArchived# ",e);
			e.printStackTrace();
			return isUpdate;
		}
		return isUpdate;
	}
	public boolean editProgressNote(ProgressNoteDTO noteDTO) {
	
		try {
			return 	generalDao.editNote(noteDTO);
		} catch (Exception e) {
			logger.error("Exception# GeneralService->addProgressNote# ",e);
			e.printStackTrace();
		}
		return false;
		}
	
	/**
	 * This method used to update appointments single/complete series, recurrence/ without recurrence
	 * 
	 * Note: in case of single appointment update all the changed meetings will also be referred in ICS Mailing file
	 * and for complete series all the changes made by single meeting will over-ridden to original dates
	 * 
	 * id required
	 * taskDate required
	 * endDate required
	 * moduleId required
	 * timeZone required
	 * profileZone required
	 * userId required
	 * parentId required
	 * updateAll required
	 * moduleName (lead/account/personal)required
	 * subject required
	 * status required
	 * recurrenceFlag required
	 * 
	 * @return 
	 */
	public JsonResponse updateAppointment(CalendarAppointmentDTO appointment) {
		JsonResponse response=new JsonResponse();
		List<CalendarAppointmentDTO> childrens= new ArrayList<CalendarAppointmentDTO>();
		List<AppointmentMailDTO> mails= new ArrayList<AppointmentMailDTO>();
		boolean ok=false;
		//is recurrence appointment
		if(appointment.getRecurr().getRecurrenceFlag().toLowerCase().equals("true"))
		{
			// update all future meetings
			if(appointment.isUpdateAll()) {	
			
				response.setData(this.updateAll(appointment, childrens, mails, ok));
				response.setErrorMessage("Record updated");
				
			} 
			//update single meeting inside recurrence
			else {
				// check if appointment is child or parent
				if(appointment.getParentId().equals("0") )
				{
				
					return response=this.singleParentMeeting(appointment, mails, ok, response);
				
				}else if( Integer.parseInt(appointment.getParentId())>0 ) {
				
					return response=this.singleChildAppointment(appointment, mails, ok, response);
				
				}
			}
		}
		// appointment without recurrence
		else {
			//update appointment without recurrence and send mail
			if(!AppUtil.isNullOrEmpty(appointment.getAttendeesEmail()))
			{
				SimpleDateFormat toformat=new SimpleDateFormat("MM/dd/yyyy");
				SimpleDateFormat orgformat=new SimpleDateFormat("MM/dd/yyyy hh:mm a");
			mails=general.getMailDTO(appointment.getUserId().toString(), String.valueOf(appointment.getId()), appointment.getModuleName());
			general.updateCalendarTask(appointment.getUserId().toString(), appointment);
			try {
				appointment.getRecurr().setRecurrenceMeetingDates(
						toformat.format(orgformat.parse(appointment.getTaskDate() ))+","+
						toformat.format(orgformat.parse(appointment.getTaskEnd() ))
						);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ok=sender.mailUpdateAllfunction(appointment, String.valueOf(appointment.getId()), appointment.getUserId().toString(),mails);
			response.setData(ok);
			}
			// update appointment only
			else {
				response.setData(general.updateCalendarTask(appointment.getUserId().toString(), appointment));
				response.setErrorMessage("Record updated");
			}
			
			}
		return response;
	}
	
	/**
	 * This method is used to update all appointment in recurrence
	 * 
	 * Note: this will over-ride all the dates changes for meeting, made by single meeting  
	 * 
	 * @return
	 */
	private boolean updateAll(CalendarAppointmentDTO appointment,List<CalendarAppointmentDTO> childrens,List<AppointmentMailDTO> mails,boolean ok)
	{
		CalendarAppointmentDTO orgAppointment=general.getOriginalAppointmentsById(appointment.getUserId().toString(), String.valueOf(appointment.getId()),appointment.getModuleName());
		// recurrence Id check all the mails in OUTLOOK are identified by recurrenceId original dates created at add Appointment
		if(AppUtil.isNullOrEmpty(appointment.getRecurrenceId()))
		{
			appointment.setRecurrenceId(orgAppointment.getRecurrenceId());
		}
		
		/*======== use case and process ===========*/
		//update parent with children and send mail
		if(!AppUtil.isNullOrEmpty(appointment.getAttendeesEmail()) && appointment.getParentId().equals("0") )
		{
			try {
				this.processDataForAllMeetingUpdateByParent(appointment, childrens);
				mails=general.getMailDTO(appointment.getUserId().toString(), String.valueOf(appointment.getId()), appointment.getModuleName());			
				ok=sender.mailUpdateAllfunction(appointment, String.valueOf(appointment.getId()), appointment.getUserId().toString(),mails);	
			}catch(Exception e) {
				e.printStackTrace();
				ok=false;
			}
			
		} 
		// update by children and send mail
		else if( !AppUtil.isNullOrEmpty(appointment.getAttendeesEmail()) && Integer.parseInt(appointment.getParentId())>0 ) {

			try {
			this.processDataForAllMeetingUpdateByChild(appointment, childrens);
			mails=general.getMailDTO(appointment.getUserId().toString(), appointment.getParentId(), appointment.getModuleName());
			ok=sender.mailUpdateAllfunction(appointment, appointment.getParentId(), appointment.getUserId().toString(),mails);
			}catch(Exception e) {
				e.printStackTrace();
				ok=false;
			}
			
		} 
		//update parent and children, no mail
		else if(appointment.getParentId().equals("0")) {
			try{
			this.processDataForAllMeetingUpdateByParent(appointment, childrens);
			ok=true;
			}catch(Exception e) {e.printStackTrace(); ok=false;}
		} 
		//update children, no mail
		else {
			try {
			this.processDataForAllMeetingUpdateByChild(appointment, childrens);
			ok=true;
			}catch(Exception e) {e.printStackTrace(); ok=false;}
		}
		return ok;
	}
	
	/**
	 * Method to update single appointment in recurrence and provided appointment is parent of appointments in recurrence
	 * 
	 * Appointment data 
	 * Mails List
	 * JsonObject to set response
	 * 
	 * calculates all changed appointments in recurrence and add there Event in Mail ICS File
	 * @return JsonResponse
	 */
	private JsonResponse singleParentMeeting(CalendarAppointmentDTO appointment,List<AppointmentMailDTO> mails,boolean ok,JsonResponse res) {
		SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sdfComplete=new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		SimpleDateFormat sdfDB=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<CalendarAppointmentDTO> childrens;
		CalendarAppointmentDTO orgAppintment=general.getOriginalAppointmentsById(appointment.getUserId().toString(), String.valueOf(appointment.getId()),appointment.getModuleName());
		orgAppintment.setRecurr(appointment.getRecurr());
	
		List<CalendarAppointmentDTO> appointmentsWithUpdate=new ArrayList<CalendarAppointmentDTO>();
		
		/*checking is recurrenceId is not null or empty */ 
		if(AppUtil.isNullOrEmpty(orgAppintment.getRecurrenceId())) {
			orgAppintment.setRecurrenceId(orgAppintment.getTaskDate());
			// to save original date as recurrence id
			appointment.setRecurrenceId(orgAppintment.getTaskDate());
  	  }else if(AppUtil.isNullOrEmpty(appointment.getRecurrenceId()))
  	  {
			// to save original date as recurrence id
			try {
				appointment.setRecurrenceId(sdfComplete.format(sdfDB.parse(orgAppintment.getRecurrenceId())));
				orgAppintment.setRecurrenceId(sdfComplete.format(sdfDB.parse(orgAppintment.getRecurrenceId())));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				appointment.setRecurrenceId(orgAppintment.getRecurrenceId());

			}
  	  }
			/**/
		
			childrens=general.getChildAppointmentsById(appointment.getUserId().toString(), String.valueOf(appointment.getId()),appointment.getModuleName());
			String nDate="";
			String oDate="";
			appointmentsWithUpdate.add(appointment);
			// checking for date time conflicts
			for(CalendarAppointmentDTO app:childrens)
			{
				try {
					// updated date for current appointment
					nDate=sdf.format(sdfComplete.parse(appointment.getTaskEnd()));
					//existing sibling appointment date
					oDate=sdf.format(sdfComplete.parse(app.getOrgTaskDate()));
					
					//new date should not contradict with any other date in recurrence
					if(sdf.parse(nDate).compareTo(sdf.parse(oDate))==0)
					{
						res.setErrorCode(1);
						res.setErrorMessage("Two occurrence of \"Meeting\" cannot occur on the same day");
						return res;
					}
					// update meeting date should not after next appointment in recurrence
					else if(sdf.parse(nDate).after(sdf.parse(oDate))) {
						res.setErrorCode(1);						
							res.setErrorMessage("Cannot reschedule an occurrence of the recurrence appointment, if it skips over a later occurrence of the same appointment.");						
							return res;
					}
					//setting appointments with changes
					if(!app.getTaskDate().equals(app.getRecurrenceId()))
					{
						appointmentsWithUpdate.add(app);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					res.setErrorCode(1);
					res.setErrorMessage("invalid date format");
					return res;
				}
			}
			/**/
			
		String meetingdates="";

		try {
			meetingdates=new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("MM/dd/yyyy hh:mm a").parse(appointment.getTaskDate()));
			appointment.getRecurr().setRecurrenceMeetingDates(meetingdates+","+appointment.getRecurr().getRecurrenceEndDate()+"");
//			appointment.getRecurr().setRecurrenceEndDate(originalAppointment.getRecurrenceString().get("recurrence_enddate")+"");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res.setErrorCode(1);
			res.setErrorMessage("invalid date format");
		}
		if(!AppUtil.isNullOrEmpty(appointment.getAttendeesEmail()) )
		{			
			general.updateCalendarTask(appointment.getUserId().toString(), appointment);
			mails=general.getMailDTO(appointment.getUserId().toString(), String.valueOf(appointment.getId()), appointment.getModuleName());
			res=this.sendSingleMeetingMail(appointmentsWithUpdate, mails, ok, res, orgAppintment);
		}
		else
		{	general.updateCalendarTask(appointment.getUserId().toString(), appointment);
			ok=true;
			res.setData(ok);
		}
		return res;
	}
	
	/**
	 * Method to update single appointment in recurrence and provided appointment is child of of recurrence
	 * 
	 * Appointment data 
	 * Mails List
	 * JsonObject to set response
	 * 
	 * calculates all changed appointments in recurrence and add there Event in Mail ICS File
	 * @return JsonResponse
	 */
	
	private JsonResponse singleChildAppointment(CalendarAppointmentDTO appointment,List<AppointmentMailDTO> mails,boolean ok,JsonResponse res) {
		SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sdfComplete=new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		SimpleDateFormat sdfDB=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<CalendarAppointmentDTO> childrens;
		CalendarAppointmentDTO orgAppintment=general.getOriginalAppointmentsById(appointment.getUserId().toString(), String.valueOf(appointment.getId()),appointment.getModuleName());
		orgAppintment.setRecurr(appointment.getRecurr());
		
		List<CalendarAppointmentDTO> appointmentsWithUpdate=new ArrayList<CalendarAppointmentDTO>();
		
		
		if(AppUtil.isNullOrEmpty(orgAppintment.getRecurrenceId())) {
			orgAppintment.setRecurrenceId(orgAppintment.getTaskDate());
			// to save original date as recurrence id
			appointment.setRecurrenceId(orgAppintment.getTaskDate());
  	  }else if(AppUtil.isNullOrEmpty(appointment.getRecurrenceId()))
  	  {
			// to save original date as recurrence id
			try {
				appointment.setRecurrenceId(sdfComplete.format(sdfDB.parse(orgAppintment.getRecurrenceId())));
				orgAppintment.setRecurrenceId(sdfComplete.format(sdfDB.parse(orgAppintment.getRecurrenceId())));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				appointment.setRecurrenceId(orgAppintment.getRecurrenceId());

			}
  	  }
		
		/**/
//			childrens=general.getChildAppointmentsByParentId(appointment.getUserId().toString(), appointment.getParentId(),orgAppintment.getTaskDate(),appointment.getModuleName());
			childrens=general.getAllAppointmentsByParentId(appointment.getUserId().toString(), appointment.getParentId(),appointment.getId(),appointment.getModuleName());
			appointmentsWithUpdate.add(appointment);
			
			String nDate="";
			String oDate="";
			// updating children
			for(CalendarAppointmentDTO app:childrens)
			{
				try {
					// updated date for current appointment
					nDate=sdf.format(sdfComplete.parse(appointment.getTaskEnd()));
					//existing sibling appointment date
					oDate=sdf.format(sdfComplete.parse(app.getOrgTaskDate()));
					//new date should not contradict with any other date in recurrence
					if(sdf.parse(nDate).compareTo(sdf.parse(oDate))==0)
					{
						res.setErrorCode(1);
						res.setErrorMessage("Two occurrence of \"Meeting\" cannot occur on the same day or time");
						return res;
					}
					// update meeting date should not after next appointment in recurrence
					else if(sdf.parse(nDate).after(sdf.parse(oDate)) && appointment.getId()<app.getId()) {
						res.setErrorCode(1);						
							res.setErrorMessage("Cannot reschedule an occurrence of the recurrence appointment, if it skips over a later occurrence of the same appointment.");						
							return res;
					}
					if(!app.getTaskDate().equals(app.getRecurrenceId()))
					{
						appointmentsWithUpdate.add(app);
					}	
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					res.setErrorCode(1);
					res.setErrorMessage("invalid date format");
					return res;
				}
			}
			/**/
			
			mails=general.getMailDTO(appointment.getUserId().toString(), String.valueOf(appointment.getParentId()), appointment.getModuleName());
			String meetingdates="";

			try {
				meetingdates=new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("MM/dd/yyyy hh:mm a").parse(appointment.getTaskDate()));
				appointment.getRecurr().setRecurrenceMeetingDates(meetingdates+","+appointment.getRecurr().getRecurrenceEndDate()+"");
//				appointment.getRecurr().setRecurrenceEndDate(originalAppointment.getRecurrenceString().get("recurrence_enddate")+"");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				res.setErrorCode(1);
				res.setErrorMessage("invalid date format");
			}
			if(!AppUtil.isNullOrEmpty(appointment.getAttendeesEmail()) )
			{			
				general.updateCalendarTask(appointment.getUserId().toString(), appointment);
				res=this.sendSingleMeetingMail(appointmentsWithUpdate, mails, ok, res, orgAppintment);
			}
			else
			{	general.updateCalendarTask(appointment.getUserId().toString(), appointment);
				ok=true;
				res.setData(ok);
			}
			
		return res;
	}
	/*
	 * function to send mail for single meeting changes
	 * Appointment data 
	 * Mails List
	 * JsonObject to set response
	 * 
	 * @return JsonRepsonse
	 */
	private JsonResponse sendSingleMeetingMail(List<CalendarAppointmentDTO> appointment,List<AppointmentMailDTO> mails,boolean ok,JsonResponse res,CalendarAppointmentDTO orgAppointment) {
		
		System.out.println("appointments size:"+appointment.size());
			ok=sender.mailUpdatefunction(appointment, String.valueOf(appointment.get(0).getId()),appointment.get(0).getUserId().toString(), mails,orgAppointment);
			res.setData(ok);

		return res;
	}
	
	
	private void processDataForAllMeetingUpdateByParent(CalendarAppointmentDTO appointment,List<CalendarAppointmentDTO> childrens) {
		
		//fetching children by parentId
		childrens=general.getChildAppointmentsById(appointment.getUserId().toString(), String.valueOf(appointment.getId()),appointment.getModuleName());
		//fetching mails record for uuid, Every OUTLOOK mail is identify by UID and meeting is identify by RecurrenceId
		SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sdfComplete=new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		SimpleDateFormat time=new SimpleDateFormat("hh:mm a");
		SimpleDateFormat sdfCompleteDB=new SimpleDateFormat("yyyy-MM-dd HH:mm");

		// preparing meeting dates for recurrence rule by meetings original dates
		String meetingDates="";
		try {
			meetingDates=meetingDates+ sdf.format(sdfCompleteDB.parse(appointment.getRecurrenceId()))+",";
		} catch (ParseException e1) { e1.printStackTrace();}
		// updating children
		for(CalendarAppointmentDTO app:childrens)
		{
			try {
								
				app.setSubject(appointment.getSubject());
				app.setAddress(appointment.getAddress());
				app.setTitle(appointment.getTitle());
				app.setTaskNote(appointment.getTaskNote());
				app.setProfileZone(appointment.getProfileZone());
				app.setTimeZone(appointment.getTimeZone());
				app.setAttendeesEmail(appointment.getAttendeesEmail());
				app.setModuleName(appointment.getModuleName());
				app.setGps(appointment.getGps());
				app.setStatus(appointment.getStatus());
				app.setUserId(appointment.getUserId());
				app.setUserName(appointment.getUserName());
				
				if(AppUtil.isNullOrEmpty(app.getRecurrenceId())) {
	        		  app.setRecurrenceId(app.getTaskDate());
	        	  }
				meetingDates=meetingDates+ sdf.format(sdfCompleteDB.parse(app.getRecurrenceId()))+",";
				
				//update start time to original date
				app.setTaskDate( sdf.format(sdfCompleteDB.parse(app.getRecurrenceId()))+" "+time.format(sdfComplete.parse(appointment.getTaskDate())) );
				//update end time to original date
				app.setTaskEnd(sdf.format(sdfCompleteDB.parse(app.getRecurrenceId()))+" "+time.format( sdfComplete.parse(appointment.getTaskEnd())) );


			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
//				return ok=false;
			}
			general.updateCalendarTask(appointment.getUserId().toString(), app);	
			
		}
		/* setting meetings to Default dates*/
		try {
			//update start time to original date
			appointment.setTaskDate( sdf.format(sdfCompleteDB.parse(appointment.getRecurrenceId()))+" "+time.format(sdfComplete.parse(appointment.getTaskDate())) );
			//update end time to original date
			appointment.setTaskEnd(sdf.format(sdfCompleteDB.parse(appointment.getRecurrenceId()))+" "+time.format( sdfComplete.parse(appointment.getTaskEnd())) );
		}catch(ParseException e) {
			e.printStackTrace();
		}
		// updating current appointment
		general.updateCalendarTask(appointment.getUserId().toString(), appointment);
		// updating mail
		appointment.getRecurr().setRecurrenceMeetingDates(meetingDates.length()>1 ? meetingDates.substring(0,meetingDates.length()-1 ):"");
		System.out.println("meetingDates:"+meetingDates);
	}
	
	private void processDataForAllMeetingUpdateByChild(CalendarAppointmentDTO appointment,List<CalendarAppointmentDTO> childrens) {
		childrens=general.getChildAppointmentsByParentId(appointment.getUserId().toString(), appointment.getParentId(),appointment.getTaskDate(),appointment.getModuleName());

		SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sdfComplete=new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		SimpleDateFormat time=new SimpleDateFormat("hh:mm a");
		SimpleDateFormat sdfCompleteDB=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String meetingDates="";
		try {
			meetingDates=meetingDates+ sdf.format(sdfCompleteDB.parse(appointment.getRecurrenceId()))+",";
		} catch (ParseException e1) { 
			e1.printStackTrace();
			}
		// updating children
		for(CalendarAppointmentDTO app:childrens)
		{
			try {
//				System.out.println("dbTime:"+app.getOrgTaskDate());
				//update start time to original date
//				app.setTaskDate( sdf.format(sdfComplete.parse(app.getOrgTaskDate()))+" "+time.format(sdfComplete.parse(appointment.getTaskDate())) );
//				//update end time to original date
//				app.setTaskEnd(sdf.format(sdfComplete.parse(app.getOrgTaskEnd()))+" "+time.format( sdfComplete.parse(appointment.getTaskEnd())) );
				
				
				app.setSubject(appointment.getSubject());
				app.setAddress(appointment.getAddress());
				app.setTitle(appointment.getTitle());
				app.setTaskNote(appointment.getTaskNote());
				app.setProfileZone(appointment.getProfileZone());
				app.setTimeZone(appointment.getTimeZone());
				app.setAttendeesEmail(appointment.getAttendeesEmail());
				app.setModuleName(appointment.getModuleName());
				app.setGps(appointment.getGps());
				app.setStatus(appointment.getStatus());
				app.setUserId(appointment.getUserId());
				app.setUserName(appointment.getUserName());
				
				if(AppUtil.isNullOrEmpty(app.getRecurrenceId())) {
	        		  app.setRecurrenceId(app.getTaskDate());
	        	  }
				meetingDates=meetingDates+ sdf.format(sdfCompleteDB.parse(app.getRecurrenceId()))+",";
				//update start time to original date
				app.setTaskDate( sdf.format(sdfCompleteDB.parse(app.getRecurrenceId()))+" "+time.format(sdfComplete.parse(appointment.getTaskDate())) );
				//update end time to original date
				app.setTaskEnd(sdf.format(sdfCompleteDB.parse(app.getRecurrenceId()))+" "+time.format( sdfComplete.parse(appointment.getTaskEnd())) );
				
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			general.updateCalendarTask(appointment.getUserId().toString(), app);	
			
		}
		/* setting meetings to Default dates*/
		try {
			//update start time to original date
			appointment.setTaskDate( sdf.format(sdfCompleteDB.parse(appointment.getRecurrenceId()))+" "+time.format(sdfComplete.parse(appointment.getTaskDate())) );
			//update end time to original date
			appointment.setTaskEnd(sdf.format(sdfCompleteDB.parse(appointment.getRecurrenceId()))+" "+time.format( sdfComplete.parse(appointment.getTaskEnd())) );
		}catch(ParseException e) {
			e.printStackTrace();
		}

		// updating current appointment
		general.updateCalendarTask(appointment.getUserId().toString(), appointment);

		// updating mail
		appointment.getRecurr().setRecurrenceMeetingDates(meetingDates.length()>1 ? meetingDates.substring(0,meetingDates.length()-1 ):"");
		System.out.println("meetingDates:"+meetingDates);

	}
	public List<ContactDTO> fetchContactList (ContactDTO contactDTO) {
		List<ContactDTO> list = null;
		try {
			list = general.fetchConctactList(contactDTO.getUserId().toString());
			//System.out.println(list.size());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception# fetchContactList# ",e);
		}
		return list;
		
	}
	public ContactDTO fetchContactDetails(ContactDTO contactDTO) {
		 ContactDTO dto = new ContactDTO();
		 try {
			 dto = general.fetchContactDetailById(contactDTO.getUserId().toString(), contactDTO.getId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception# fetchContactDetails# ",e);
		}
		return dto;
	}
	public JsonResponse archivedContact(ContactDTO dto) {
		JsonResponse jsonResponse = new JsonResponse();
		try {

				boolean isUpdateContact =  general.deleteOrArchivedContact(dto);
				if(isUpdateContact == true) {
					boolean isContactAsIds =  general.deleteOrArchivedContactAsIds(dto);
					if(isContactAsIds) {
						jsonResponse.setErrorMessage("Record has been updated successfully.");
						jsonResponse.setErrorCode(1);
					}
				} else {
					jsonResponse.setErrorMessage("Record has been updated successfully.");
					jsonResponse.setErrorCode(1);
				}	  
		} catch (Exception e) {
			e.printStackTrace();
			jsonResponse.setErrorMessage("Exception occured.");
			jsonResponse.setErrorCode(1);
			return jsonResponse;
		}
		return jsonResponse;
	}
	
	/* First we add in contact_ids table and get new added record Id
	 * @ return id 
	 * Here we are saving data in contact table
	 * return object
	 */
	public JsonResponse addContact(ContactDTO contactDTO, JsonResponse response)  {

		try {

			Long contactId = general.addContactIds(contactDTO);
			if(contactId !=null) { 
				Long returnId  = general.addContact(contactDTO, contactId);
				if(returnId!=null) {
					response.setData(contactId);
					response.setErrorCode(0);
					response.setErrorMessage("Recard saved sucessfully.");
				}else {
					response.setErrorCode(1);
					response.setErrorMessage("Contact saving recard have a problem.");
				}
			}else {
				response.setErrorCode(1);
				response.setErrorMessage("ContactIds saving record have a problem.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setErrorMessage("Expection accured.");
			return response;
		}
		return response;
		
	}
}
