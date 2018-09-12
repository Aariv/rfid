package com.denali.rfid.dto;

/**
 * @author zentere
 *
 */
public enum AlarmType {

	INTRUSION("intrusion"), TIME_ELAPSED("time elapsed");

	private String type;

	/**
	 * @param type
	 */
	private AlarmType(String type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}