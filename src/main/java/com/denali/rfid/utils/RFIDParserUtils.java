/**
 * 
 */
package com.denali.rfid.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import com.denali.rfid.dto.RfidDTO;

/**
 * @author zentere
 *
 */
public class RFIDParserUtils {

	public static RfidDTO onParse(String hexData) {
		String dataWithoutSpace = hexData.replaceAll(" ", "");
		try {
			// Contains A013
			if (dataWithoutSpace.startsWith("A013")) {
				// For valid data
				String header = dataWithoutSpace.substring(0, 4);
				String readerId = dataWithoutSpace.substring(4, 6);
				String command = dataWithoutSpace.substring(6, 8);
				String antenna = dataWithoutSpace.substring(8, 10);
				String pc = dataWithoutSpace.substring(10, 12);
				// String antena = dataWithoutSpace.substring(12, 14);
				String epc = dataWithoutSpace.substring(14, 34);
				String rssi = dataWithoutSpace.substring(37, 41);
				String antennaID = "";

				if (HexadecimalToBinary.isHexadecimalNumber(antenna)) {
					String binary = HexadecimalToBinary.getBinaryFromHexadecimalNumber(antenna);
					String substring = binary.substring(Math.max(binary.length() - 2, 0));
					if (substring.equalsIgnoreCase("00")) {
						antennaID = "ANT1";
					} else if (substring.equalsIgnoreCase("01")) {
						antennaID = "ANT2";
					} else if (substring.equalsIgnoreCase("10")) {
						antennaID = "ANT3";
					} else if (substring.equalsIgnoreCase("11")) {
						antennaID = "ANT4";
					}
				}
				List<String> completeData = new ArrayList<>();
				completeData.add(header);
				completeData.add(readerId);
				completeData.add(command);
				completeData.add(antenna);
				completeData.add("(" + antennaID + ")");
				completeData.add(pc);
				completeData.add(epc);
				String formattedData = new String(Hex.decodeHex(epc));
				completeData.add("(" + formattedData + ")");
				completeData.add(rssi);
				// Check EPC data is correct
				if (!isStringContainsSpecialChars(formattedData)) {
					// logger.info("ByteArray to Text : {} ", StringUtils.join(completeData, '|'));
					// Check the reader index else create reader index

					// new String(Hex.decodeHex("54303037333130303034"))
					RfidDTO rfidDTO = new RfidDTO(readerId, antennaID, new String(Hex.decodeHex(epc)), rssi,
							DateUtils.dateFormattedWith24Hours());
					return rfidDTO;
				}
			}
		} catch (StringIndexOutOfBoundsException | DecoderException e1) {
			System.out.println("Error in Converting Byte Array to Text");
		}
		return null;
	}

	private static boolean isStringContainsSpecialChars(String str) {
		Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
		Matcher matcher = pattern.matcher(str);
		if (!matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}
}
