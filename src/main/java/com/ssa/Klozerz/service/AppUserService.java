package com.ssa.Klozerz.service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ssa.Klozerz.common.JsonResponse;
import com.ssa.Klozerz.dao.AppUserDAO;
import com.ssa.Klozerz.dto.AppUserDTO;
import com.ssa.Klozerz.dto.RoleDTO;
import com.ssa.Klozerz.enums.RoleTypeEnum;
/**
 * 
 * @author Zubair
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AppUserService {
	private static final Logger logger = LogManager.getLogger(AppUserService.class);
	
	@Autowired
	private AppUserDAO appUserDAO;
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * This method is used to validate the email and password. Also we get all the
	 * roles and sub-user id's
	 * 
	 * @param appUserDTO
	 * @param messageSource
	 * @return
	 */
	public JsonResponse validateAppUser(AppUserDTO appUserDTO) {
		JsonResponse response=new JsonResponse();
		try {
			appUserDTO=appUserDAO.getUserByEmailAndPsw(appUserDTO.getEmailAddress(),appUserDTO.getPassword());
			if(Objects.nonNull(appUserDTO)) {
				appUserDTO.setSessionId(UUID.randomUUID().toString());
				//Going to save 
				appUserDAO.loginTrack(appUserDTO);
				
				List<RoleDTO> roles=appUserDAO.getRoleListByUserId(appUserDTO.getUserId());
				appUserDTO.setRoles(roles);
				boolean isRoleTypeMatch = roles.stream().anyMatch(role->role.getRoleType().equalsIgnoreCase(RoleTypeEnum.CEO.getValue())
						|| role.getRoleType().equalsIgnoreCase(RoleTypeEnum.DIRECTOR.getValue())
						|| role.getRoleType().equalsIgnoreCase(RoleTypeEnum.MANAGER.getValue()));
				if(isRoleTypeMatch) {
					List<AppUserDTO> userIds=appUserDAO.getSubUserIds(appUserDTO.getUserId());
					if(!CollectionUtils.isEmpty(userIds)) {
						String userIdsCommaSeparated = userIds.stream().map(AppUserDTO::getUserId)
								.map(String::valueOf)
                                .collect(Collectors.joining(","));
						appUserDTO.setSubUserIds(userIdsCommaSeparated);
					}
				}
				response.setData(appUserDTO);
			}else {
				response.setErrorCode(1);
				response.setErrorMessage(messageSource.getMessage("invalid.user.credentials", null, null));
			}
		} catch (Exception e) {
			response.setErrorCode(1);
			response.setErrorMessage(messageSource.getMessage("server.error", null, null));
			logger.error("Exception# validateAppUser# ",e);
		}
		return response;
	}
}
