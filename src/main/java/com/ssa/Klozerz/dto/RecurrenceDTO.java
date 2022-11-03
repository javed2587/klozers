package com.ssa.Klozerz.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class RecurrenceDTO {

	String recurrenceMeetingDates;
	String recurrenceFlag;
	String recurrenceStartTime;
	String recurrenceEndTime;
	String recurrenceDuration;
	String recurrencePattern;
	/*Daily*/
	String recurrencePatternSub;
	String recurrenceDaysCount;
	/*Weekly*/
	String recurrenceWeeksCount;
	String recurrenceWeekDays;
	/*Monthly*/
	String recurrenceMonthType;
	String recurrenceMonthDate;
	String recurrenceMonthCount;
	/*===Not in Use===*/
	String recurrenceMonthlyDay;
	String recurrenceMonthlyName;
	String recurrenceMonthlyTheCount;
	/**/
	String recurrenceStartDate;
	String recurrenceEndDateOption;
	String recurrenceEndCount;
	String recurrenceEndDate;
	
	public String getRecurrenceMeetingDates() {
		return recurrenceMeetingDates;
	}
	public void setRecurrenceMeetingDates(String recurrenceMeetingDates) {
		this.recurrenceMeetingDates = recurrenceMeetingDates;
	}
	public String getRecurrenceFlag() {
		return recurrenceFlag;
	}
	public void setRecurrenceFlag(String recurrenceFlag) {
		this.recurrenceFlag = recurrenceFlag;
	}
	public String getRecurrenceStartTime() {
		return recurrenceStartTime;
	}
	public void setRecurrenceStartTime(String recurrenceStartTime) {
		this.recurrenceStartTime = recurrenceStartTime;
	}
	public String getRecurrenceEndTime() {
		return recurrenceEndTime;
	}
	public void setRecurrenceEndTime(String recurrenceEndTime) {
		this.recurrenceEndTime = recurrenceEndTime;
	}
	public String getRecurrenceDuration() {
		return recurrenceDuration;
	}
	public void setRecurrenceDuration(String recurrenceDuration) {
		this.recurrenceDuration = recurrenceDuration;
	}
	public String getRecurrencePattern() {
		return recurrencePattern;
	}
	public void setRecurrencePattern(String recurrencePattern) {
		this.recurrencePattern = recurrencePattern;
	}
	public String getRecurrencePatternSub() {
		return recurrencePatternSub;
	}
	public void setRecurrencePatternSub(String recurrencePatternSub) {
		this.recurrencePatternSub = recurrencePatternSub;
	}
	public String getRecurrenceDaysCount() {
		return recurrenceDaysCount;
	}
	public void setRecurrenceDaysCount(String recurrenceDaysCount) {
		this.recurrenceDaysCount = recurrenceDaysCount;
	}
	public String getRecurrenceWeeksCount() {
		return recurrenceWeeksCount;
	}
	public void setRecurrenceWeeksCount(String recurrenceWeeksCount) {
		this.recurrenceWeeksCount = recurrenceWeeksCount;
	}
	public String getRecurrenceWeekDays() {
		return recurrenceWeekDays;
	}
	public void setRecurrenceWeekDays(String recurrenceWeekDays) {
		this.recurrenceWeekDays = recurrenceWeekDays;
	}
	public String getRecurrenceMonthType() {
		return recurrenceMonthType;
	}
	public void setRecurrenceMonthType(String recurrenceMonthType) {
		this.recurrenceMonthType = recurrenceMonthType;
	}
	public String getRecurrenceMonthDate() {
		return recurrenceMonthDate;
	}
	public void setRecurrenceMonthDate(String recurrenceMonthDate) {
		this.recurrenceMonthDate = recurrenceMonthDate;
	}
	public String getRecurrenceMonthCount() {
		return recurrenceMonthCount;
	}
	public void setRecurrenceMonthCount(String recurrenceMonthCount) {
		this.recurrenceMonthCount = recurrenceMonthCount;
	}
	public String getRecurrenceMonthlyDay() {
		return recurrenceMonthlyDay;
	}
	public void setRecurrenceMonthlyDay(String recurrenceMonthlyDay) {
		this.recurrenceMonthlyDay = recurrenceMonthlyDay;
	}
	public String getRecurrenceMonthlyName() {
		return recurrenceMonthlyName;
	}
	public void setRecurrenceMonthlyName(String recurrenceMonthlyName) {
		this.recurrenceMonthlyName = recurrenceMonthlyName;
	}
	public String getRecurrenceMonthlyTheCount() {
		return recurrenceMonthlyTheCount;
	}
	public void setRecurrenceMonthlyTheCount(String recurrenceMonthlyTheCount) {
		this.recurrenceMonthlyTheCount = recurrenceMonthlyTheCount;
	}
	public String getRecurrenceStartDate() {
		return recurrenceStartDate;
	}
	public void setRecurrenceStartDate(String recurrenceStartDate) {
		this.recurrenceStartDate = recurrenceStartDate;
	}
	public String getRecurrenceEndDateOption() {
		return recurrenceEndDateOption;
	}
	public void setRecurrenceEndDateOption(String recurrenceEndDateOption) {
		this.recurrenceEndDateOption = recurrenceEndDateOption;
	}
	public String getRecurrenceEndCount() {
		return recurrenceEndCount;
	}
	public void setRecurrenceEndCount(String recurrenceEndCount) {
		this.recurrenceEndCount = recurrenceEndCount;
	}
	public String getRecurrenceEndDate() {
		return recurrenceEndDate;
	}
	public void setRecurrenceEndDate(String recurrenceEndDate) {
		this.recurrenceEndDate = recurrenceEndDate;
	}

}
