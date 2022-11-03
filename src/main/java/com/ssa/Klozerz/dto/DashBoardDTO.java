package com.ssa.Klozerz.dto;

import java.util.ArrayList;

public class DashBoardDTO {

	   private ArrayList<CalendarAppointmentDTO> accountAppointments;
	   public ArrayList<CalendarAppointmentDTO> getAccountAppointments() {
		return accountAppointments;
	}
	public void setAccountAppointments(ArrayList<CalendarAppointmentDTO> accountAppointments) {
		this.accountAppointments = accountAppointments;
	}
	public ArrayList<CalendarAppointmentDTO> getLeadAppointments() {
		return leadAppointments;
	}
	public void setLeadAppointments(ArrayList<CalendarAppointmentDTO> leadAppointments) {
		this.leadAppointments = leadAppointments;
	}
	public ArrayList<CalendarAppointmentDTO> getPersonlaAppointmens() {
		return personlaAppointmens;
	}
	public void setPersonlaAppointmens(ArrayList<CalendarAppointmentDTO> personlaAppointmens) {
		this.personlaAppointmens = personlaAppointmens;
	}
	private ArrayList<CalendarAppointmentDTO> leadAppointments;
	   private ArrayList<CalendarAppointmentDTO> personlaAppointmens;
	   
	   
	

}
