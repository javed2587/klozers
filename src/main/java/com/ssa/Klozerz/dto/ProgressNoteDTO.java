package com.ssa.Klozerz.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ProgressNoteDTO {

	 String id;
	 String moduleId;
	 String userId;
	 String subject;
	 String visitNote;
	 String noteDate;
	 String created_by;
	 String modifiedby;
	 String archived_by;
	 String archive_status;
	 String gps;
	 String faciltyName;
	 String dateTimep;
	 String noteTime;
	 String moduleSuffix;
   	 String moduleName;
	
	 String personalVisit;
	 String userName;
	 
	 Long created_date_count;
     Long modified_date_count;
     
	 LocalDateTime modified_date;
	 //LocalDateTime archivedDate;
	 LocalDateTime created_date;
	 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getVisitNote() {
		return visitNote;
	}
	public void setVisitNote(String visitNote) {
		this.visitNote = visitNote;
	}
//	public String getMailingCity() {
//		return mailingCity;
//	}
//	public void setMailingCity(String mailingCity) {
//		this.mailingCity = mailingCity;
//	}
//	public String getReferral() {
//		return referral;
//	}
//	public void setReferral(String referral) {
//		this.referral = referral;
//	}
	/*
	 * public LocalDateTime getArchivedDate() { return archivedDate; } public void
	 * setArchivedDate(LocalDateTime archivedDate) { this.archivedDate =
	 * archivedDate; }
	 */
	public String getNoteDate() {
		return this.noteDate;
	}
	public void setNoteDate(String noteDate) {
		this.noteDate = noteDate;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public LocalDateTime getCreated_date() {
		return created_date;
	}
	public void setCreated_date(LocalDateTime created_date) {
		this.created_date = created_date;
		this.created_date_count=this.created_date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
				//Date.from(this.created_date.atZone(ZoneId.systemDefault()).toInstant());
	}
	public String getModifiedby() {
		return modifiedby;
	}
	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}
	public LocalDateTime getModified_date() {
		return modified_date;
	}
	public void setModified_date(LocalDateTime modified_date) {
		this.modified_date = modified_date;
		this.modified_date_count=this.modified_date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
//				Date.from(this.modified_date.atZone(ZoneId.systemDefault()).toInstant());
	}
	public String getArchived_by() {
		return archived_by;
	}
	public void setArchived_by(String archived_by) {
		this.archived_by = archived_by;
	}
	public String getArchive_status() {
		return archive_status;
	}
	public void setArchive_status(String archive_status) {
		this.archive_status = archive_status;
	}
	public String getGps() {
		return gps;
	}
	public void setGps(String gps) {
		this.gps = gps;
	}
	public String getFaciltyName() {
		return faciltyName;
	}
	public void setFaciltyName(String faciltyName) {
		this.faciltyName = faciltyName;
	}
	public String getDateTimep() {
		return dateTimep;
	}
	public void setDateTimep(String dateTimep) {
		this.dateTimep = dateTimep;
		
		/**/
		try {
            String act_date = "";
            if (this.dateTimep.contains("00:00:00")) {
                final String[] datearr = this.dateTimep.split(" ");
                final String[] temprr = datearr[0].split("-");
                act_date = String.valueOf(temprr[0]) + "-" + temprr[2] + "-" + temprr[1];
                this.noteDate = String.valueOf(temprr[1]) + "-" + temprr[1] + "-" + temprr[0];
                this.setNoteDate(act_date);
                try {
                    this.setNoteTime(this.getNoteTime());
                }
                catch (Exception e3) {
                    this.setNoteTime("");
                }
            }
            else {
                final String[] datearr = this.getDateTimep().split(" ");
                this.setNoteDate(datearr[0]);
                try {
                    this.setNoteTime(this.getNoteTime().replace("null", ""));
                }
                catch (Exception e4) {
                    this.setNoteTime("");
                }
            }
        }
        catch (Exception e2) {
            this.setNoteDate("");
            this.setNoteTime("");
        }
	}
	public String getNoteTime() {
		return this.noteTime;
	}
	public void setNoteTime(String noteTime) {
		this.noteTime = noteTime;
	}
	 public String getModuleSuffix() {
		return moduleSuffix;
	}
	public void setModuleSuffix(String moduleSuffix) {
		this.moduleSuffix = moduleSuffix;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Long getCreated_date_count() {
		return created_date_count;
	}
	public void setCreated_date_count(Long created_date_count) {
		this.created_date_count = created_date_count;
	}
	public Long getModified_date_count() {
		return modified_date_count;
	}
	public void setModified_date_count(Long modified_date_count) {
		this.modified_date_count = modified_date_count;
	}
	 
	public String getPersonalVisit() {
		return personalVisit;
	}
	public void setPersonalVisit(String personalVisit) {
		this.personalVisit = personalVisit;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	 
	 
}
