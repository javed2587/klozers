package com.ssa.Klozerz.service;

import java.util.ArrayList;
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
import com.ssa.Klozerz.dao.DashBoardDAO;
import com.ssa.Klozerz.dto.AppUserDTO;
import com.ssa.Klozerz.dto.CalendarAppointmentDTO;
import com.ssa.Klozerz.dto.DashBoardDTO;
import com.ssa.Klozerz.dto.RoleDTO;
import com.ssa.Klozerz.enums.RoleTypeEnum;


@Service
@Transactional(rollbackFor = Exception.class)
public class DashBoardService {
	private static final Logger logger = LogManager.getLogger(AppUserService.class);
	
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private DashBoardDAO dashBoardDao;

	public DashBoardDTO fetchUpComingCalenderAppointments(CalendarAppointmentDTO calendarAppointmentDTO) {
		//JsonResponse response=new JsonResponse();
	//	List<DashBoardDTO> list = new ArrayList<>();
		DashBoardDTO dashBoardDTO = new DashBoardDTO();
		try {
		    dashBoardDTO.setAccountAppointments(dashBoardDao.getAccountCalenderUpcomingCampaigns(calendarAppointmentDTO.getUserId()));
		    dashBoardDTO.setLeadAppointments(dashBoardDao.getLeadCalenderUpcomingCampaigns(calendarAppointmentDTO.getUserId()));
		    dashBoardDTO.setPersonlaAppointmens(dashBoardDao.getPersonalCalenderUpcomingCampaigns(calendarAppointmentDTO.getUserId()));
		   // list.add(dashBoardDTO);
		    
			//System.out.println(list);
		} catch (Exception e) {
			logger.error("Exception# fetchUpComingCalenderAppointments# ",e);
		}
		return dashBoardDTO;
	}
}
