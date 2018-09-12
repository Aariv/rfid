/**
 * 
 */
package com.denali.rfid.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.denali.rfid.utils.AlarmCommon;
import com.denali.rfid.utils.VisitorCommon;

/**
 * @author zentere
 *
 */
@Component
public class VisitorGatewayRouteBuilder extends RouteBuilder {

	@Value("${es.visitor.search.endpoint}")
	private String elasticSearchEndpoint;
	
	@Value("${es.index.endpoint}")
	private String elasticIndexEndpoint;
	
	@Value("${visitor.gateway.route.endpoint}")
	private String visitorGatewayRouteEndpoint;
	
	@Value("${visitor.route.gateway.name}")
	private String visitorGateWayRouterName;
	
	@Value("${alarm.route.endpoint}")
	private String alarmEndpoint;
	
	@Value("${odoo.rest.visitor.api}")
	private String visitorAPI;
	
	@Override
	public void configure() throws Exception {
		//onException(RuntimeException.class).handled(true).log("Error has occured");
		
		from(visitorGatewayRouteEndpoint)
			.routeId(visitorGateWayRouterName)
				.setProperty("RFID_DETAILS", body())
				.log("RFID Details from visitor -> ${body}")
					.bean(VisitorCommon.class, "onVisitorSearchRequest")
						.to(elasticSearchEndpoint)
					.bean(VisitorCommon.class, "onValidateVisitor")
						.log("Response from elastic after validation is ${body}")
						.choice()
							.when(body().contains("NotFound"))
							.log("Get from odoo and update visitor")
							.bean(VisitorCommon.class, "onPayloadForVisitorAPI")
							.log("Request Payload to visitor API ${body}")
							.setProperty("EPC", body())
							//.recipientList(simple(visitorAPI + "${body}"))
							.setHeader(Exchange.HTTP_METHOD, constant("GET"))
							.recipientList(simple(visitorAPI + "${body}"))
							.bean(VisitorCommon.class, "onConvertVisitorElasticMap")
							.to(elasticIndexEndpoint)
							.log("Visitor details updated success ${body}")
							.bean(VisitorCommon.class, "onVisitorSearch")
								.log("Request to elasticsearch ${body}")
								.to(elasticSearchEndpoint)
								.log("Response from elasticsearch ${body}")
								.bean(VisitorCommon.class, "onValidateVisitor")
								.log("Response from elastic after validation is ${body} to generate alarm")
								.choice()
									.when(body().contains("NotFound"))
										.log("Not found")
									.otherwise().log("Proceed with ${body} for Alarm")
									.bean(AlarmCommon.class, "onPayloadForAlarm")
									.to(alarmEndpoint)
								.endChoice()
							.endChoice()
						.otherwise()
							.log("Procceed with Visitor ${body} and Generate Alarm")
							// Go to AlarmRouter.java
							.bean(AlarmCommon.class, "onPayloadForAlarm")
							.to(alarmEndpoint);
	}

}
