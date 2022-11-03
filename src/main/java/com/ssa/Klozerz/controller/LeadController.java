package com.ssa.Klozerz.controller;

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
import com.ssa.Klozerz.dto.AppUserDTO;
import com.ssa.Klozerz.dto.LeadDTO;
import com.ssa.Klozerz.service.LeadService;
import com.ssa.Klozerz.util.AppUtil;


@RestController
public class LeadController {
	private static final Logger logger = LogManager.getLogger(LeadController.class);
	@Autowired
	LeadService leadService;
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(value = "/GetLeadList", produces = { Constants.APPLICATION_JSON_UTF8 }, method = RequestMethod.POST)
	public ResponseEntity<Object> getLeadList(@RequestBody AppUserDTO appUser) {
		JsonResponse jsonResponse=new JsonResponse();
		if(!StringUtils.hasText(appUser.getUserId().toString())) {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage(messageSource.getMessage("userid.notempty", null, null));
			return new ResponseEntity<Object>(jsonResponse,HttpStatus.BAD_REQUEST);
		}
		if(!StringUtils.hasText(appUser.getSubUserIds()))
		{
			appUser.setSubUserIds(appUser.getUserId().toString());
		}
		try {
			jsonResponse=leadService.getLeadList(appUser);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(jsonResponse,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/AddLead", produces = { Constants.APPLICATION_JSON_UTF8 }, method = RequestMethod.POST)
	public ResponseEntity<Object> addLead(@RequestBody LeadDTO lead) {
		JsonResponse jsonResponse=new JsonResponse();
		
		if(!StringUtils.hasText(lead.getUser_id())) {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage(messageSource.getMessage("userid.notempty", null, null));
			return new ResponseEntity<Object>(jsonResponse,HttpStatus.BAD_REQUEST);
		}
		if(!StringUtils.hasText(lead.getName()))
		{
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage(messageSource.getMessage("name.notempty", null, null));
			return new ResponseEntity<Object>(jsonResponse,HttpStatus.BAD_REQUEST);
		}
		if(!StringUtils.hasText(lead.getPhone()))
		{
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage(messageSource.getMessage("phone.notempty", null, null));
			return new ResponseEntity<Object>(jsonResponse,HttpStatus.BAD_REQUEST);
		}
		if(!StringUtils.hasText(lead.getZipCode()))
		{
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage(messageSource.getMessage("zipCode.notempty", null, null));
			return new ResponseEntity<Object>(jsonResponse,HttpStatus.BAD_REQUEST);
		}
		jsonResponse=leadService.addLead(lead);
		
//		jsonResponse.setData(lead);
		return new ResponseEntity<Object>(jsonResponse,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/GetAccountList", produces = { Constants.APPLICATION_JSON_UTF8 }, method = RequestMethod.POST)
	public ResponseEntity<Object> getAccountList(@RequestBody AppUserDTO appUser) {
		JsonResponse jsonResponse=new JsonResponse();

		 if(!StringUtils.hasText(appUser.getUserId().toString())){
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage(messageSource.getMessage("userid.notempty", null, null));
			return new ResponseEntity<Object>(jsonResponse,HttpStatus.BAD_REQUEST);
		}
		if(!StringUtils.hasText(appUser.getSubUserIds()))
		{
			appUser.setSubUserIds(appUser.getUserId().toString());
		}
		try {
			jsonResponse=leadService.getAccountList(appUser);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(jsonResponse,HttpStatus.OK);
	}
	@RequestMapping(value = "/EditLead", produces = { Constants.APPLICATION_JSON_UTF8 }, method = RequestMethod.POST)
	public ResponseEntity<Object> editLead(@RequestBody LeadDTO lead) {
		JsonResponse jsonResponse=new JsonResponse();
		 if(!StringUtils.hasText(lead.getUser_id())){
				jsonResponse.setErrorCode(1);
				jsonResponse.setErrorMessage(messageSource.getMessage("userid.notempty", null, null));
				return new ResponseEntity<Object>(jsonResponse,HttpStatus.BAD_REQUEST);
			}
		if(!StringUtils.hasText(lead.getId()))
		{
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage(messageSource.getMessage("leadid.notempty", null, null));
			return new ResponseEntity<Object>(jsonResponse,HttpStatus.BAD_REQUEST);
		}
		if(!StringUtils.hasText(lead.getName()))
		{
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage(messageSource.getMessage("name.notempty", null, null));
			return new ResponseEntity<Object>(jsonResponse,HttpStatus.BAD_REQUEST);
		}
		if(!StringUtils.hasText(lead.getPhone()))
		{
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage(messageSource.getMessage("phone.notempty", null, null));
			return new ResponseEntity<Object>(jsonResponse,HttpStatus.BAD_REQUEST);
		}
		if(!StringUtils.hasText(lead.getZipCode()))
		{
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage(messageSource.getMessage("zipCode.notempty", null, null));
			return new ResponseEntity<Object>(jsonResponse,HttpStatus.BAD_REQUEST);
		}
		boolean isUpdated = leadService.editLead(lead);
		if(isUpdated == true) {
			jsonResponse.setErrorCode(0);
			jsonResponse.setErrorMessage(messageSource.getMessage("lead.updated", null, null));
		}else {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage(messageSource.getMessage("error.updated.lead", null, null));
			return new ResponseEntity<Object>(jsonResponse,HttpStatus.EXPECTATION_FAILED); 
		}
		
//		jsonResponse.setData(lead);
		return new ResponseEntity<Object>(jsonResponse,HttpStatus.OK);
	}
	@RequestMapping(value = "/EditAccount", produces = { Constants.APPLICATION_JSON_UTF8 }, method = RequestMethod.POST)
	public ResponseEntity<Object> editAccount(@RequestBody LeadDTO leadDto) {
		JsonResponse jsonResponse=new JsonResponse();
			if (!StringUtils.hasText(String.valueOf(leadDto.getUser_id())))
		     {
			   jsonResponse.setErrorCode(1);
			   jsonResponse.setErrorMessage(messageSource.getMessage("userid.notempty", null, null));
			   return new ResponseEntity<Object>(jsonResponse,HttpStatus.BAD_REQUEST);
		     }
			if (!StringUtils.hasText(String.valueOf(leadDto.getId())))
		     {
			   jsonResponse.setErrorCode(1);
			   jsonResponse.setErrorMessage(messageSource.getMessage("accountId.notempty", null, null));
			   return new ResponseEntity<Object>(jsonResponse,HttpStatus.BAD_REQUEST);
		     }

		try {
			boolean isUpdated = leadService.editAccount(leadDto);
			if(isUpdated == true) {
				jsonResponse.setErrorCode(0);
				jsonResponse.setErrorMessage(messageSource.getMessage("account.updated", null, null));
			}else {
				jsonResponse.setErrorCode(1);
				jsonResponse.setErrorMessage(messageSource.getMessage("error.updated.account", null, null));
				return new ResponseEntity<Object>(jsonResponse,HttpStatus.EXPECTATION_FAILED); 
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(jsonResponse,HttpStatus.OK);
	}
}
