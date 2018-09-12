package com.denali.rfid.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.elasticsearch.ElasticsearchGenerationException;
import org.elasticsearch.action.bulk.BulkRequest;
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

import com.denali.rfid.dto.ReaderDTO;
import com.denali.rfid.dto.RfidDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author zentere
 *
 */
@Component
public class ReaderCommon {

	@Value("${reader.index.name}")
	private String readerIndexName;

	@Value("${reader.index.type}")
	private String readerIndexType;

	public void onReaderToElasticsearchMap(Exchange exchange) {
		try {
			String dataFromOdoo = exchange.getIn().getBody(String.class);
			JSONObject jsonObject = new JSONObject(dataFromOdoo);
			JSONArray readersArray = jsonObject.getJSONArray("data");
			List<ReaderDTO> bulkReaders = new ArrayList<>();
			BulkRequest bulkRequest = new BulkRequest();
			for (int i = 0; i < readersArray.length(); i++) {
				ObjectMapper oMapper = new ObjectMapper();
				JSONObject reader = readersArray.getJSONObject(i);
				ReaderDTO readerDto = oMapper.readValue(reader.toString(), ReaderDTO.class);
				bulkReaders.add(readerDto);
				@SuppressWarnings("unchecked")
				Map<String, Object> map = oMapper.convertValue(readerDto, Map.class);
				IndexRequest indexRequest = new IndexRequest(readerIndexName, readerIndexName);
				indexRequest.source(map);
				bulkRequest.add(indexRequest);
			}
			exchange.getIn().setBody(bulkRequest);
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
	
	public void onReaderSearchRequest(Exchange exchange) {
		RfidDTO data = exchange.getIn().getBody(RfidDTO.class);
		SearchRequest search = new SearchRequest();
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		boolQuery.should(QueryBuilders.termQuery("readerId", data.getReader()));
		sourceBuilder.query(boolQuery);
		search.source(sourceBuilder);
		exchange.getIn().setBody(search);
	}
	
	public void onVerifyReader(Exchange exchange) {
		String dataFromElastic = exchange.getIn().getBody(String.class);
		try {
			JSONObject fromEs = new JSONObject(dataFromElastic);
			JSONObject hits = fromEs.getJSONObject("hits");
			JSONArray hitsResult = hits.getJSONArray("hits");
			if (hitsResult != null && hitsResult.length() > 0) {
				JSONObject jsonObject = hitsResult.getJSONObject(0);
				JSONObject reader = jsonObject.getJSONObject("_source");
				ObjectMapper mapper = new ObjectMapper();
				ReaderDTO readerDto = mapper.readValue(reader.toString(), ReaderDTO.class);
				exchange.getIn().setBody(readerDto);
			} else {
				exchange.getIn().setBody("NotFound");
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
			exchange.getIn().setBody("NotFound");
		} catch (JsonMappingException e) {
			e.printStackTrace();
			exchange.getIn().setBody("NotFound");
		} catch (JSONException e) {
			e.printStackTrace();
			exchange.getIn().setBody("NotFound");
		} catch (IOException e) {
			e.printStackTrace();
			exchange.getIn().setBody("NotFound");
		}
	}
}