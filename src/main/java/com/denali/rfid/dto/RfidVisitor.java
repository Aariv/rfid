/**
 * 
 */
package com.denali.rfid.dto;

/**
 * @author zentere
 *
 */
public class RfidVisitor {

	private RfidDTO rfidDTO;

	private VisitorDetailsDTO visitorDetailsDTO;

	public RfidVisitor() {
		// TODO Auto-generated constructor stub
	}

	public RfidVisitor(RfidDTO rfidDTO, VisitorDetailsDTO visitorDetailsDTO) {
		super();
		this.rfidDTO = rfidDTO;
		this.visitorDetailsDTO = visitorDetailsDTO;
	}

	/**
	 * @return the rfidDTO
	 */
	public RfidDTO getRfidDTO() {
		return rfidDTO;
	}

	/**
	 * @param rfidDTO
	 *            the rfidDTO to set
	 */
	public void setRfidDTO(RfidDTO rfidDTO) {
		this.rfidDTO = rfidDTO;
	}

	/**
	 * @return the visitorDetailsDTO
	 */
	public VisitorDetailsDTO getVisitorDetailsDTO() {
		return visitorDetailsDTO;
	}

	/**
	 * @param visitorDetailsDTO
	 *            the visitorDetailsDTO to set
	 */
	public void setVisitorDetailsDTO(VisitorDetailsDTO visitorDetailsDTO) {
		this.visitorDetailsDTO = visitorDetailsDTO;
	}

}
