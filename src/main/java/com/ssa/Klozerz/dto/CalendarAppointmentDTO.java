package com.ssa.Klozerz.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CalendarAppointmentDTO {

	private long id;
	private Long moduleId;
	
	//@NotNull(message="UserID cannot be missing or empty")
	private Long userId;
	
//    private long accountId;
//    private long personalId;
    
    private String moduleName;
	private String rating;
	private String facilityName;
	private String subject;
	private String archivedStatus;
	private String taskNote;
	private String status;
	private String periority;
	private String gps;
	private String createdBy;
	private String modefiedBy;
	private String archivedBy;
	
	private String street;
	private String mainFax;
	private String state;
	private String zipCode;
	private String city;
	
	private Date archivedDate;
	private Date modefiedDate;
	private Date createdDate;
	private String taskDate;
	private String taskEnd;
	
	/**/
//	String moduleName;
	String userName;
	String title;
	String attendeesEmail;
	String address;
	String timeZone;
	String profileZone;
//	String recurrenceMeetingDates;
//	String recurrenceFlag;
//	String recurrenceStartTime;
//	String recurrenceEndTime;
//	String recurrenceDuration;
//	String recurrencePattern;
//	/*Daily*/
//	String recurrencePatternSub;
//	String recurrenceDaysCount;
//	/*Weekly*/
//	String recurrenceWeeksCount;
//	String recurrenceWeekDays;
//	/*Monthly*/
//	String recurrenceMonthType;
//	String recurrenceMonthDate;
//	String recurrenceMonthCount;
//	/*===Not in Use===*/
//	String recurrenceMonthlyDay;
//	String recurrenceMonthlyName;
//	String recurrenceMonthlyTheCount;
//	/**/
//	String recurrenceStartDate;
//	String recurrenceEndDateOption;
//	String recurrenceEndCount;
//	String recurrenceEndDate;
	RecurrenceDTO recurr;
	JSONObject recurrenceString;
//	String endTime;
	String orgTaskDate;
	String orgTaskEnd;
	String parentId;
	boolean updateAll;
	String recurrenceId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getArchivedStatus() {
		return archivedStatus;
	}
	public void setArchivedStatus(String archivedStatus) {
		this.archivedStatus = archivedStatus;
	}
	public String getTaskNote() {
		return taskNote;
	}
	public void setTaskNote(String taskNote) {
		this.taskNote = taskNote;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPeriority() {
		return periority;
	}
	public void setPeriority(String periority) {
		this.periority = periority;
	}
	public String getGps() {
		return gps;
	}
	public void setGps(String gps) {
		this.gps = gps;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModefiedBy() {
		return modefiedBy;
	}
	public void setModefiedBy(String modefiedBy) {
		this.modefiedBy = modefiedBy;
	}
	public String getArchivedBy() {
		return archivedBy;
	}
	public void setArchivedBy(String archivedBy) {
		this.archivedBy = archivedBy;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getMainFax() {
		return mainFax;
	}
	public void setMainFax(String mainFax) {
		this.mainFax = mainFax;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Date getArchivedDate() {
		return archivedDate;
	}
	public void setArchivedDate(Date archivedDate) {
		this.archivedDate = archivedDate;
	}
	public Date getModefiedDate() {
		return modefiedDate;
	}
	public void setModefiedDate(Date modefiedDate) {
		this.modefiedDate = modefiedDate;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getTaskDate() {
		return taskDate;
	}
	public void setTaskDate(String taskDate) {
		this.taskDate = taskDate;
	}
	public String getTaskEnd() {
		return taskEnd;
	}
	public void setTaskEnd(String taskEnd) {
		this.taskEnd = taskEnd;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAttendeesEmail() {
		return attendeesEmail;
	}
	public void setAttendeesEmail(String attendeesEmail) {
		this.attendeesEmail = attendeesEmail;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public String getProfileZone() {
		return profileZone;
	}
	public void setProfileZone(String profileZone) {
		this.profileZone = profileZone;
	}
//	public String getEndTime() {
//		return endTime;
//	}
//	public void setEndTime(String endTime) {
//		this.endTime = endTime;
//	}
	public String getOrgTaskDate() {
		return orgTaskDate;
	}
	public void setOrgTaskDate(String orgTaskDate) {
		this.orgTaskDate = orgTaskDate;
	}
	public String getOrgTaskEnd() {
		return orgTaskEnd;
	}
	public void setOrgTaskEnd(String orgTaskEnd) {
		this.orgTaskEnd = orgTaskEnd;
	}
	public RecurrenceDTO getRecurr() {
		return recurr;
	}
	public void setRecurr(RecurrenceDTO recurr) {
		this.recurr = recurr;
	}
	public JSONObject getRecurrenceString() {
		return recurrenceString;
	}
	public void setRecurrenceString(JSONObject recurrenceString) {
		this.recurrenceString = recurrenceString;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public boolean isUpdateAll() {
		return updateAll;
	}
	public void setUpdateAll(boolean updateAll) {
		this.updateAll = updateAll;
	}
	public String getRecurrenceId() {
		return recurrenceId;
	}
	public void setRecurrenceId(String recurrenceId) {
		this.recurrenceId = recurrenceId;
	}
	
	
		
}
