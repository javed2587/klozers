package com.ssa.Klozerz.controller;

import javax.servlet.http.HttpServletRequest;

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
import com.ssa.Klozerz.service.AppUserService;

/**
 * 
 * @author Zubair
 *
 */
@RestController
public class AppUserController {
	private static final Logger logger = LogManager.getLogger(AppUserController.class);
	
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private AppUserService appUserService;

	/**
	 * This API is used to login in the APP
	 * 
	 * @param appUser
	 * @return
	 */
	@RequestMapping(value = "/login", produces = { Constants.APPLICATION_JSON_UTF8 }, method = RequestMethod.POST)
	public ResponseEntity<Object> login(@RequestBody AppUserDTO appUser,HttpServletRequest request) {
		JsonResponse jsonResponse=new JsonResponse();
		try {
			if(!StringUtils.hasText(appUser.getEmailAddress())) {
				jsonResponse.setErrorCode(1);
				jsonResponse.setErrorMessage(messageSource.getMessage("email.notempty", null, null));
				return new ResponseEntity<Object>(jsonResponse,HttpStatus.BAD_REQUEST);
			}
			if(!StringUtils.hasText(appUser.getPassword())) {
				jsonResponse.setErrorCode(1);
				jsonResponse.setErrorMessage(messageSource.getMessage("password.notempty", null, null));
				return new ResponseEntity<Object>(jsonResponse,HttpStatus.BAD_REQUEST);
			}
			appUser.setLoginIp(request.getRemoteAddr());
			jsonResponse=appUserService.validateAppUser(appUser);
		} catch (Exception e) {
			jsonResponse.setErrorCode(1);
			jsonResponse.setErrorMessage(messageSource.getMessage("server.error", null, null));
			logger.error("Exception# login# ",e);
			return new ResponseEntity<Object>(jsonResponse,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(jsonResponse,HttpStatus.OK);
	}
}
