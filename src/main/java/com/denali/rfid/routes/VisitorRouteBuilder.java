/**
 * 
 */
package com.denali.rfid.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.denali.rfid.utils.ReaderCommon;
import com.denali.rfid.utils.VisitorCommon;

/**
 * @author zentere
 *
 */
@Component
public class VisitorRouteBuilder extends RouteBuilder {

	@Value("${es.reader.search.endpoint}")
	private String elasticSearchEndpoint;
	
	@Value("${seda.stage2.endpoint}")
	private String sedaStage2Endpoint;
	
	@Value("${visitor.route.name}")
	private String visitorRouteName;
	
	@Value("${reader.route.endpoint}")
	private String readerRouteEndpoint;
	
	@Value("${visitor.gateway.route.endpoint}")
	private String visitorGatewayRouteEndpoint;
	
	@Override
	public void configure() throws Exception {
		onException(RuntimeException.class).handled(true).log("Error has occured");
		
		from(sedaStage2Endpoint)
			.routeId(visitorRouteName)
				.setProperty("RFID_DETAILS", body())
				.log("RFID Details from seda:stage2-> ${body}")
					.bean(ReaderCommon.class, "onReaderSearchRequest")
						.to(elasticSearchEndpoint)
					.log("Result for the reader search request ${body}")
					.bean(ReaderCommon.class, "onVerifyReader")
					.choice()
						.when(body().contains("NotFound"))
						.log("Get from odoo and update reader_idx")
						// Go to ReaderRouter.java
						.to(readerRouteEndpoint)
						.bean(VisitorCommon.class, "onPayloadForVisitorGateway")
						.to(visitorGatewayRouteEndpoint)
					.otherwise()
					.log("Given reader exists so procced to gatewat")
					.bean(VisitorCommon.class, "onPayloadForVisitorGateway")
					.to(visitorGatewayRouteEndpoint);
	}

}
