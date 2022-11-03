package com.ssa.Klozerz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.ssa.Klozerz.dto.AccountDTO;
import com.ssa.Klozerz.dto.AppUserDTO;
import com.ssa.Klozerz.dto.LeadDTO;
import com.ssa.Klozerz.dto.RoleDTO;
import com.ssa.Klozerz.util.AppUtil;
import com.ssa.Klozerz.util.DataBaseConfig;


@Repository
public class LeadDAO extends BaseDAO {
//	@Autowired
//	private NamedParameterJdbcTemplate namedJdbcTemplate;
//	@Autowired
//	private DataBaseConfig database;
//	
//	@Autowired
//	private AppUserDAO appUser;
//	/**
//	 * This method is used to init the JdbcTemplate
//	 * 
//	 * @return
//	 */
//	public JdbcTemplate getJdbcTemplate(String userId) {
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName("org.postgresql.Driver");
//		String databaseId=appUser.getDatabaseName(Integer.parseInt(userId));
//			
//		String url=database.getUrl()+"module_"+databaseId+"?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8";
//		dataSource.setUrl(url);
//		dataSource.setUsername(database.getUsername());
//		dataSource.setPassword(database.getPassword());
//		namedJdbcTemplate.getJdbcTemplate().setDataSource(dataSource);
//		return namedJdbcTemplate.getJdbcTemplate();
//	}

