package com.ssa.Klozerz.dto;

public class AppointmentMailDTO {
 private String id;
 private String calendarId;
 private String uuid;
 private String status;
 private String rrule;
 private String mailTo;
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getCalendarId() {
	return calendarId;
}
public void setCalendarId(String calendarId) {
	this.calendarId = calendarId;
}
public String getUuid() {
	return uuid;
}
public void setUuid(String uuid) {
	this.uuid = uuid;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getRrule() {
	return rrule;
}
public void setRrule(String rrule) {
	this.rrule = rrule;
}
public String getMailTo() {
	return mailTo;
}
public void setMailTo(String mailTo) {
	this.mailTo = mailTo;
}
 

 
}
