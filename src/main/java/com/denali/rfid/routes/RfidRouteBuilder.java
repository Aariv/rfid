/**
 * 
 */
package com.denali.rfid.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.denali.rfid.camel.ListAggregationStrategy;
import com.denali.rfid.utils.RFIDCommon;

/**
 * @author zentere
 *
 */
@Component
public class RfidRouteBuilder extends RouteBuilder {

	@Value("${seda.stage1.endpoint}")
	private String sedaStage1Endpoint;
	
	@Value("${interval.time}")
	private String intervalTime;
	
	@Value("${es.bulk.index.endpoint}")
	private String elasticBulkIndexHost;
	
	@Value("${rfid.route.name}")
	private String rfidRouteName;
	
	@Autowired
	private ListAggregationStrategy listAggregation;

	@Override
	public void configure() throws Exception {
		from(sedaStage1Endpoint)
			.routeId(rfidRouteName)
			.log("Payload from seda stage1 -> ${body}")
			.choice()
				.when(body().isEqualTo(null))
					.log("Don't procced because payload is null")
			.otherwise()
				.bean(RFIDCommon.class, "onRfidToElasticsearchMap")
				.aggregate(constant(true), listAggregation)
				.completionInterval(3000)
				// makes sure the last batch will be processed before application shuts down:
				.forceCompletionOnStop()
				.to(elasticBulkIndexHost)
				.log("Uploaded documents on ${headers.indexName}: ${body.size()}");
				
	}

}
