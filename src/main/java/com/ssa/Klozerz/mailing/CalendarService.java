package com.ssa.Klozerz.mailing;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.mail.javamail.JavaMailSender;

import com.ssa.Klozerz.dto.CalendarAppointmentDTO;

public class CalendarService {

    private JavaMailSender mailSender;
    private Date currentDate=new Date();
    SimpleDateFormat currFormatDate=new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat currFormatTime=new SimpleDateFormat("HHmmss");
    
    public CalendarService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
 
    
    /**
	 * Method to create ICS file for recurrence mail, 
	 * 
	 * @return
	 */	
    public boolean sendCalendarInvite(String fromEmail, CalendarRequest calendarRequest) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
			mimeMessage.addHeaderLine("method=REQUEST");
		
        mimeMessage.addHeaderLine("charset=UTF-8");
        mimeMessage.addHeaderLine("component=VEVENT");
        mimeMessage.setFrom(new InternetAddress(fromEmail));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(calendarRequest.getToEmail()));
        mimeMessage.setSubject(calendarRequest.getSubject());
        StringBuilder builder = new StringBuilder();
        builder.append("BEGIN:VCALENDAR\n" +
//        	    "METHOD:CANCEL\n" +
                "METHOD:REQUEST\n" +
                "PRODID:Microsoft Exchange Server 2010\n" +
                "VERSION:2.0\n" +

                "BEGIN:VEVENT\n" +
                "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:" + calendarRequest.getToEmail() + "\n" +
                "ORGANIZER;CN=DannyCRM:MAILTO:" + fromEmail + "\n" +
                "DESCRIPTION;LANGUAGE=en-US:" + calendarRequest.getBody() + "\n" +
                "UID:"+calendarRequest.getUid()+"\n" +
                "SUMMARY;LANGUAGE=en-US:Meeting\n" +
               /* "METHOD:CANCEL\n" +*/
//                "STATUS:CANCELLED\n" +
					"DTSTART;TZID="+calendarRequest.getZone()+":"+ calendarRequest.getStartDate() +"T"+ calendarRequest.getMeetingStartTime() +"00\n" +
					"DTEND;TZID="+calendarRequest.getZone()+":"+ calendarRequest.getStartDate() +"T"+ calendarRequest.getMeetingEndTime() +"00\n" +
                "RRULE:"+calendarRequest.getRrule()  +
                "CLASS:PUBLIC\n" +
                "PRIORITY:5\n" +
                "DTSTAMP:20201211T105302Z\n" +
                "TRANSP:OPAQUE\n" +
                "STATUS:CONFIRMED\n" +
                "SEQUENCE:0\n" +
                "LOCATION;LANGUAGE=en-US:"+calendarRequest.getMeeting_address()+"\n" +

                "BEGIN:VALARM\n" +
                "DESCRIPTION:REMINDER\n" +
                "TRIGGER;RELATED=START:-PT15M\n" +
                "ACTION:DISPLAY\n" +
                "END:VALARM\n" +
                "END:VEVENT\n" +
                "END:VCALENDAR");
 
        MimeBodyPart messageBodyPart = new MimeBodyPart();
 
        messageBodyPart.setHeader("Content-Class", "urn:content-classes:calendarmessage");
        messageBodyPart.setHeader("Content-ID", "calendar_message");
        try {
			messageBodyPart.setDataHandler(new DataHandler(
			        new ByteArrayDataSource(builder.toString(), "text/calendar;method=REQUEST;name=\"invite.ics\"")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
        MimeMultipart multipart = new MimeMultipart();
 
        multipart.addBodyPart(messageBodyPart);
 
        mimeMessage.setContent(multipart);
 
        System.out.println(builder.toString());
 
        mailSender.send(mimeMessage);
        System.out.println("Calendar invite sent");
        return true;
        } catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
    
    
    /**
	 * Method to create ICS file for normal mail, 
	 * 
	 * @return
	 */	
    public boolean sendCalendarInviteWithOutRecc(String fromEmail, CalendarRequest calendarRequest) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
			mimeMessage.addHeaderLine("method=REQUEST");
		
        mimeMessage.addHeaderLine("charset=UTF-8");
        mimeMessage.addHeaderLine("component=VEVENT");
        mimeMessage.setFrom(new InternetAddress(fromEmail));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(calendarRequest.getToEmail()));
        mimeMessage.setSubject(calendarRequest.getSubject());
      //  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss");
        StringBuilder builder = new StringBuilder();
        builder.append("BEGIN:VCALENDAR\n" +
//        	    "METHOD:CANCEL\n" +
                "METHOD:REQUEST\n" +
                "PRODID:Microsoft Exchange Server 2010\n" +
                "VERSION:2.0\n" +
                 	
                
                "BEGIN:VEVENT\n" +
                "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:" + calendarRequest.getToEmail() + "\n" +
                "ORGANIZER;CN=DannyCRM:MAILTO:" + fromEmail + "\n" +
                "DESCRIPTION;LANGUAGE=en-US:" + calendarRequest.getBody() + "\n" +
                "UID:"+calendarRequest.getUid()+"\n" +
                "SUMMARY;LANGUAGE=en-US:Meeting\n" +
               /* "METHOD:CANCEL\n" +*/
//                "STATUS:CANCELLED\n" +
 "DTSTART;TZID="+calendarRequest.getZone()+":"+ calendarRequest.getStartDate() +"T"+ calendarRequest.getMeetingStartTime() +"00\n" +
 "DTEND;TZID="+calendarRequest.getZone()+":"+ calendarRequest.getStartDate() +"T"+ calendarRequest.getMeetingEndTime() +"00\n" +
 "RRULE:FREQ=DAILY;INTERVAL=1;COUNT=1" +   
			"CLASS:PUBLIC\n" +
                "PRIORITY:5\n" +
                "DTSTAMP:"+currFormatDate.format(currentDate)+"T"+currFormatTime.format(currentDate)+"Z\n" +

                "TRANSP:OPAQUE\n" +
                "STATUS:CONFIRMED\n" +
                "SEQUENCE:0\n" +
                "LOCATION;LANGUAGE=en-US:"+calendarRequest.getMeeting_address()+"\n" +
                "BEGIN:VALARM\n" +
                "DESCRIPTION:REMINDER\n" +
                "TRIGGER;RELATED=START:-PT15M\n" +
                "ACTION:DISPLAY\n" +
                "END:VALARM\n" +
                "END:VEVENT\n" +
                "END:VCALENDAR");
 
        MimeBodyPart messageBodyPart = new MimeBodyPart();
 
        messageBodyPart.setHeader("Content-Class", "urn:content-classes:calendarmessage");
        messageBodyPart.setHeader("Content-ID", "calendar_message");
        try {
			messageBodyPart.setDataHandler(new DataHandler(
			        new ByteArrayDataSource(builder.toString(), "text/calendar;method=REQUEST;name=\"invite.ics\"")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
        MimeMultipart multipart = new MimeMultipart();
 
        multipart.addBodyPart(messageBodyPart);
 
        mimeMessage.setContent(multipart);
 
        System.out.println(builder.toString());
 
        mailSender.send(mimeMessage);
        System.out.println("Calendar invite sent");
        return true;
        } catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
    
    
    /**
	 * Method to create ICS file for updated recurrence, single meeting in recurrence 
	 * 
	 * @return
	 */	
    public boolean editCalendarInviteForChild(String fromEmail, CalendarRequest calendarRequest,CalendarAppointmentDTO orgAppointment,List<CalendarAppointmentDTO> appointment) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
			mimeMessage.addHeaderLine("method=REQUEST");
		
        mimeMessage.addHeaderLine("charset=UTF-8");
        mimeMessage.addHeaderLine("component=VEVENT");
        mimeMessage.setFrom(new InternetAddress(fromEmail));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(calendarRequest.getToEmail()));
        mimeMessage.setSubject(calendarRequest.getSubject());
        SimpleDateFormat formatterDate = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat formatterDateOrg = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat formatterTimeOrg = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat formatterTime = new SimpleDateFormat("HHmm");
        SimpleDateFormat formatterDateTime = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        SimpleDateFormat formatterDateTimeDB = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        StringBuilder builder = new StringBuilder();
        try {
			builder.append("BEGIN:VCALENDAR\n" +
//        	    "METHOD:CANCEL\n" +
			        "METHOD:REQUEST\n" +
			        "PRODID:Microsoft Exchange Server 2010\n" +
			        "VERSION:2.0\n" +
			        
			        "BEGIN:VEVENT\n" +
			        "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:" + calendarRequest.getToEmail() + "\n" +
			        "ORGANIZER;CN=DannyCRM:MAILTO:" + fromEmail + "\n" +
			        "DESCRIPTION;LANGUAGE=en-US:" + calendarRequest.getBody() + "\n" +
			        "UID:"+calendarRequest.getUid()+"\n" +
//               "UID:10323bb2-9d2f-446c-8b9e-f13553f63331\n" +
			        "SUMMARY;LANGUAGE=en-US:Meeting\n" +
			       /* "METHOD:CANCEL\n" +*/
			       "DTSTART;TZID="+orgAppointment.getTimeZone()+":"+ formatterDate.format(formatterDateOrg.parse(orgAppointment.getRecurr().getRecurrenceStartDate()))  +"T"+ calendarRequest.getMeetingStartTime() +"00\n" +
			       "DTEND;TZID="+orgAppointment.getTimeZone()+":"+ formatterDate.format(formatterDateOrg.parse(orgAppointment.getRecurr().getRecurrenceStartDate())) +"T"+ calendarRequest.getMeetingEndTime() +"00\n" +
					"RRULE:"+calendarRequest.getRrule()  +//"\n"+ //RRULE:FREQ=WEEKLY;BYDAY=TU;INTERVAL=1;COUNT=1
				"CLASS:PUBLIC\n" +
			        "PRIORITY:5\n" +
			        "DTSTAMP:"+currFormatDate.format(currentDate)+"T"+currFormatTime.format(currentDate)+"Z\n" +
			        "TRANSP:OPAQUE\n" +
			        "STATUS:CONFIRMED\n" +
			        "SEQUENCE:0\n" +
					"LOCATION;LANGUAGE=en-US:"+calendarRequest.getMeeting_address()+"\n" +

			        "BEGIN:VALARM\n" +
			        "DESCRIPTION:REMINDER\n" +
			        "TRIGGER;RELATED=START:-PT15M\n" +
			        "ACTION:DISPLAY\n" +
			        "END:VALARM\n" +
			        "END:VEVENT\n" );
			        
			        /*already updated appointments in recurrence*/
	 		for(int i=0;i<appointment.size();i++)
	 	 		if(i>0)	
	 	    	{
	 	 			
	 	 			String meetingSDate;
	 				try {
	 					meetingSDate = formatterDate.format(formatterDateTimeDB.parse(appointment.get(i).getTaskDate()));
	 				
	 	 			builder.append(
	 	    				"BEGIN:VEVENT\n" + 
	 	    				        "DTSTART;TZID="+appointment.get(i).getTimeZone()+":"+ meetingSDate +"T"+ formatterTime.format(formatterDateTimeDB.parse(appointment.get(i).getTaskDate())) +"00\n" +
	 	    				        "DTEND;TZID="+appointment.get(i).getTimeZone()+":"+ meetingSDate +"T"+ formatterTime.format(formatterDateTimeDB.parse(appointment.get(i).getTaskEnd())) +"00\n" +

	 						"RECURRENCE-ID;TZID="+appointment.get(i).getTimeZone()+":"+ formatterDate.format(formatterDateTimeDB.parse(appointment.get(i).getRecurrenceId())) +"T"+ formatterTime.format(formatterDateTimeDB.parse(appointment.get(i).getRecurrenceId())) +"00\n" +
	 						"SUMMARY:Final Meeting \n"); 
	 						if(i==1)
	 	    				builder.append("TRANSP:OPAQUE \n"); 
	 						builder.append(
	 				        "UID:"+calendarRequest.getUid()+"\n" + 
	 	    				"END:VEVENT\n");
	 	    		
	 				} catch (ParseException e) {
	 					// TODO Auto-generated catch block
	 					e.printStackTrace();
	 				}
	 	    	}
	 		
//			       builder.append(this.updatedAppointmentEvent(appointment, calendarRequest.getUid()));
			       builder.append(""+
			        /* appointment to update */
			        "BEGIN:VEVENT\n" + 
//			        "DTSTART;TZID="+calendarRequest.getZone()+":"+ calendarRequest.getStartDate() +"T"+ formatterTime.format(formatterDateTime.parse(orgAppointment.getTaskDate())) +"00\n" +
//			        "DTEND;TZID="+calendarRequest.getZone()+":"+ calendarRequest.getStartDate() +"T"+ formatterTime.format(formatterDateTime.parse(orgAppointment.getTaskEnd())) +"00\n" +

			        "DTSTART;TZID="+calendarRequest.getZone()+":"+ calendarRequest.getStartDate() +"T"+ formatterTime.format(formatterDateTime.parse(appointment.get(0).getTaskDate())) +"00\n" +
			        "DTEND;TZID="+calendarRequest.getZone()+":"+ calendarRequest.getStartDate() +"T"+ formatterTime.format(formatterDateTime.parse(appointment.get(0).getTaskEnd())) +"00\n" +


					"DTSTAMP:"+currFormatDate.format(currentDate)+"T"+currFormatTime.format(currentDate)+"Z\n" +
					"LAST-MODIFIED:"+currFormatDate.format(currentDate)+"T"+currFormatTime.format(currentDate)+"Z\n" +

					"RECURRENCE-ID;TZID="+calendarRequest.getZone()+":"+ formatterDate.format(formatterDateTime.parse(orgAppointment.getRecurrenceId())) +"T"+ formatterTime.format(formatterDateTime.parse(orgAppointment.getRecurrenceId())) +"00\n" + 
					
//			        "RECURRENCE-ID;TZID="+calendarRequest.getZone()+":"+ formatterDate.format(formatterDateTime.parse(orgAppointment.getTaskDate())) +"T"+ formatterTime.format(formatterDateTime.parse(orgAppointment.getTaskDate())) +"00\n" + 
			        "SEQUENCE:0\n" + 
			        "SUMMARY:Final Meeting \n" + 
			        "TRANSP:OPAQUE\n" + 
			        "UID:"+calendarRequest.getUid()+"\n" + 
			        "END:VEVENT\n" + 
			        "END:VCALENDAR");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
 
        MimeBodyPart messageBodyPart = new MimeBodyPart();
 
        messageBodyPart.setHeader("Content-Class", "urn:content-classes:calendarmessage");
        messageBodyPart.setHeader("Content-ID", "calendar_message");
        try {
			messageBodyPart.setDataHandler(new DataHandler(
			        new ByteArrayDataSource(builder.toString(), "text/calendar;method=REQUEST;name=\"invite.ics\"")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
        MimeMultipart multipart = new MimeMultipart();
 
        multipart.addBodyPart(messageBodyPart);
 
        mimeMessage.setContent(multipart);
 
        System.out.println(builder.toString());
 
        mailSender.send(mimeMessage);
        System.out.println("Calendar invite sent");
        return true;
        } catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
    
    private String updatedAppointmentEvent(List<CalendarAppointmentDTO> appointment,String uuid) {
    	 StringBuilder builder = new StringBuilder();
         SimpleDateFormat formatterDate = new SimpleDateFormat("yyyyMMdd");
         SimpleDateFormat formatterTime = new SimpleDateFormat("HHmm");
         SimpleDateFormat formatterDateTime = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

 		for(int i=0;i>appointment.size();i++)
 		if(i>0)	
    	{
 			String meetingSDate;
			try {
				meetingSDate = formatterDate.format(formatterDateTime.parse(appointment.get(0).getTaskDate()));
			
 			builder.append(
    				"BEGIN:VEVENT\n" + 
    				        "DTSTART;TZID="+appointment.get(i).getTimeZone()+":"+ meetingSDate +"T"+ formatterTime.format(formatterDateTime.parse(appointment.get(0).getTaskDate())) +"00\n" +
    				        "DTEND;TZID="+appointment.get(i).getTimeZone()+":"+ meetingSDate +"T"+ formatterTime.format(formatterDateTime.parse(appointment.get(0).getTaskEnd())) +"00\n" +

					"RECURRENCE-ID;TZID="+appointment.get(i).getTimeZone()+":"+ formatterDate.format(formatterDateTime.parse(appointment.get(i).getRecurrenceId())) +"T"+ formatterTime.format(formatterDateTime.parse(appointment.get(i).getRecurrenceId())) +"00\n" +
					"SUMMARY:Final Meeting \n" + 
    				"TRANSP:OPAQUE \n" + 
			        "UID:"+uuid+"\n" + 
    				"END:VEVENT");
    		
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	return builder.toString();
    }

    /**
	 * Method to create ICS file for updated recurrence mail, all upcoming appointments 
	 * 
	 * @return
	 */	
    public boolean updateAllCalendarInvite(String fromEmail, CalendarRequest calendarRequest,CalendarAppointmentDTO app) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
			mimeMessage.addHeaderLine("method=REQUEST");
		
        mimeMessage.addHeaderLine("charset=UTF-8");
        mimeMessage.addHeaderLine("component=VEVENT");
        mimeMessage.setFrom(new InternetAddress(fromEmail));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(calendarRequest.getToEmail()));
        mimeMessage.setSubject(calendarRequest.getSubject());
        StringBuilder builder = new StringBuilder();
        
        /**/
        SimpleDateFormat formatterDate = new SimpleDateFormat("yyyyMMdd");
//        SimpleDateFormat formatterDateOrg = new SimpleDateFormat("MM/dd/yyyy");
//        SimpleDateFormat formatterTimeOrg = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat formatterTime = new SimpleDateFormat("HHmm");
        SimpleDateFormat formatterDateTime = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        SimpleDateFormat formatterDateTimeDB = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        /**/
        
        try {
			builder.append("BEGIN:VCALENDAR\n" +
//        	    "METHOD:CANCEL\n" +
			        "METHOD:REQUEST\n" +
			        "PRODID:Microsoft Exchange Server 2010\n" +
			        "VERSION:2.0\n" +

			        "BEGIN:VEVENT\n" +
			        "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:" + calendarRequest.getToEmail() + "\n" +
			        "ORGANIZER;CN=DannyCRM:MAILTO:" + fromEmail + "\n" +
			        "DESCRIPTION;LANGUAGE=en-US:" + calendarRequest.getBody() + "\n" +
			        "UID:"+calendarRequest.getUid()+"\n" +
			        "SUMMARY;LANGUAGE=en-US:Meeting-Update\n" +
			       /* "METHOD:CANCEL\n" +*/
//                "STATUS:CANCELLED\n" +
//                "DTSTART:"+ calendarRequest.getStartDate() +"T"+ calendarRequest.getMeetingStartTime() +"00Z\n" +
//                "DTEND:"+ calendarRequest.getEndDate() +"T"+ calendarRequest.getMeetingEndTime() +"00Z\n" +
//					"DTSTART;TZID="+calendarRequest.getZone()+":"+ calendarRequest.getStartDate() +"T"+ formatterTime.format(formatterDateTime.parse(app.getTaskDate())) +"00\n" +
//					"DTEND;TZID="+calendarRequest.getZone()+":"+ calendarRequest.getStartDate() +"T"+ formatterTime.format(formatterDateTime.parse(app.getTaskEnd())) +"00\n" +
			
	"DTSTART;TZID="+calendarRequest.getZone()+":"+ formatterDate.format(formatterDateTimeDB.parse(app.getRecurrenceId())) +"T"+ formatterTime.format(formatterDateTime.parse(app.getTaskDate())) +"00\n" +
	"DTEND;TZID="+calendarRequest.getZone()+":"+ formatterDate.format(formatterDateTimeDB.parse(app.getRecurrenceId())) +"T"+ formatterTime.format(formatterDateTime.parse(app.getTaskEnd())) +"00\n" +


			        "RRULE:"+calendarRequest.getRrule()  +
			        "CLASS:PUBLIC\n" +
			        "PRIORITY:5\n" +
			        "DTSTAMP:"+currFormatDate.format(currentDate)+"T"+currFormatTime.format(currentDate)+"Z\n" +
			        "TRANSP:OPAQUE\n" +
			        "STATUS:CONFIRMED\n" +
			        "SEQUENCE:0\n" +
//                "LOCATION;LANGUAGE=en-US:21800 Haggerty Road, Northville, MI, USA\n" +
					"LOCATION;LANGUAGE=en-US:"+calendarRequest.getMeeting_address()+"\n" +
			        "BEGIN:VALARM\n" +
			        "DESCRIPTION:REMINDER\n" +
			        "TRIGGER;RELATED=START:-PT15M\n" +
			        "ACTION:DISPLAY\n" +
			        "END:VALARM\n" +
			        "END:VEVENT\n" +
			        "END:VCALENDAR");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
 
        MimeBodyPart messageBodyPart = new MimeBodyPart();
 
        messageBodyPart.setHeader("Content-Class", "urn:content-classes:calendarmessage");
        messageBodyPart.setHeader("Content-ID", "calendar_message");
        try {
			messageBodyPart.setDataHandler(new DataHandler(
			        new ByteArrayDataSource(builder.toString(), "text/calendar;method=REQUEST;name=\"invite.ics\"")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
        MimeMultipart multipart = new MimeMultipart();
 
        multipart.addBodyPart(messageBodyPart);
 
        mimeMessage.setContent(multipart);
 
        System.out.println(builder.toString());
 
        mailSender.send(mimeMessage);
        System.out.println("Calendar invite sent");
        return true;
        } catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
    
    /**/
    
//    public boolean sendTestCalendarInvite(String fromEmail, CalendarRequest calendarRequest) {
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        try {
//			mimeMessage.addHeaderLine("method=REQUEST");
//		
//        mimeMessage.addHeaderLine("charset=UTF-8");
//        mimeMessage.addHeaderLine("component=VEVENT");
//        mimeMessage.setFrom(new InternetAddress(fromEmail));
//        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(calendarRequest.getToEmail()));
//        mimeMessage.setSubject(calendarRequest.getSubject());
//        StringBuilder builder = new StringBuilder();
//        builder.append("BEGIN:VCALENDAR\n" +
//                "METHOD:REQUEST\n" +
//                "PRODID:Microsoft Exchange Server 2010\n" +
//                "VERSION:2.0\n" +
//               
//                "BEGIN:VEVENT\n" +
//                "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:" + calendarRequest.getToEmail() + "\n" +
//                "ORGANIZER;CN=DannyCRM:MAILTO:" + fromEmail + "\n" +
//                "DESCRIPTION;LANGUAGE=en-US:" + calendarRequest.getBody() + "\n" +
////                "UID:"+calendarRequest.getUid()+"\n" +
//               "UID:10323bb2-9d2f-446c-8b9e-f13553f63331\n" +
//                "SUMMARY;LANGUAGE=en-US:Meeting\n" +
//               /* "METHOD:CANCEL\n" +*/
////                "STATUS:CANCELLED\n" +
//               "DTSTART;TZID="+calendarRequest.getZone()+":"+ calendarRequest.getStartDate() +"T"+ calendarRequest.getMeetingStartTime() +"00\n" +
//               "DTEND;TZID="+calendarRequest.getZone()+":"+ calendarRequest.getStartDate() +"T"+ calendarRequest.getMeetingEndTime() +"00\n" +
////            "DTEND;TZID="+calendarRequest.getZone()+":"+ calendarRequest.getEndDate() +"T"+ calendarRequest.getMeetingEndTime() +"00Z\n" + 
//               //   "RRULE:FREQ=WEEKLY;BYDAY=MO,TU,WE,TH,FR;INTERVAL=1;COUNT=5"  +
////				"RRULE:"+calendarRequest.getRrule()  +//"\n"+ //RRULE:FREQ=WEEKLY;BYDAY=TU;INTERVAL=1;COUNT=1
//			//"RRULE:FREQ=WEEKLY;INTERVAL=2;COUNT=8;WKST=SU;BYDAY=TU,TH" +   
//"RRULE:FREQ=WEEKLY;BYDAY=SA;INTERVAL=1;UNTIL=20211016T235900Z"+
//			"CLASS:PUBLIC\n" +
//                "PRIORITY:5\n" +
//                "DTSTAMP:"+currFormatDate.format(currentDate)+"T"+currFormatTime.format(currentDate)+"Z\n" +
//
////                "DTSTAMP:20201211T105302Z\n" +
//                "TRANSP:OPAQUE\n" +
//                "STATUS:CONFIRMED\n" +
//                "SEQUENCE:0\n" +
//               // "SEQUENCE:$sequenceNumber\n" +
//                
//                "LOCATION;LANGUAGE=en-US:21800 Haggerty Road, Northville, MI, USA\n" +
////				"LOCATION;LANGUAGE=en-US:"+calendarRequest.getMeeting_address()+"\n" +
//
//                "BEGIN:VALARM\n" +
//                "DESCRIPTION:REMINDER\n" +
//                "TRIGGER;RELATED=START:-PT15M\n" +
//                "ACTION:DISPLAY\n" +
//                "END:VALARM\n" +
//                "END:VEVENT\n" +
//                "END:VCALENDAR");
// 
//        MimeBodyPart messageBodyPart = new MimeBodyPart();
// 
//        messageBodyPart.setHeader("Content-Class", "urn:content-classes:calendarmessage");
//        messageBodyPart.setHeader("Content-ID", "calendar_message");
//        try {
//			messageBodyPart.setDataHandler(new DataHandler(
//			        new ByteArrayDataSource(builder.toString(), "text/calendar;method=REQUEST;name=\"invite.ics\"")));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
// 
//        MimeMultipart multipart = new MimeMultipart();
// 
//        multipart.addBodyPart(messageBodyPart);
// 
//        mimeMessage.setContent(multipart);
// 
//        System.out.println(builder.toString());
// 
//        mailSender.send(mimeMessage);
//        System.out.println("Calendar invite sent");
//        return true;
//        } catch (MessagingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return false;
//		}
//    }
}
