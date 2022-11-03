package com.ssa.Klozerz.dao;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ssa.Klozerz.dto.AppUserDTO;
import com.ssa.Klozerz.dto.RoleDTO;
import com.ssa.Klozerz.util.AppUtil;

/**
 * 
 * @author Zubair
 *
 */
@Repository
public class AppUserDAO extends BaseDAO {
	
	/***
	 * This query is used to get the user from the user table
	 * 
	 * @param appUserDTO
	 * @return
	 * @throws Exception
	 */
	public AppUserDTO getUserByEmailAndPsw(String emailAddress, String password) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("userid as userId,");
		sql.append("firstname as firstName,");
		sql.append("middlename as middleName,");
		sql.append("lastname as lastName,");
		sql.append("companyid as companyId,");
		sql.append("emailaddress as emailAddress,");
		sql.append("timezoneid as timeZoneId");
		sql.append(" FROM user_ ");
		sql.append("where emailaddress=? ");
		sql.append("And password_=? ");

		List<AppUserDTO> resultList = getJdbcTemplate().query(sql.toString(), new Object[] { emailAddress, password },
				new int[] { Types.VARCHAR, Types.VARCHAR }, new BeanPropertyRowMapper<AppUserDTO>(AppUserDTO.class));
		System.out.println("query # " +sql.toString());
		System.out.println("result list# " + resultList.size());
		AppUserDTO appUserDTO = null;
		if (!CollectionUtils.isEmpty(resultList)) {
			appUserDTO = resultList.get(0);
		}
		return appUserDTO;
	}

	/**
	 * This Query is used to get the all the roles from the users_roles table
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<RoleDTO> getRoleListByUserId(Integer userId) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("rol.roleid as roleId,");
		sql.append("rol.name as roleType ");
		sql.append(" FROM users_roles As users_roles ");
		sql.append(" inner join role_ As rol on rol.roleid=users_roles.roleid ");
		sql.append("where users_roles.userid=? ");

		List<RoleDTO> resultList = namedJdbcTemplate.getJdbcTemplate().query(sql.toString(),
				new Object[] { userId }, new int[] { Types.BIGINT }, new BeanPropertyRowMapper<RoleDTO>(RoleDTO.class));

		return resultList;
	}

	/**
	 * This Query is used to get the sub user ids from the users_orgs table
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<AppUserDTO> getSubUserIds(Integer userId) throws Exception {
		String organization_treepath = this.getOrganizationTreepathByUserId(userId);
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT distinct ");
		sql.append("userid AS userId ");
		sql.append(" FROM users_orgs ");
		sql.append(" where organizationid in (select organizationid from organization_ where treepath like :treepath)");
		SqlParameterSource param = new MapSqlParameterSource("treepath", organization_treepath + "%");

		List<AppUserDTO> userIds = namedJdbcTemplate.query(sql.toString(), param,
				new BeanPropertyRowMapper<AppUserDTO>(AppUserDTO.class));
		return userIds;
	}
	
	/**
	 * The query is used to track the user login from the device
	 * 
	 * @param appUserDTO
	 * @throws Exception
	 */
	public void loginTrack(AppUserDTO appUserDTO) throws Exception{
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO gahc_usertrack(user_id,username,login_ip,user_agent,uuid,login_from,location_detail)");
		query.append("VALUES(:user_id,:username,:login_ip,:user_agent,:uuid,:login_from,:location_detail)");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("user_id", appUserDTO.getUserId());
		paramMap.put("username", appUserDTO.getFirstName()+" "+AppUtil.getSafeStr(appUserDTO.getLastName(), ""));
		paramMap.put("login_ip", AppUtil.getSafeStr(appUserDTO.getLoginIp(),""));
		paramMap.put("user_agent", AppUtil.getSafeStr(appUserDTO.getUserAgent(),""));
		paramMap.put("uuid", appUserDTO.getSessionId());
		paramMap.put("login_from", AppUtil.getSafeStr(appUserDTO.getLoginFrom(),""));
		paramMap.put("location_detail", AppUtil.getSafeStr(appUserDTO.getLocationDetail(),""));
		
		namedJdbcTemplate.update(query.toString(), paramMap);  
	}
}
