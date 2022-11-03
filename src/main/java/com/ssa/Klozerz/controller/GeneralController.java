package com.ssa.Klozerz.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssa.Klozerz.common.Constants;
import com.ssa.Klozerz.common.JsonResponse;
import com.ssa.Klozerz.dto.CalendarAppointmentDTO;
import com.ssa.Klozerz.dto.ContactDTO;
import com.ssa.Klozerz.dto.ProgressNoteDTO;
import com.ssa.Klozerz.service.AppUserService;
import com.ssa.Klozerz.service.GeneralService;
import com.ssa.Klozerz.util.AppUtil;
import com.ssa.Klozerz.util.DateUtil;

@RestController
public class GeneralController {

	@Autowired
	GeneralService gService;

	@Autowired
	private MessageSource messageSource;
	private static final Logger logger = LogManager.getLogger(AppUserService.class);

	@RequestMapping(value = "/GetNotesList", produces = {
			Constants.APPLICATION_JSON_UTF8 }, method = RequestMethod.POST)
	public ResponseEntity<Object> getLeadList(@RequestBody ProgressNoteDTO note) {
		JsonResponse jsonResponse = new JsonResponse();
		if (!StringUtils.hasText(note.getUserId())) {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage(messageSource.getMessage("userid.notempty", null, null));
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
		if (!StringUtils.hasText(note.getModuleName())) {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage(messageSource.getMessage("modulename.notempty", null, null));
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
		if (!StringUtils.hasText(note.getModuleId())) {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage(messageSource.getMessage("moduleid.notempty", null, null));
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
//		if(note.getModuleName().toLowerCase().equals("lead") || !note.getModuleName().toLowerCase().equals("account"))
//		{
//			jsonResponse.setErrorCode(1);
//			jsonResponse.setErrorMessage("Invaild module name");
//			return new ResponseEntity<Object>(jsonResponse,HttpStatus.BAD_REQUEST);
//		}
		jsonResponse = gService.getProgressNotesByModuleName(note);
		return new ResponseEntity<Object>(jsonResponse, HttpStatus.OK);
	}

	@RequestMapping(value = "/AddAppointment", produces = {
			Constants.APPLICATION_JSON_UTF8 }, method = RequestMethod.POST)
	public ResponseEntity<Object> addAppointment(@RequestBody CalendarAppointmentDTO appointment) {
		JsonResponse jsonResponse = new JsonResponse();
		if (!StringUtils.hasText(String.valueOf(appointment.getUserId()))) {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage(messageSource.getMessage("userid.notempty", null, null));
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
		if (!StringUtils.hasText(appointment.getModuleName())) {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage(messageSource.getMessage("modulename.notempty", null, null));
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
		if (!StringUtils.hasText(String.valueOf(appointment.getModuleId()))) {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage(messageSource.getMessage("moduleid.notempty", null, null));
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
		if (!StringUtils.hasText(appointment.getSubject())) {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage("Subject is required");
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
		if (!StringUtils.hasText(appointment.getTaskDate())) {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage("Task date is required");
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
		if (!StringUtils.hasText(appointment.getTaskEnd())) {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage("Task end is required");
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
		if (!StringUtils.hasText(appointment.getTimeZone())) {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage("Time zone is required");
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
		if (!StringUtils.hasText(appointment.getProfileZone())) {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage("Profile zone is required");
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
		if (!appointment.getModuleName().toLowerCase().equals("lead")
				&& !appointment.getModuleName().toLowerCase().equals("account")
				&& !appointment.getModuleName().toLowerCase().equals("personal")) {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage("Invaild module name");
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
//		jsonResponse.setData(appointment);
		jsonResponse = gService.addAppointment(appointment);
		return new ResponseEntity<Object>(jsonResponse, HttpStatus.OK);
	}

	@RequestMapping(value = "/fetchAppointmentsByModule", produces = {
			Constants.APPLICATION_JSON_UTF8 }, method = RequestMethod.POST)
	public ResponseEntity<Object> getApponitsmentsList(@RequestBody CalendarAppointmentDTO calendarAppointmentDTO) {
		JsonResponse jsonResponse = new JsonResponse();
		try {
				if (!StringUtils.hasText(String.valueOf(calendarAppointmentDTO.getUserId()))) {
				jsonResponse.setErrorCode(1);
				jsonResponse.setErrorMessage(messageSource.getMessage("userid.notempty", null, null));
				return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
			}
			if (!StringUtils.hasText(calendarAppointmentDTO.getModuleName())) {
				jsonResponse.setErrorCode(1);
				jsonResponse.setErrorMessage(messageSource.getMessage("modulename.notempty", null, null));
				return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
			}
			if (calendarAppointmentDTO.getModuleId() == null) {
				jsonResponse.setErrorCode(1);
				jsonResponse.setErrorMessage(messageSource.getMessage("moduleid.notempty", null, null));
				return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
			}
			if (!calendarAppointmentDTO.getModuleName().toLowerCase().equals("lead")
					&& !calendarAppointmentDTO.getModuleName().toLowerCase().equals("account")) {
				jsonResponse.setErrorCode(1);
				jsonResponse.setErrorMessage("Invaild module name");
				return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
//				return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
			}
			List<CalendarAppointmentDTO> lst = gService.fetchAppointments(calendarAppointmentDTO);
			if (lst != null) {
				jsonResponse.setErrorCode(0);
				jsonResponse.setData(lst);
				jsonResponse.setTotalRecords(lst.size());
			} else {
				jsonResponse.setErrorCode(1);
				jsonResponse.setErrorMessage("Not fetched.");
				return new ResponseEntity<Object>(jsonResponse, HttpStatus.EXPECTATION_FAILED);
			}

		} catch (Exception e) {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage(messageSource.getMessage("server.error", null, null));
			logger.error("Exception# fetchAppointmentsByModule# ", e);
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(jsonResponse, HttpStatus.OK);

	}

	@RequestMapping(value = "/addProgressNote", produces = {
			Constants.APPLICATION_JSON_UTF8 }, method = RequestMethod.POST)
	public ResponseEntity<Object> addNote(@RequestBody ProgressNoteDTO noteDTO) {
		JsonResponse jsonResponse = new JsonResponse();
		try {
			if (!StringUtils.hasText(noteDTO.getUserId())) {
				jsonResponse.setErrorCode(0);
				jsonResponse.setErrorMessage(messageSource.getMessage("userid.notempty", null, null));
				return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
			}
			  if (AppUtil.isNullOrEmpty(noteDTO.getNoteDate())) {
				  jsonResponse.setErrorCode(0);
				  jsonResponse.setErrorMessage(messageSource.getMessage("date.notemtpy", null, null));
				  return new  ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST); 
				  }
			  if (DateUtil.validateDateFormat(noteDTO.getNoteDate()) != true) {
				  jsonResponse.setErrorCode(0);
				  jsonResponse.setErrorMessage(messageSource.getMessage("date.format.not.matched", null, null));
				  return new  ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST); 
				  }
			 
			if (!StringUtils.hasText(noteDTO.getModuleName())) {
				jsonResponse.setErrorCode(0);
				jsonResponse.setErrorMessage(messageSource.getMessage("modulename.notempty", null, null));
				return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
			}
			if (!StringUtils.hasText(noteDTO.getModuleId())) {
				jsonResponse.setErrorCode(0);
				jsonResponse.setErrorMessage(messageSource.getMessage("moduleid.notempty", null, null));
				return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
			}
			ProgressNoteDTO dto = new ProgressNoteDTO();
			long saveNoteId = gService.addProgressNote(noteDTO);
			dto.setId(String.valueOf(saveNoteId));
			if (!AppUtil.isNullOrEmpty(saveNoteId)) {
				jsonResponse.setData(dto);
				jsonResponse.setErrorCode(0);
				jsonResponse.setErrorMessage(messageSource.getMessage("msg.progressNote.saved", null, null));
				//jsonResponse.setRecordSavedId(saveNoteId);
			} else {
				jsonResponse.setErrorCode(1);
				jsonResponse.setErrorMessage("Not fetched.");
				return new ResponseEntity<Object>(jsonResponse, HttpStatus.EXPECTATION_FAILED);
			}

		} catch (Exception e) {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage(messageSource.getMessage("server.error", null, null));
			logger.error("Exception# login# ", e);
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(jsonResponse, HttpStatus.OK);
	}

	@RequestMapping(value = "/archiveOrDelete", produces = {
			Constants.APPLICATION_JSON_UTF8 }, method = RequestMethod.POST)
	public ResponseEntity<Object> achivedRecord(@RequestBody ProgressNoteDTO noteDTO) {
		JsonResponse jsonResponse = new JsonResponse();
		try {
			if (!StringUtils.hasText(noteDTO.getModuleSuffix())) {
				jsonResponse.setErrorCode(0);
				jsonResponse.setErrorMessage("Module Suffix not be empty.");
				return new ResponseEntity<Object>(jsonResponse, HttpStatus.EXPECTATION_FAILED);
			}
			if ((!noteDTO.getModuleSuffix().equals("calendar")) && (!noteDTO.getModuleSuffix().equals("note"))) {
				jsonResponse.setErrorCode(0);
				jsonResponse.setErrorMessage("Module Suffix calendar/note must be matched/required.");
				return new ResponseEntity<Object>(jsonResponse, HttpStatus.EXPECTATION_FAILED);
			}
			if (!StringUtils.hasText(noteDTO.getUserId())) {
				jsonResponse.setErrorCode(0);
				jsonResponse.setErrorMessage(messageSource.getMessage("userid.notempty", null, null));
				return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
			}
			if (!StringUtils.hasText(noteDTO.getModuleName())) {
				jsonResponse.setErrorCode(0);
				jsonResponse.setErrorMessage(messageSource.getMessage("modulename.notempty", null, null));
				return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
			}
			if (!StringUtils.hasText(noteDTO.getModuleId())) {
				jsonResponse.setErrorCode(0);
				jsonResponse.setErrorMessage(messageSource.getMessage("moduleid.notempty", null, null));
				return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
			}
			if (!StringUtils.hasText(noteDTO.getArchive_status())) {
				jsonResponse.setErrorCode(0);
				jsonResponse.setErrorMessage(messageSource.getMessage("Archived_Status not be empty.", null, null));
				return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
			}
			if ((!noteDTO.getArchive_status().equals("trash")) && (!noteDTO.getArchive_status().equals("archived"))) {
				jsonResponse.setErrorCode(0);
				jsonResponse.setErrorMessage("Archived_Status must be trash/archived.");
				return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
			}
			boolean isDone = gService.deleteOrArchived(noteDTO);
			if (isDone == true) {
				jsonResponse.setErrorCode(0);
				jsonResponse.setErrorMessage("Record " + noteDTO.getArchive_status() + " successfully");
			} else {
				jsonResponse.setErrorCode(1);
				jsonResponse.setErrorMessage("Not fetched.");
				return new ResponseEntity<Object>(jsonResponse, HttpStatus.EXPECTATION_FAILED);
			}

		} catch (Exception e) {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage(messageSource.getMessage("server.error", null, null));
			logger.error("Exception# archiveOrDelete# ", e);
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(jsonResponse, HttpStatus.OK);
	}

	@RequestMapping(value = "/EditProgressNote", produces = {
			Constants.APPLICATION_JSON_UTF8 }, method = RequestMethod.POST)
	public ResponseEntity<Object> editNote(@RequestBody ProgressNoteDTO noteDTO) {
		JsonResponse jsonResponse = new JsonResponse();
		try {
			if (!StringUtils.hasText(noteDTO.getUserId())) {
				jsonResponse.setErrorCode(0);
				jsonResponse.setErrorMessage(messageSource.getMessage("userid.notempty", null, null));
				return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
			}
			if (!StringUtils.hasText(noteDTO.getModuleName())) {
				jsonResponse.setErrorCode(0);
				jsonResponse.setErrorMessage(messageSource.getMessage("modulename.notempty", null, null));
				return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
			}
			if (!StringUtils.hasText(noteDTO.getModuleId())) {
				jsonResponse.setErrorCode(0);
				jsonResponse.setErrorMessage(messageSource.getMessage("moduleid.notempty", null, null));
				return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
			}
			boolean saveNoteId = gService.editProgressNote(noteDTO);
			if (saveNoteId == true) {
				jsonResponse.setErrorCode(0);
				jsonResponse.setErrorMessage("Record updated successfully.");
			} else {
				jsonResponse.setErrorCode(1);
				jsonResponse.setErrorMessage("Not fetched.");
				return new ResponseEntity<Object>(jsonResponse, HttpStatus.EXPECTATION_FAILED);
			}

		} catch (Exception e) {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage(messageSource.getMessage("server.error", null, null));
			logger.error("Exception# login# ", e);
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(jsonResponse, HttpStatus.OK);
	}

	
	/**
	 * Method to update appointment with recurrence/without recurrence, single meeting in recurrence, complete series
	 * 
	 * if user tries to update complete series changed dates will be overridden to its original state 
	 * 
	 * moduleName (lead/account) required
	 * moduleId (leadId/accountId) required
	 * userId required
	 * 
	 * @return appointment list
	 */
	@RequestMapping(value = "/updateAppointment", produces = {
			Constants.APPLICATION_JSON_UTF8 }, method = RequestMethod.POST)
	public ResponseEntity<Object> updateAppointment(@RequestBody CalendarAppointmentDTO appointment) {
		JsonResponse jsonResponse = new JsonResponse();
		if (!StringUtils.hasText(appointment.getUserId().toString())) {
			jsonResponse.setErrorCode(0);
			jsonResponse.setErrorMessage(messageSource.getMessage("userid.notempty", null, null));
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
		if (!StringUtils.hasText(appointment.getModuleName())) {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage(messageSource.getMessage("modulename.notempty", null, null));
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
		if (!StringUtils.hasText(String.valueOf(appointment.getModuleId()))) {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage(messageSource.getMessage("moduleid.notempty", null, null));
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
		if (!StringUtils.hasText(appointment.getSubject())) {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage("Subject is required");
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
		if (!StringUtils.hasText(appointment.getTaskDate())) {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage("Task date is required");
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
		if (!StringUtils.hasText(appointment.getTaskEnd())) {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage("Task end is required");
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
		if (!StringUtils.hasText(appointment.getTimeZone())) {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage("Time zone is required");
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
		if (!StringUtils.hasText(appointment.getProfileZone())) {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage("Profile zone is required");
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
		if (!appointment.getModuleName().toLowerCase().equals("lead")
				&& !appointment.getModuleName().toLowerCase().equals("account")
				&& !appointment.getModuleName().toLowerCase().equals("personal")) {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage("Invaild module name");
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
//		jsonResponse.setData(appointment);
		jsonResponse = gService.updateAppointment(appointment);
		return new ResponseEntity<Object>(jsonResponse, HttpStatus.OK);
	}
	@RequestMapping(value = "/fetchContactList", produces = { Constants.APPLICATION_JSON_UTF8 }, method = RequestMethod.POST)
	public ResponseEntity<Object> getContactList(@RequestBody ContactDTO contactDto) {
		JsonResponse jsonResponse=new JsonResponse();
		
		if (!StringUtils.hasText(contactDto.getUserId().toString())) {
			jsonResponse.setErrorCode(0);
			jsonResponse.setErrorMessage(messageSource.getMessage("userid.notempty", null, null));
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
		
	   try {
		   List<ContactDTO> list = gService.fetchContactList(contactDto);
			if(list != null) {
				jsonResponse.setData(list);
				jsonResponse.setErrorCode(0);
				jsonResponse.setTotalRecords(list.size());
			} else {
				jsonResponse.setErrorCode(1);
				jsonResponse.setErrorMessage(messageSource.getMessage("list.empty", null, null));
				return new ResponseEntity<Object>(jsonResponse,HttpStatus.BAD_REQUEST);
			}
	} catch (Exception e) {
		e.printStackTrace();
	}
	   return new ResponseEntity<Object>(jsonResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/fetchContactDetailById", produces = { Constants.APPLICATION_JSON_UTF8 }, method = RequestMethod.POST)
	public ResponseEntity<Object> getContactDetails(@RequestBody ContactDTO contactDto) {
		JsonResponse jsonResponse=new JsonResponse();
		if (!StringUtils.hasText(contactDto.getUserId().toString())) {
			jsonResponse.setErrorCode(0);
			jsonResponse.setErrorMessage(messageSource.getMessage("userid.notempty", null, null));
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
		try {
			   ContactDTO dto = gService.fetchContactDetails(contactDto);
				if(dto != null) {
					jsonResponse.setData(dto);
					jsonResponse.setErrorCode(0);
				} else {
					jsonResponse.setErrorCode(1);
					jsonResponse.setErrorMessage(messageSource.getMessage("contact.notExsit", null, null));
					return new ResponseEntity<Object>(jsonResponse,HttpStatus.BAD_REQUEST);
				}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception# fetchContactDetailById# ",e);
			jsonResponse.setErrorCode(1);
			return new ResponseEntity<Object>(jsonResponse,HttpStatus.BAD_REQUEST);
		}
		 return new ResponseEntity<Object>(jsonResponse, HttpStatus.OK);
		
	}
	@RequestMapping(value = "/archiveOrDeleteContact", produces = {
			Constants.APPLICATION_JSON_UTF8 }, method = RequestMethod.POST)
	public ResponseEntity<Object> archiveOrDeleteContact(@RequestBody ContactDTO contactDTO) {
		JsonResponse jsonResponse = new JsonResponse();
		try {

			if (!StringUtils.hasText(contactDTO.getUserId().toString())) {
				jsonResponse.setErrorCode(0);
				jsonResponse.setErrorMessage(messageSource.getMessage("userid.notempty", null, null));
				return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
			}
			if (!StringUtils.hasText(contactDTO.getId().toString())) {
				jsonResponse.setErrorCode(0);
				jsonResponse.setErrorMessage(messageSource.getMessage("contact.id", null, null));
				return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
			}
			if (!StringUtils.hasText(contactDTO.getArchivedStatus())) {
				jsonResponse.setErrorCode(0);
				jsonResponse.setErrorMessage(messageSource.getMessage("Archived_Status not be empty.", null, null));
				return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
			}
			if ((!contactDTO.getArchivedStatus().equals("trash")) && (!contactDTO.getArchivedStatus().equals("archived"))) {
				jsonResponse.setErrorCode(0);
				jsonResponse.setErrorMessage("Archived_Status must be trash/archived.");
				return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
			}
			  jsonResponse= gService.archivedContact(contactDTO);
			
		} catch (Exception e) {
			jsonResponse.setErrorMessage(messageSource.getMessage("server.error", null, null));
			logger.error("Exception# archiveOrDelete# ", e);
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(jsonResponse, HttpStatus.OK);
	}
	@RequestMapping(value = "/AddContact", produces = { Constants.APPLICATION_JSON_UTF8 }, method = RequestMethod.POST)
	public ResponseEntity<Object> addContact(@RequestBody ContactDTO contactDTO) {
		JsonResponse jsonResponse = new JsonResponse();
		if (!StringUtils.hasText(String.valueOf(contactDTO.getUserId()))) {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage(messageSource.getMessage("userid.notempty", null, null));
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
		jsonResponse = gService.addContact(contactDTO, jsonResponse);
		return new ResponseEntity<Object>(jsonResponse, HttpStatus.OK);
	}
}
