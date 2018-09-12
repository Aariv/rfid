package com.denali.rfid.utils;

import java.util.Map;
import java.util.Random;

import org.apache.camel.Exchange;
import org.elasticsearch.action.index.IndexRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.denali.rfid.dto.RfidDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author zentere
 *
 */
@Component
public class RFIDCommon {
	
	@Value("${rfid.index.name}")
	private String rfidIndexName;
	
	@Value("${rfid.index.type}")
	private String rfidIndexType;

	public void onProcessRfid(Exchange exchange) {
		String data = exchange.getIn().getBody(String.class);
		exchange.getIn().setBody(RFIDParserUtils.onParse(data));
	}
	
	public void onRfidToElasticsearchMap(Exchange exchange) {
		RfidDTO data = exchange.getIn().getBody(RfidDTO.class);
		ObjectMapper oMapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String, Object> rfid = oMapper.convertValue(data, Map.class);
		IndexRequest indexRequest = new IndexRequest(rfidIndexName, rfidIndexType, generateId(data));
		indexRequest.source(rfid);
		exchange.getIn().setBody(indexRequest);
		exchange.getIn().setHeader("indexName", rfidIndexName);
	
	}

	private String generateId(RfidDTO rfid) {
		return rfid.getReader() + "-" + rfid.getAntenna() + "-" + rfid.getEpc() + "-" + rfid.getCssRssi() + "-"
				+ generateRandomString();
	}

	private String generateRandomString() {
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		String generatedString = buffer.toString();
		return generatedString.substring(0, 4);
	}
}