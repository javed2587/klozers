package com.ssa.Klozerz.common;

public class JsonResponse {
	private Object data;
	private int errorCode;
	private String errorMessage;
	private long totalRecords;
	//private long recordSavedId;
	//private String successMessage;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

	/*
	 * public long getRecordSavedId() { return recordSavedId; }
	 * 
	 * public void setRecordSavedId(long recordSavedId) { this.recordSavedId =
	 * recordSavedId; }
	 * 
	 * public String getSuccessMessage() { return successMessage; }
	 * 
	 * public void setSuccessMessage(String successMessage) { this.successMessage =
	 * successMessage; }
	 */
}
