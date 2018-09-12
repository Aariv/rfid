/**
 * 
 */
package com.denali.rfid.dto;

import java.util.List;

import com.denali.rfid.utils.DateUtils;

/**
 * @author zentere
 *
 */
public class ReaderDTO {

	private String readerId;

	private String name;

	private String description;

	private String latitude;

	private String longitude;

	private String status;

	private List<AntennaDTO> antenna;

	private Integer readerTableId;

	private String createdDateTime = DateUtils.dateFormattedWith24Hours();

	private String lastUpdatedTime = DateUtils.dateFormattedWith24Hours();;

	public ReaderDTO() {
		// TODO Auto-generated constructor stub
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
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
	 * @return the antenna
	 */
	public List<AntennaDTO> getAntenna() {
		return antenna;
	}

	/**
	 * @param antenna
	 *            the antenna to set
	 */
	public void setAntenna(List<AntennaDTO> antenna) {
		this.antenna = antenna;
	}

	/**
	 * @return the readerTableId
	 */
	public Integer getReaderTableId() {
		return readerTableId;
	}

	/**
	 * @param readerTableId
	 *            the readerTableId to set
	 */
	public void setReaderTableId(Integer readerTableId) {
		this.readerTableId = readerTableId;
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
