/**
 * 
 */
package com.denali.rfid.dto;

import java.util.List;


/**
 * @author zentere
 *
 */
public class VisitorDetailsDTO {

	// Host
	private String inmateName;

	private String inmatePhoneNumber;

	private String inmateCompany;

	private String inmateEmail;

	// Visitor
	private String visitorCompany;

	private String visitorEmail;

	private String visitorName;

	private String visitorPhoneNumber;

	private String image;

	private String stage;

	private String dateTo;

	private String dateFrom;

	private String building;

	private String floor;

	private String purpose;

	private List<ReaderDTO> readers;

	private String epc;

	private String createdDateTime;

	private String lastUpdatedDateTime;

	public VisitorDetailsDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the inmateName
	 */
	public String getInmateName() {
		return inmateName;
	}

	/**
	 * @param inmateName
	 *            the inmateName to set
	 */
	public void setInmateName(String inmateName) {
		this.inmateName = inmateName;
	}

	/**
	 * @return the inmatePhoneNumber
	 */
	public String getInmatePhoneNumber() {
		return inmatePhoneNumber;
	}

	/**
	 * @param inmatePhoneNumber
	 *            the inmatePhoneNumber to set
	 */
	public void setInmatePhoneNumber(String inmatePhoneNumber) {
		this.inmatePhoneNumber = inmatePhoneNumber;
	}

	/**
	 * @return the inmateCompany
	 */
	public String getInmateCompany() {
		return inmateCompany;
	}

	/**
	 * @param inmateCompany
	 *            the inmateCompany to set
	 */
	public void setInmateCompany(String inmateCompany) {
		this.inmateCompany = inmateCompany;
	}

	/**
	 * @return the inmateEmail
	 */
	public String getInmateEmail() {
		return inmateEmail;
	}

	/**
	 * @param inmateEmail
	 *            the inmateEmail to set
	 */
	public void setInmateEmail(String inmateEmail) {
		this.inmateEmail = inmateEmail;
	}

	/**
	 * @return the visitorCompany
	 */
	public String getVisitorCompany() {
		return visitorCompany;
	}

	/**
	 * @param visitorCompany
	 *            the visitorCompany to set
	 */
	public void setVisitorCompany(String visitorCompany) {
		this.visitorCompany = visitorCompany;
	}

	/**
	 * @return the visitorEmail
	 */
	public String getVisitorEmail() {
		return visitorEmail;
	}

	/**
	 * @param visitorEmail
	 *            the visitorEmail to set
	 */
	public void setVisitorEmail(String visitorEmail) {
		this.visitorEmail = visitorEmail;
	}

	/**
	 * @return the visitorName
	 */
	public String getVisitorName() {
		return visitorName;
	}

	/**
	 * @param visitorName
	 *            the visitorName to set
	 */
	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}

	/**
	 * @return the visitorPhoneNumber
	 */
	public String getVisitorPhoneNumber() {
		return visitorPhoneNumber;
	}

	/**
	 * @param visitorPhoneNumber
	 *            the visitorPhoneNumber to set
	 */
	public void setVisitorPhoneNumber(String visitorPhoneNumber) {
		this.visitorPhoneNumber = visitorPhoneNumber;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the stage
	 */
	public String getStage() {
		return stage;
	}

	/**
	 * @param stage
	 *            the stage to set
	 */
	public void setStage(String stage) {
		this.stage = stage;
	}

	/**
	 * @return the dateTo
	 */
	public String getDateTo() {
		return dateTo;
	}

	/**
	 * @param dateTo
	 *            the dateTo to set
	 */
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * @return the dateFrom
	 */
	public String getDateFrom() {
		return dateFrom;
	}

	/**
	 * @param dateFrom
	 *            the dateFrom to set
	 */
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * @return the building
	 */
	public String getBuilding() {
		return building;
	}

	/**
	 * @param building
	 *            the building to set
	 */
	public void setBuilding(String building) {
		this.building = building;
	}

	/**
	 * @return the floor
	 */
	public String getFloor() {
		return floor;
	}

	/**
	 * @param floor
	 *            the floor to set
	 */
	public void setFloor(String floor) {
		this.floor = floor;
	}

	/**
	 * @return the purpose
	 */
	public String getPurpose() {
		return purpose;
	}

	/**
	 * @param purpose
	 *            the purpose to set
	 */
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	/**
	 * @return the readers
	 */
	public List<ReaderDTO> getReaders() {
		return readers;
	}

	/**
	 * @param readers
	 *            the readers to set
	 */
	public void setReaders(List<ReaderDTO> readers) {
		this.readers = readers;
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
	 * @return the lastUpdatedDateTime
	 */
	public String getLastUpdatedDateTime() {
		return lastUpdatedDateTime;
	}

	/**
	 * @param lastUpdatedDateTime
	 *            the lastUpdatedDateTime to set
	 */
	public void setLastUpdatedDateTime(String lastUpdatedDateTime) {
		this.lastUpdatedDateTime = lastUpdatedDateTime;
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
