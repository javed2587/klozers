package com.ssa.Klozerz.dao;

import java.sql.Types;
import java.util.logging.Logger;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;
import com.ssa.Klozerz.service.AppUserService;
import com.ssa.Klozerz.util.DataBaseConfig;

@Repository
public class BaseDAO {
	private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(AppUserService.class);
	@Autowired
	private DataBaseConfig database;
	@Autowired
	NamedParameterJdbcTemplate namedJdbcTemplate;

	/**
	 * This method is used to init the JdbcTemplate for the lportal DB
	 * 
	 * @return
	 */
	public JdbcTemplate getJdbcTemplate() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		String url = database.getUrl() + "lportal?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8";
		dataSource.setUrl(url);
		dataSource.setUsername(database.getUsername());
		dataSource.setPassword(database.getPassword());
		namedJdbcTemplate.getJdbcTemplate().setDataSource(dataSource);
		return namedJdbcTemplate.getJdbcTemplate();
	}

	/**
	 * This method is used to init the JdbcTemplate for the module DB
	 * 
	 * @return
	 */
	public JdbcTemplate getJdbcTemplate(String userId) {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		String databaseId = this.getDatabaseName(Integer.parseInt(userId));

		String url = database.getUrl() + "module_" + databaseId
				+ "?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8";
		dataSource.setUrl(url);
		dataSource.setUsername(database.getUsername());
		dataSource.setPassword(database.getPassword());
		namedJdbcTemplate.getJdbcTemplate().setDataSource(dataSource);
		return namedJdbcTemplate.getJdbcTemplate();
	}

	/**
	 * This Query is used to get the treepath from the organization table
	 * 
	 * @param userId
	 * @return
	 */
	public String getOrganizationTreepathByUserId(Integer userId) {
		String organization_treepath = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("organization_.treepath");
		sql.append(" FROM organization_ ");
		sql.append(" INNER JOIN users_orgs ON organization_.organizationid = users_orgs.organizationid ");
		sql.append(" and users_orgs.userid =?");

		//System.out.print(false);
		try {
			 organization_treepath = getJdbcTemplate().queryForObject(sql.toString(), new Object[] { userId },
					new int[] { Types.BIGINT }, String.class);
			return organization_treepath;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception# BaseDAO->getOrganizationTreepathByUserId# ",e);
			System.out.println("User configuration issue.");
			return organization_treepath;
			
		}

	}

	public String getDatabaseName(int userId) {
		String treePath = this.getOrganizationTreepathByUserId(userId);
		String Str[] = treePath.split("/");
		System.out.println("Return Value :" + Str[1]);
		return Str[1];
	}

}
