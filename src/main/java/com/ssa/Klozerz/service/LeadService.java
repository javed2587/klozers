package com.ssa.Klozerz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ssa.Klozerz.common.JsonResponse;
import com.ssa.Klozerz.dao.AppUserDAO;
import com.ssa.Klozerz.dao.LeadDAO;
import com.ssa.Klozerz.dto.AppUserDTO;
import com.ssa.Klozerz.dto.LeadDTO;
import com.ssa.Klozerz.dto.RoleDTO;
import com.ssa.Klozerz.enums.RoleTypeEnum;

@Service
@Transactional(rollbackFor = Exception.class)
public class LeadService {

	@Autowired
	private LeadDAO leadDAO;
	@Autowired
	private MessageSource messageSource;
	
//	private static final Logger logger = LogManager.getLogger(LeadService.class);
	
	public JsonResponse addLead(LeadDTO lead)
	{
		JsonResponse response=new JsonResponse();
		try {
			if(leadDAO.duplicatePhoneAndZipCode(lead.getPhone(), lead.getZipCode(), lead.getUser_id()))
			{
				response.setErrorCode(1);
				response.setErrorMessage("Duplicate phone and zipcode");
				return response;
			}
			if(leadDAO.duplicatePhone(lead.getPhone(), lead.getUser_id()))
			{
				response.setErrorCode(1);
				response.setErrorMessage("Duplicate phone");
				return response;
			}
			LeadDTO fac=new LeadDTO();
			fac.setId(String.valueOf(leadDAO.addLead(lead)));
			response.setData(fac);
		} catch (Exception e) {
			e.printStackTrace();
			response.setErrorCode(0);
			response.setErrorMessage("Server Error");
			return response;
		}
		return response;
	}
	public JsonResponse getLeadList(AppUserDTO user)
	{
		JsonResponse response=new JsonResponse();
	try {
		List<LeadDTO> leads;
		List<RoleDTO> roles=user.getRoles();
		boolean isRoleTypeMatch = roles.stream().anyMatch(role->role.getRoleType().equalsIgnoreCase(RoleTypeEnum.CEO.getValue())
				|| role.getRoleType().equalsIgnoreCase(RoleTypeEnum.DIRECTOR.getValue())
				|| role.getRoleType().equalsIgnoreCase(RoleTypeEnum.MANAGER.getValue()));
		if(isRoleTypeMatch)
			leads=leadDAO.getLeadList(user.getUserId().toString(), user.getSubUserIds());
		else
			leads=leadDAO.getLeadList(user.getUserId().toString(),user.getUserId().toString() );
		System.out.println("leads:"+leads.size());
		response.setData(leads);
		response.setTotalRecords(leads.size());
		
		
		} catch (Exception e) {
			e.printStackTrace();
			response.setErrorCode(0);
			response.setErrorMessage("Server Error");
			return response;
		}
		return response;
	}
	
	public JsonResponse getAccountList(AppUserDTO user)
	{
		JsonResponse response=new JsonResponse();
	try {
		List<LeadDTO> leads;
		List<RoleDTO> roles=user.getRoles();
		boolean isRoleTypeMatch = roles.stream().anyMatch(role->role.getRoleType().equalsIgnoreCase(RoleTypeEnum.CEO.getValue())
				|| role.getRoleType().equalsIgnoreCase(RoleTypeEnum.DIRECTOR.getValue())
				|| role.getRoleType().equalsIgnoreCase(RoleTypeEnum.MANAGER.getValue()));
		if(isRoleTypeMatch)
			leads=leadDAO.getAccountList(user.getUserId().toString(), user.getSubUserIds());
		else
			leads=leadDAO.getAccountList(user.getUserId().toString(),user.getUserId().toString() );
		System.out.println("Accounts:"+leads.size());
		response.setData(leads);
		response.setTotalRecords(leads.size());
		
		
		} catch (Exception e) {
			e.printStackTrace();
			response.setErrorCode(0);
			response.setErrorMessage("Server Error");
			return response;
		}
		return response;
	}
	public Boolean editLead(LeadDTO lead) {
		boolean isUpdated=false;
		try {
			isUpdated = leadDAO.editLead(lead);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isUpdated;
		
	}
	public boolean editAccount(LeadDTO lead) {
		try {
				leadDAO.editAccount(lead);
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
}
