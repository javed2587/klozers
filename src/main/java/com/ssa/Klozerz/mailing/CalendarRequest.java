package com.ssa.Klozerz.mailing;


import java.util.UUID;

public class CalendarRequest {
    private String uid = /*"f523a6e3-c719-4e4b-9ad9-89b4b43e32c3";*/UUID.randomUUID().toString();
    private String toEmail;
    private String subject;
    private String body;
    //recurrence_start_time
    private String meetingStartTime;
    //recurrence_end_time
    private String meetingEndTime;
    //recurrence_startdate
    private String startDate; 
    //recurrence_enddate
    private String endDate; 
    //rule
    private String rrule;
    // recurrence_flag
    private String recurrence_flag;
    //address
    private String meeting_address;
    // email array
    private String attendesList[];
    //timezone
    private String zone;
    
    private CalendarRequest(Builder builder) {
        toEmail = builder.toEmail;
        subject = builder.subject;
        body = builder.body;
        meetingStartTime = builder.meetingStartTime;
        meetingEndTime = builder.meetingEndTime;
        startDate=builder.startDate;
        endDate=builder.endDate;
        rrule=builder.rrule;
        setRecurrence_flag(builder.recurrence_flag);
        setMeeting_address(builder.meeting_address);
        setAttendesList(builder.attendesList);
        zone=builder.zone;
    }
 /**/
    public void setUid(String uid) {
        this.uid= uid;
    }
 /**/   
    public String getUid() {
        return uid;
    }
 
    public String getToEmail() {
        return toEmail;
    }
 
    public String getSubject() {
        return subject;
    }
 
    public String getBody() {
        return body;
    }
 
    public String getMeetingStartTime() {
        return meetingStartTime;
    }
 
    public String getMeetingEndTime() {
        return meetingEndTime;
    }
 
    
    public String getStartDate() {
		return startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public String getRrule() {
		return rrule;
	}


	public void setRrule(String rrule) {
		this.rrule = rrule;
	}


	public String getRecurrence_flag() {
		return recurrence_flag;
	}


	public void setRecurrence_flag(String recurrence_flag) {
		this.recurrence_flag = recurrence_flag;
	}


	public String getMeeting_address() {
		return meeting_address;
	}


	public void setMeeting_address(String meeting_address) {
		this.meeting_address = meeting_address;
	}


	public String[] getAttendesList() {
		return attendesList;
	}


	public void setAttendesList(String attendesList[]) {
		this.attendesList = attendesList;
	}

	public String getZone() {
		return zone;
	}


	public void setZone(String zone) {
		this.zone = zone;
	}

	public static final class Builder {
        private String toEmail;
        private String subject;
        private String body;
        private String meetingStartTime;
        private String meetingEndTime;
        private String startDate; 
        private String endDate; 
        private String rrule;
        private String recurrence_flag;
        private String meeting_address;
        private String attendesList[];
       private String zone;
        
        public Builder() {
        }
        public Builder withStartDate(String val) {
        	startDate = val;
            return this;
        }
        public Builder withEndDate(String val) {
        	endDate = val;
            return this;
        }
        public Builder withToEmail(String val) {
            toEmail = val;
            return this;
        }
 
        public Builder withSubject(String val) {
            subject = val;
            return this;
        }
 
        public Builder withBody(String val) {
            body = val;
            return this;
        }
 
        public Builder withMeetingStartTime(String val) {
            meetingStartTime = val;
            return this;
        }
 
        public Builder withMeetingEndTime(String val) {
            meetingEndTime = val;
            return this;
        }
        
        public Builder withRRule(String val) {
            rrule = val;
            return this;
        }
        public Builder withFlag(String val) {
        	 recurrence_flag=val;
        	   return this;
        }
        public Builder withAddress(String val)
        {
        	   meeting_address=val;
        	   return this;
        }
        public Builder withAttendesList(String val[])
        {
        	  attendesList=val;
        	   return this;
        }
        public Builder withZone(String val)
        {
        	  	zone=val;
        	   return this;
        }
        
        public CalendarRequest build() {
            return new CalendarRequest(this);
        }
    }
}