	/***
	 * This query is used to add the lead to table
	 * 
	 * @param appUserDTO
	 * @return
	 * @throws Exception
	 */
	
///////*************************************//////////
  public int addLead(LeadDTO lead) throws Exception {
	  KeyHolder keyHolder = new GeneratedKeyHolder();
	  StringBuilder sql = new StringBuilder();
	  sql.append( "INSERT INTO lead(\"NPI\",\"Payor Type\",\"Facility Name\",\"Facility Type\",\"Rating\",\"Street\","
	  		+ "\"Suggested Rating\",\"Phone\",\"Main Fax\",\"How can I make your job easier?\",\"Status\",\"State\",\"City\","
	  		+ "\"Street Line 2\",\"Lead Source\",\"The most important thing to consider during agency reference?\",\"Email\",\"How can I earn your trust and business?\","
	  		+ "\"Website\",\"Zip Code\",\"Biggest challenges with agencies youre utilizing?\",\"#Appointments canceled due to transportation?\","
	  		+ "\"What is the best time for me to come and visit?\",\"Other Sub Facility Type\",\"#Patients you see per day on an average?\","
	  		+ "\"How often do you use Home Health per month on average?\",\"Do you want to give us a chance to show what we can do?\","
	  		+ "\"#Patients call the office more than once per week?\",\"#ER Visits could be avoided by a nurse call?\",\"Suggested Frequency\","
	  		+ "\"#Your patient have frequent visit to ER?\",office_hours,how_often_do_you_shift_for_nursing,what_size_is_your_active_caseload,"
	  		+ "how_often_you_refer_to_private_duty, how_often_do_you_refer_to_transportation,user_id,created_by,modified_by,created_date,modified_date) ");
	  
	  sql.append("VALUES("+(!AppUtil.isNullOrEmpty(lead.getNPI()) ? "'"+lead.getNPI()+"'" : (Object)null)+","+
		(!AppUtil.isNullOrEmpty(lead.getPayorType())? "'"+lead.getPayorType()+"'" :(Object)null)+","+
		"'"+lead.getName()+"',"+(!AppUtil.isNullOrEmpty(lead.getType()) ? "'"+lead.getType()+"'" : "'Other'")+","+
		(!AppUtil.isNullOrEmpty(lead.getRating()) ? "'"+lead.getRating()+"'": "'None'" )+","+
		(!AppUtil.isNullOrEmpty(lead.getAddress1())? "'"+lead.getAddress1()+"'":null)+","+
		(!AppUtil.isNullOrEmpty(lead.getRating())? "'"+lead.getRating()+"'": "'None'" )+","+
		("'"+lead.getPhone()+"'")+","+(!AppUtil.isNullOrEmpty(lead.getFax()) ? "'"+lead.getFax()+"'" : (Object)null )+","+
		(!AppUtil.isNullOrEmpty(lead.getHowcanImakeyourjobeasier()) ? "'"+lead.getHowcanImakeyourjobeasier()+"'":(Object)null )+","+
		(!AppUtil.isNullOrEmpty(lead.getStatus())?"'"+lead.getStatus()+"'":"'Open'")+","+(!AppUtil.isNullOrEmpty(lead.getState()) ? "'"+lead.getState()+"'" : (Object)null)+","+
		(!AppUtil.isNullOrEmpty(lead.getCity()) ? "'"+lead.getCity()+"'" : (Object)null)+","+
		(!AppUtil.isNullOrEmpty(lead.getAddress2()) ? "'"+lead.getAddress2()+"'" : (Object)null)+","+
		(!AppUtil.isNullOrEmpty(lead.getLeadSource())? "'"+lead.getLeadSource()+"'": (Object)null)+","+
		(!AppUtil.isNullOrEmpty(lead.getThemostimportantthingtoconsiderduringagencyreference())
		? "'"+lead.getThemostimportantthingtoconsiderduringagencyreference()+"'" : (Object)null)+","+
		(!AppUtil.isNullOrEmpty(lead.getEmail()) ? "'"+lead.getEmail()+"'":(Object)null)+","+
		(!AppUtil.isNullOrEmpty(lead.getHowcanIearnyourtrustandbusiness())? "'"+lead.getHowcanIearnyourtrustandbusiness()+"'" :(Object)null)+","+
		
		(!AppUtil.isNullOrEmpty(lead.getWebsite()) ? "'"+lead.getWebsite()+"'" :(Object)null)+","+
		(!AppUtil.isNullOrEmpty(lead.getZipCode())? "'"+lead.getZipCode()+"'":(Object)null)+","+
		(!AppUtil.isNullOrEmpty(lead.getBiggestchallengeswithagenciesyoureutilizing())
				? "'"+lead.getBiggestchallengeswithagenciesyoureutilizing()+"'": (Object)null)+","+
		(!AppUtil.isNullOrEmpty(lead.getAppointmentscanceledduetotransportation()) ? "'"+lead.getAppointmentscanceledduetotransportation()+"'": (Object)null)+","+
		(!AppUtil.isNullOrEmpty(lead.getWhatthebesttimeformetocomeandvisit()) ? "'"+lead.getWhatthebesttimeformetocomeandvisit()+"'" : (Object)null)+","+
		(!AppUtil.isNullOrEmpty(lead.getSubType())? "'"+lead.getSubType()+"'":(Object)null )+","+
		(!AppUtil.isNullOrEmpty(lead.getPatientsyouseeperdayonanaverage()) ? "'"+lead.getPatientsyouseeperdayonanaverage()+"'":(Object)null )+","+
		(!AppUtil.isNullOrEmpty(lead.getHowoftendoyouuseHomeHealthpermonthonaverage()) ? 
				"'"+lead.getHowoftendoyouuseHomeHealthpermonthonaverage()+"'":(Object)null )+","+
		(!AppUtil.isNullOrEmpty(lead.getDoyouwanttogiveusachancetoshowwhatwecando()) ? 
				"'"+lead.getDoyouwanttogiveusachancetoshowwhatwecando()+"'":(Object)null )+","+
		(!AppUtil.isNullOrEmpty(lead.getPatientscalltheofficemorethanonceperweek()) 
				? "'"+lead.getPatientscalltheofficemorethanonceperweek()+"'" : (Object)null )+","+
		(!AppUtil.isNullOrEmpty(lead.getERVisitscouldbeavoidedbyanursecall()) ? 
				"'"+lead.getERVisitscouldbeavoidedbyanursecall()+"'":(Object)null )+","+
		(!AppUtil.isNullOrEmpty(lead.getSuggestedFrequency())? "'"+lead.getSuggestedFrequency()+"'": (Object)null)+","+
		(!AppUtil.isNullOrEmpty(lead.getYourpatienthavefrequentvisittoER()) ? 
				"'"+lead.getYourpatienthavefrequentvisittoER()+"'" : (Object)null )+","+
		(!AppUtil.isNullOrEmpty(lead.getOfficeHours()) ? "'"+lead.getOfficeHours()+"'" : (Object)null )+","+
		(!AppUtil.isNullOrEmpty(lead.getHowoftendoyoushiftfornursing()) ? 
				"'"+lead.getHowoftendoyoushiftfornursing()+"'": (Object)null  )+","+
		(!AppUtil.isNullOrEmpty(lead.getWhatsizeisyouractivecaseload()) ? 
				"'"+lead.getWhatsizeisyouractivecaseload()+"'": (Object)null  )+","+
		(!AppUtil.isNullOrEmpty(lead.getHowoftenyourefertoprivateduty()) ? 
				"'"+lead.getHowoftenyourefertoprivateduty()+"'" : (Object)null )+","+
		(!AppUtil.isNullOrEmpty(lead.getHowoftendoyourefertotransportation()) ? 
				"'"+lead.getHowoftendoyourefertotransportation()+"'" : (Object)null )+","+
		lead.getUser_id()+",'"+
		lead.getCreated_by()+"','"+
		lead.getModified_by()+"',NOW(),NOW() )");
	 System.out.println(sql.toString());
	  getJdbcTemplate(lead.getUser_id()).update(
			new PreparedStatementCreator() {
			    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			      return connection.prepareStatement(sql.toString(), new String[] {"id"});
			    }
			  }, keyHolder
			);
    return keyHolder.getKey().intValue();     
}
 
  public boolean duplicatePhone(String phone,String userId) {
	  String sql="SELECT count(id) FROM lead WHERE archive_status='new' AND LOWER(\"Phone\") = LOWER('"+phone+"')";
		int count = getJdbcTemplate(userId).queryForObject(sql.toString(),Integer.class);
		return count>0;
  }
  public boolean duplicatePhoneAndZipCode(String phone,String zipCode,String userId) {
	  String sql="SELECT count(id) FROM lead WHERE archive_status='new' AND LOWER(\"Phone\") = LOWER('"+phone+"') AND LOWER(\"Zip Code\") = LOWER('"+zipCode+"')";
		int count = getJdbcTemplate(userId).queryForObject(sql.toString(),Integer.class);
		return count>0;
  }
  
  public List<LeadDTO> getLeadList(String userId,String memberIds) {
	  
	  String sql="SELECT id,\"Facility Name\" as name,\"Facility Type\" as type,\"Other Sub Facility Type\" as subType,\"NPI\" as npi,\"Who is the decision maker?\" as whoisthedecisionmaker,"
	  		+ "\"Street\" as address1,\"Street Line 2\" as address2,\"City\" as city,\"State\" as state,\"Zip Code\" as zipCode,\"Phone\" as phone,"
	  		+ "\"Main Fax\" as fax,\"Email\" as email,\"Website\" as website,\"How can I earn your trust and business?\" as howcanIearnyourtrustandbusiness,"
	  		+ "\"Lead Source\" as leadSource,\"Rating\" as rating,\"Status\" as status,\"Payor Type\" as payorType,\"GPS Punch\" as gpsPunch,"
	  		+ "created_by,created_date,modified_by,modified_date,archived_date,archived_by,user_id,archive_status,\"Suggested Rating\" as suggestedRating,"
	  		+ "lead_to_account,contact_role,\"Biggest challenges with agencies youre utilizing?\" as biggestchallengeswithagenciesyoureutilizing,"
	  		+ "\"#Your patient have frequent visit to ER?\" as yourpatienthavefrequentvisittoER,\"#ER Visits could be avoided by a nurse call?\" as eRVisitscouldbeavoidedbyanursecall,"
	  		+ "\"#Appointments canceled due to transportation?\" as appointmentscanceledduetotransportation,\"#Patients call the office more than once per week?\" as patientscalltheofficemorethanonceperweek,"
	  		+ "\"The most important thing to consider during agency reference?\" as themostimportantthingtoconsiderduringagencyreference,\"How can I make your job easier?\" as howcanImakeyourjobeasier,"
	  		+ " \"What is the best time for me to come and visit?\" as whatthebesttimeformetocomeandvisit,\"#Patients you see per day on an average?\" as patientsyouseeperdayonanaverage,"
	  		+ "\"How often do you use Home Health per month on average?\" as howoftendoyouuseHomeHealthpermonthonaverage,\"Do you want to give us a chance to show what we can do?\" as doyouwanttogiveusachancetoshowwhatwecando,"
	  		+ "progress_note_last_modified_date as progressNoteLastModifiedDate,office_hours as officeHours,merge_status as mergeStatus  "
	  		+ "FROM lead WHERE archive_status='new' AND user_id IN("+memberIds+") ORDER BY modified_date DESC";
//	  System.out.println(sql);
	  List<LeadDTO> leadList = getJdbcTemplate(userId).query(sql.toString(),new BeanPropertyRowMapper<LeadDTO>(LeadDTO.class));
		return leadList;
  }
  
  public List<LeadDTO> getAccountList(String userId,String memberIds) {

	  String sqlQuery="SELECT id,\"Facility Name\" as name,\"Account Type\" as type,\"Hospital Sub Facility Type\" as subType,"
	  		+ "\"Other Sub Facility Type\" as otherSubType,\"Who is the decision maker?\" as whoisthedecisionmaker,\"Street\" as address1,"
	  		+ "\"Street Line 2\" as address2,\"City\" as city,\"State\" as state,\"Zip Code\" as zipCode,\"Phone\" as phone,\"Main Fax\" as fax,"
	  		+ "\"Email\" as email,\"lead_id\" as leadId,\"Note\" as facilityNotes,\"Facility size\" as facilitySize,\"What could I do to make your job easier?\" as howcanImakeyourjobeasier,"
	  		+ "\"How can I earn your trust and business?\" as howcanIearnyourtrustandbusiness, \"Rating\" as rating,\"Payor Type\" as payorType,"
	  		+ "\"GPS Punch\" as gpsPunch,lead_to_account,archive_status,\"Biggest challenges with agencies youre utilizing?\" as biggestchallengeswithagenciesyoureutilizing,"
	  		+ "\"#Your patient have frequent visit to ER?\" as yourpatienthavefrequentvisittoER,\"#ER Visits could be avoided by a nurse call?\" as ervisitscouldbeavoidedbyanursecall,"
	  		+ "\"#Appointments canceled due to transportation?\" as appointmentscanceledduetotransportation,\"#Patients call the office more than once per week?\" as patientscalltheofficemorethanonceperweek,"
	  		+ "\"The most important thing to consider during agency reference?\" as themostimportantthingtoconsiderduringagencyreference,"
	  		+ "\"What is the best time for me to come and visit?\" as whatthebesttimeformetocomeandvisit,\"#Patients you see per day on an average?\" as patientsyouseeperdayonanaverage,"
	  		+ "\"How often do you use Home Health per month on average?\" as howoftendoyouuseHomeHealthpermonthonaverage,\"Do you want to give us a chance to show what we can do?\" as doyouwanttogiveusachancetoshowwhatwecando,"
	  		+ "progress_note_last_modified_date as progressNoteLastModifiedDate,\"how_often_do_you_shift_for_nursing\" as howoftendoyoushiftfornursing,\"what_size_is_your_active_caseload\" as whatsizeisyouractivecaseload,"
	  		+ "\"how_often_you_refer_to_private_duty\" as howoftenyourefertoprivateduty, \"how_often_do_you_refer_to_transportation\" as howoftendoyourefertotransportation,"
	  		+ "office_hours as officeHours,user_id,created_by,created_date,modified_by,modified_date,archived_by FROM account WHERE archive_status = 'new' AND user_id IN("+memberIds+") ORDER BY modified_date DESC";
//	  System.out.println(sqlQuery);
	  List<LeadDTO> accountList = getJdbcTemplate(userId).query(sqlQuery.toString(),new BeanPropertyRowMapper<LeadDTO>(LeadDTO.class));
		System.out.println(accountList.size());
		return accountList;
  }
  public boolean editLead(LeadDTO lead) throws Exception {
	  StringBuilder sql = new StringBuilder();
	  sql.append("UPDATE lead SET ");
	  sql.append("\"NPI\"= "+(!AppUtil.isNullOrEmpty(lead.getNPI()) ? "'"+lead.getNPI()+"'" : (Object)null)+",");
	  sql.append(" \"Payor Type\"=" +(!AppUtil.isNullOrEmpty(lead.getPayorType())? "'"+lead.getPayorType()+"'" :(Object)null)+",");
	  sql.append(" \"Facility Name\"='"+lead.getName()+"',");
	  sql.append(" \"Facility Type\"="+(!AppUtil.isNullOrEmpty(lead.getType()) ? "'"+lead.getType()+"'" : "'Other'")+","); 
	  sql.append(" \"Rating\" ="+(!AppUtil.isNullOrEmpty(lead.getRating()) ? "'"+lead.getRating()+"'": "'None'" )+",");
	  sql.append(" \"Street\"="+(!AppUtil.isNullOrEmpty(lead.getAddress1())? "'"+lead.getAddress1()+"'":null)+",");
	  sql.append(" \"Suggested Rating\"="+(!AppUtil.isNullOrEmpty(lead.getRating())? "'"+lead.getRating()+"'": "'None'" )+",");
	  sql.append(" \"Phone\"="+("'"+lead.getPhone()+"',"));
	  sql.append(" \"Main Fax\"="+(!AppUtil.isNullOrEmpty(lead.getFax()) ? "'"+lead.getFax()+"'" : (Object)null )+",");
	  sql.append(" \"How can I make your job easier?\"="+(!AppUtil.isNullOrEmpty(lead.getHowcanImakeyourjobeasier()) ? "'"+lead.getHowcanImakeyourjobeasier()+"'":(Object)null )+",");
	  sql.append(" \"Status\"="+(!AppUtil.isNullOrEmpty(lead.getStatus())?"'"+lead.getStatus()+"'":"'Open'")+",");
	  sql.append(" \"State\"="+(!AppUtil.isNullOrEmpty(lead.getState()) ? "'"+lead.getState()+"'" : (Object)null)+",");
	  sql.append(" \"City\"="+(!AppUtil.isNullOrEmpty(lead.getCity()) ? "'"+lead.getCity()+"'" : (Object)null)+",");
	  sql.append(" \"Street Line 2\"="+(!AppUtil.isNullOrEmpty(lead.getAddress2()) ? "'"+lead.getAddress2()+"'" : (Object)null)+",");
	  sql.append(" \"Lead Source\"="+(!AppUtil.isNullOrEmpty(lead.getLeadSource())? "'"+lead.getLeadSource()+"'": (Object)null)+",");
	  sql.append(" \"The most important thing to consider during agency reference?\"="+(!AppUtil.isNullOrEmpty(lead.getThemostimportantthingtoconsiderduringagencyreference())
				? "'"+lead.getThemostimportantthingtoconsiderduringagencyreference()+"'" : (Object)null)+",");
	  sql.append(" \"Email\"="+(!AppUtil.isNullOrEmpty(lead.getEmail()) ? "'"+lead.getEmail()+"'":(Object)null)+",");
	  sql.append(" \"How can I earn your trust and business?\"="+(!AppUtil.isNullOrEmpty(lead.getHowcanIearnyourtrustandbusiness())? "'"+lead.getHowcanIearnyourtrustandbusiness()+"'" :(Object)null)+",");
	  sql.append(" \"Website\"="+(!AppUtil.isNullOrEmpty(lead.getWebsite()) ? "'"+lead.getWebsite()+"'" :(Object)null)+",");
	  sql.append(" \"Zip Code\"="+(!AppUtil.isNullOrEmpty(lead.getZipCode())? "'"+lead.getZipCode()+"'":(Object)null)+",");
	  sql.append(" \"Biggest challenges with agencies youre utilizing?\" ="+(!AppUtil.isNullOrEmpty(lead.getBiggestchallengeswithagenciesyoureutilizing())
				? "'"+lead.getBiggestchallengeswithagenciesyoureutilizing()+"'": (Object)null)+",");
	  sql.append(" \"#Appointments canceled due to transportation?\" ="+(!AppUtil.isNullOrEmpty(lead.getAppointmentscanceledduetotransportation()) ? "'"+lead.getAppointmentscanceledduetotransportation()+"'": (Object)null)+",");
	  sql.append(" \"What is the best time for me to come and visit?\" ="+(!AppUtil.isNullOrEmpty(lead.getWhatthebesttimeformetocomeandvisit()) ? "'"+lead.getWhatthebesttimeformetocomeandvisit()+"'" : (Object)null)+",");
	  sql.append(" \"Other Sub Facility Type\"= "+(!AppUtil.isNullOrEmpty(lead.getSubType())? "'"+lead.getSubType()+"'":(Object)null )+",");
	  sql.append(" \"#Patients you see per day on an average?\" ="+	(!AppUtil.isNullOrEmpty(lead.getPatientsyouseeperdayonanaverage()) ? "'"+lead.getPatientsyouseeperdayonanaverage()+"'":(Object)null )+",");
	  sql.append(" \"How often do you use Home Health per month on average?\"  ="+(!AppUtil.isNullOrEmpty(lead.getHowoftendoyouuseHomeHealthpermonthonaverage()) ? 
				"'"+lead.getHowoftendoyouuseHomeHealthpermonthonaverage()+"'":(Object)null )+",");
	  sql.append(" \"Do you want to give us a chance to show what we can do?\" ="+(!AppUtil.isNullOrEmpty(lead.getDoyouwanttogiveusachancetoshowwhatwecando()) ? 
				"'"+lead.getDoyouwanttogiveusachancetoshowwhatwecando()+"'":(Object)null )+",");
	  sql.append(" \"#Patients call the office more than once per week?\" ="+(!AppUtil.isNullOrEmpty(lead.getPatientscalltheofficemorethanonceperweek()) 
				? "'"+lead.getPatientscalltheofficemorethanonceperweek()+"'" : (Object)null )+",");
	  sql.append(" \"#ER Visits could be avoided by a nurse call?\" ="+(!AppUtil.isNullOrEmpty(lead.getERVisitscouldbeavoidedbyanursecall()) ? 
				"'"+lead.getERVisitscouldbeavoidedbyanursecall()+"'":(Object)null )+",");
	  sql.append(" \"Suggested Frequency\" ="+(!AppUtil.isNullOrEmpty(lead.getSuggestedFrequency())? "'"+lead.getSuggestedFrequency()+"'": (Object)null)+",");
	  sql.append(" \"#Your patient have frequent visit to ER?\" ="+(!AppUtil.isNullOrEmpty(lead.getYourpatienthavefrequentvisittoER()) ? 
				"'"+lead.getYourpatienthavefrequentvisittoER()+"'" : (Object)null )+",");
	  sql.append(" \"office_hours\"="+(!AppUtil.isNullOrEmpty(lead.getOfficeHours()) ? "'"+lead.getOfficeHours()+"'" : (Object)null )+",");
	  sql.append(" \"how_often_do_you_shift_for_nursing\"="+(!AppUtil.isNullOrEmpty(lead.getHowoftendoyoushiftfornursing()) ? 
				"'"+lead.getHowoftendoyoushiftfornursing()+"'": (Object)null  )+",");
	  sql.append(" \"what_size_is_your_active_caseload\" ="+(!AppUtil.isNullOrEmpty(lead.getWhatsizeisyouractivecaseload()) ? 
				"'"+lead.getWhatsizeisyouractivecaseload()+"'": (Object)null  )+",");
	  sql.append(" \"how_often_you_refer_to_private_duty\"="+(!AppUtil.isNullOrEmpty(lead.getHowoftenyourefertoprivateduty()) ? 
				"'"+lead.getHowoftenyourefertoprivateduty()+"'" : (Object)null )+",");
	  sql.append(" \"how_often_do_you_refer_to_transportation\" ="+(!AppUtil.isNullOrEmpty(lead.getHowoftendoyourefertotransportation()) ? 
				"'"+lead.getHowoftendoyourefertotransportation()+"'" : (Object)null )+",");
	  sql.append(" created_by = '"+lead.getCreated_by()+"',");
	  sql.append(" modified_by = '"+lead.getModified_by()+"',");
	  sql.append(" created_date = Now(),");
	  sql.append(" modified_date = Now()");
	  sql.append(" where user_id = "+lead.getUser_id());
	  sql.append(" and id = "+lead.getId());
	  
	  System.out.println(sql.toString());
		 getJdbcTemplate(lead.getUser_id()).update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					return con.prepareStatement(sql.toString());
  }
});
		return true;
  }
  
  public boolean editAccount(LeadDTO leadDto) {
	  StringBuilder sql = new StringBuilder();
          sql.append(" UPDATE account SET"); 
          sql.append(" \"Account Type\" = '"+leadDto.getType()+"',");
          sql.append(" \"Hospital Sub Facility Type\" = "+leadDto.getSubType()+",");
          // sql.append(" \"Other Sub Facility Type\" = "+leadDto.getSubType()+",");
          //sql.append(" \"Click to get NPI.\" = "+leadDto.getNPI()+",");
          sql.append(" \"NPI #\" = "+leadDto.getNPI()+",");
          sql.append(" \"Who is the decision maker?\"= '"+leadDto.getWhoisthedecisionmaker()+"',");
          sql.append(" \"Street\" = '"+leadDto.getAddress1()+"',");
          sql.append(" \"Street Line 2\"= '"+leadDto.getAddress2()+"',");
          sql.append(" \"City\"= '"+leadDto.getCity()+"',");
          sql.append(" \"State\"= '"+leadDto.getState()+"',");
          sql.append(" \"Zip Code\" = "+leadDto.getZipCode()+",");
          sql.append(" \"Phone\"= "+leadDto.getPhone()+",");
          sql.append("  modified_by = '"+leadDto.getModified_by()+"',");
          sql.append(" modified_date = Now(),");
         // sql.append(" \"Email\"= "+leadDto.getEmail()+",");
         // sql.append(" \"Note\"='"+leadDto.getFacilityNotes()+"',");
         // sql.append(" \"Facility size\"= "+leadDto.getFacilitySize()+",");
         // sql.append(" \"Could you give us an opportunity?\" = "+leadDto.get+",");
          sql.append(" \"How can I earn your trust and business?\" = '"+leadDto.getHowcanIearnyourtrustandbusiness()+"',");
          sql.append(" \"Payor Type\" = '"+leadDto.getPayorType()+"',");
          sql.append(" \"Avg #Home Care Referrals per Month\" = '"+leadDto.getOfmonthlyreferrals()+"',");
          sql.append(" \"GPS Punch\" = '"+leadDto.getGpsPunch()+"',");
          sql.append("  lead_to_account = '"+leadDto.getLead_to_account()+"',");
          sql.append(" \"Biggest challenges with agencies youre utilizing?\" = '"+leadDto.getBiggestchallengeswithagenciesyoureutilizing()+"',");
          sql.append(" \"#Your patient have frequent visit to ER?\" = "+leadDto.getYourpatienthavefrequentvisittoER()+",");
          sql.append("  \"#ER Visits could be avoided by a nurse call?\" = "+leadDto.getERVisitscouldbeavoidedbyanursecall()+",");
          sql.append("  \"#Appointments canceled due to transportation?\" = "+leadDto.getAppointmentscanceledduetotransportation()+",");
          sql.append("  \"#Patients call the office more than once per week?\" = "+leadDto.getPatientscalltheofficemorethanonceperweek()+",");
          sql.append(" \"The most important thing to consider during agency reference?\" = '"+leadDto.getThemostimportantthingtoconsiderduringagencyreference()+"',");
          sql.append("  \"How can I make your job easier?\" = '"+leadDto.getHowcanImakeyourjobeasier()+"',");
          sql.append(" \"What is the best time for me to come and visit?\" = '"+leadDto.getWhatthebesttimeformetocomeandvisit()+"',");
          sql.append("  \"#Patients you see per day on an average?\" = "+leadDto.getPatientsyouseeperdayonanaverage()+",");
          sql.append("  \"How often do you use Home Health per month on average?\" = "+leadDto.getHowoftendoyouuseHomeHealthpermonthonaverage()+",");
          sql.append("  \"Do you want to give us a chance to show what we can do?\" = '"+leadDto.getDoyouwanttogiveusachancetoshowwhatwecando()+"', ");
          sql.append(" progress_note_last_modified_date = '"+leadDto.getProgressNoteLastModifiedDate()+"', ");
          sql.append("  office_hours = "+leadDto.getOfficeHours());
          sql.append(" Where user_id ="+leadDto.getUser_id());
          sql.append(" AND id = "+leadDto.getId());
          
    	  System.out.println(sql.toString());
 		  getJdbcTemplate(leadDto.getUser_id()).update(new PreparedStatementCreator() {
 				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
 					return con.prepareStatement(sql.toString());
   }
 });
 		return true;
  }
  public LeadDTO checkAccountExist(String id, String userId) {
	  String sql = " SELECT id from account where id ="+id;
	  LeadDTO isAvialable = getJdbcTemplate(userId).queryForObject(sql.toString(), new BeanPropertyRowMapper<LeadDTO>(LeadDTO.class));
		System.out.println("fetching id is : "+isAvialable.getId());
		return isAvialable;
		

	  
  }
}
  
