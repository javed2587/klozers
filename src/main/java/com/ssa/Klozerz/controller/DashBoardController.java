package com.ssa.Klozerz.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssa.Klozerz.common.Constants;
import com.ssa.Klozerz.common.JsonResponse;
import com.ssa.Klozerz.dto.CalendarAppointmentDTO;
import com.ssa.Klozerz.dto.DashBoardDTO;
import com.ssa.Klozerz.service.DashBoardService;

@RestController
public class DashBoardController {
	private static final Logger logger = LogManager.getLogger(AppUserController.class);

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private DashBoardService dashBoardService;

	@RequestMapping(value = "/getUpcomingAppointments", produces = {
			Constants.APPLICATION_JSON_UTF8 }, method = RequestMethod.POST)
	public ResponseEntity<Object> getAppointments(@RequestBody CalendarAppointmentDTO calendarAppointmentDTO) {
		JsonResponse jsonResponse = new JsonResponse();
		try {
			if(!StringUtils.hasText(calendarAppointmentDTO.getUserId().toString())) {
				   jsonResponse.setErrorCode(1);
				   jsonResponse.setErrorMessage(messageSource.getMessage("userid.notempty", null, null));
				   return new ResponseEntity<Object>(jsonResponse,HttpStatus.BAD_REQUEST);
			}
			DashBoardDTO lst = dashBoardService.fetchUpComingCalenderAppointments(calendarAppointmentDTO);
			
			  if (lst != null) { 
				  jsonResponse.setErrorCode(0);
				  jsonResponse.setData(lst); 
				  jsonResponse.setTotalRecords(lst.getAccountAppointments().size() + lst.getLeadAppointments().size() + lst.getPersonlaAppointmens().size());
				  }
			  else {
				  jsonResponse.setErrorCode(1); jsonResponse.setErrorMessage("Not fetched.");
			      return new ResponseEntity<Object>(jsonResponse,HttpStatus.EXPECTATION_FAILED); 
			      }
			 
		} catch (Exception e) {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage(messageSource.getMessage("server.error", null, null));
			logger.error("Exception# login# ", e);
			return new ResponseEntity<Object>(jsonResponse, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(jsonResponse, HttpStatus.OK);
	}
}
