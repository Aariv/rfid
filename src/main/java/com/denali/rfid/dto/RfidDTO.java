/**
 * 
 */
package com.denali.rfid.dto;

/**
 * @author zentere
 *
 */
public class RfidDTO {

	private String reader;

	private String antenna;

	private String epc;

	private String cssRssi;

	private String date;

	private String lastUpdatedTime;

	public RfidDTO() {
		// TODO Auto-generated constructor stub
	}

	public RfidDTO(String reader, String antenna, String epc, String date) {
		super();
		this.reader = reader;
		this.antenna = antenna;
		this.epc = epc;
		this.date = date;
	}

	public RfidDTO(String reader, String antenna, String epc, String cssRssi, String date) {
		super();
		this.reader = reader;
		this.antenna = antenna;
		this.epc = epc;
		this.cssRssi = cssRssi;
		this.date = date;
	}

	public RfidDTO(String reader, String antenna, String epc, String cssRssi, String date, String lastUpdatedTime) {
		super();
		this.reader = reader;
		this.antenna = antenna;
		this.epc = epc;
		this.cssRssi = cssRssi;
		this.date = date;
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * @return the reader
	 */
	public String getReader() {
		return reader;
	}

	/**
	 * @param reader
	 *            the reader to set
	 */
	public void setReader(String reader) {
		this.reader = reader;
	}

	/**
	 * @return the antenna
	 */
	public String getAntenna() {
		return antenna;
	}

	/**
	 * @param antenna
	 *            the antenna to set
	 */
	public void setAntenna(String antenna) {
		this.antenna = antenna;
	}

	/**
	 * @return the epc
	 */
	public String getEpc() {
		return epc;
	}

	/**
	 * @param epc
	 *            the epc to set
	 */
	public void setEpc(String epc) {
		this.epc = epc;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the cssRssi
	 */
	public String getCssRssi() {
		return cssRssi;
	}

	/**
	 * @param cssRssi
	 *            the cssRssi to set
	 */
	public void setCssRssi(String cssRssi) {
		this.cssRssi = cssRssi;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Rfid [reader=" + reader + ", antenna=" + antenna + ", epc=" + epc + ", date=" + date + "]";
	}

}
