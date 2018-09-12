package com.denali.rfid.utils;

import java.io.IOException;
import java.util.Map;

import org.apache.camel.Exchange;
import org.elasticsearch.ElasticsearchGenerationException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.denali.rfid.dto.RfidDTO;
import com.denali.rfid.dto.VisitorDetailsDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author zentere
 *
 */
@Component
public class VisitorCommon {
	
	@Value("${vrl.index.name}")
	private String visitorIndexName;
	
	@Value("${vrl.index.type}")
	private String visitorIndexType;

	/**
	 * 
	 * @param exchange
	 * 
	 * Get details by epc from vrl_idx
	 */
	public void onVisitorSearchRequest(Exchange exchange) {
		RfidDTO data = (RfidDTO) exchange.getProperty("RFID_DETAILS");
		SearchRequest search = new SearchRequest();
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		boolQuery.should(QueryBuilders.termQuery("_id", data.getEpc()));
		sourceBuilder.query(boolQuery);
		search.source(sourceBuilder);
		exchange.getIn().setBody(search);
	}
	
	public void onVisitorSearch(Exchange exchange) {
		String epc = exchange.getIn().getBody(String.class);
		SearchRequest search = new SearchRequest();
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		boolQuery.should(QueryBuilders.termQuery("_id", epc));
		sourceBuilder.query(boolQuery);
		search.source(sourceBuilder);
		exchange.getIn().setBody(search);
	}
	
	public void onPayloadForVisitorGateway(Exchange exchange) {
		RfidDTO rfidDTO = (RfidDTO) exchange.getProperty("RFID_DETAILS");
		exchange.getIn().setBody(rfidDTO);
	}
	
	public void onPayloadForVisitorAPI(Exchange exchange) {
		RfidDTO rfidDTO = (RfidDTO) exchange.getProperty("RFID_DETAILS");
		exchange.getIn().setBody(rfidDTO.getEpc());
	}
	
	public void onValidateVisitor(Exchange exchange) {
		String dataFromElastic = exchange.getIn().getBody(String.class);
		try {
			JSONObject fromEs = new JSONObject(dataFromElastic);
			JSONObject hits = fromEs.getJSONObject("hits");
			JSONArray hitsResult = hits.getJSONArray("hits");
			if (hitsResult != null && hitsResult.length() > 0) {
				JSONObject jsonObject = hitsResult.getJSONObject(0);
				JSONObject visitor = jsonObject.getJSONObject("_source");
				ObjectMapper mapper = new ObjectMapper();
				VisitorDetailsDTO visitorDto = mapper.readValue(visitor.toString(), VisitorDetailsDTO.class);
				exchange.getIn().setBody(visitorDto);
			} else {
				exchange.getIn().setBody("NotFound");
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void onConvertVisitorElasticMap(Exchange exchange) {
		try {
			String dataFromOdoo = exchange.getIn().getBody(String.class);
			Object epcData = exchange.getProperty("EPC");
			JSONObject jsonObject = new JSONObject(dataFromOdoo);
			Boolean result = jsonObject.getBoolean("result");
			if(result) {
				String epc = (String) epcData;
				JSONObject visitor = jsonObject.getJSONObject("data");
				ObjectMapper oMapper = new ObjectMapper();
				VisitorDetailsDTO readerDto = oMapper.readValue(visitor.toString(), VisitorDetailsDTO.class);
				@SuppressWarnings("unchecked")
				Map<String, Object> map = oMapper.convertValue(readerDto, Map.class);
				IndexRequest indexRequest = new IndexRequest(visitorIndexName, visitorIndexType, epc);
				indexRequest.source(map);
				exchange.getIn().setBody(indexRequest);
			} else {
				exchange.getIn().setBody(null);
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (ElasticsearchGenerationException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}