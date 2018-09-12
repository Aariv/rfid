package com.denali.rfid.dto;

/**
 * @author zentere
 *
 */
public class AlarmDTO {

	private String alarmType;

	private String readerId;

	private String status;

	private String lastUpdatedTime;

	private String createdDateTime;

	private String vrlId;

	private boolean alarmSent;

	public AlarmDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the alarmType
	 */
	public String getAlarmType() {
		return alarmType;
	}

	/**
	 * @param alarmType
	 *            the alarmType to set
	 */
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	/**
	 * @return the readerId
	 */
	public String getReaderId() {
		return readerId;
	}

	/**
	 * @param readerId
	 *            the readerId to set
	 */
	public void setReaderId(String readerId) {
		this.readerId = readerId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the vrlId
	 */
	public String getVrlId() {
		return vrlId;
	}

	/**
	 * @param vrlId
	 *            the vrlId to set
	 */
	public void setVrlId(String vrlId) {
		this.vrlId = vrlId;
	}

	/**
	 * @return the alarmSent
	 */
	public boolean isAlarmSent() {
		return alarmSent;
	}

	/**
	 * @param alarmSent
	 *            the alarmSent to set
	 */
	public void setAlarmSent(boolean alarmSent) {
		this.alarmSent = alarmSent;
	}

	/**
	 * @return the lastUpdatedTime
	 */
	public String getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime
	 *            the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(String lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * @return the createdDateTime
	 */
	public String getCreatedDateTime() {
		return createdDateTime;
	}

	/**
	 * @param createdDateTime
	 *            the createdDateTime to set
	 */
	public void setCreatedDateTime(String createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

}