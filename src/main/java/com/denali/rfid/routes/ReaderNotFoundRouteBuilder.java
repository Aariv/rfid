/**
 * 
 */
package com.denali.rfid.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.denali.rfid.dto.AlarmDTO;
import com.denali.rfid.utils.AlarmCommonUtils;

/**
 * @author zentere
 *
 */
@Component
public class ReaderNotFoundRouteBuilder extends RouteBuilder {

	@Value("${alarm.route.name}")
	private String alarmRouterName;
	
	@Value("${alarm.route.endpoint}")
	private String alarmEndpoint;
	
	@Value("${es.alarm.search.endpoint}")
	private String alarmSearchIndexEndpoint;
	
	@Value("${es.index.endpoint}")
	private String elasticIndexEndpoint;
	
	@Value("${mqtt.endpoint}")
	private String mqttEndpoint;
	
	@Override
	public void configure() throws Exception {
		
		onException(RuntimeException.class).handled(true).log("Error has occured");
		
		// For reader Not Found
		from("direct-vm:processReaderNotFound")
		.routeId("READER_NOT_FOUND_ROUTER")
			.log("Alarm Details READER_NOT_FOUND_ROUTER -> ${body}")
			.bean(AlarmCommonUtils.class, "onReaderNotFound") // This is RfidVisitor.java
			.setProperty("ALARM_DETAILS", body()) // This is AlarmDto.java
			.bean(AlarmCommonUtils.class, "onAlarmSearch")
			.log("Search Request READER_NOT_FOUND_ROUTER ${body}")
			.to(alarmSearchIndexEndpoint)
			.log("Search Response READER_NOT_FOUND_ROUTER ${body}")
			.bean(AlarmCommonUtils.class, "onAlarmValidation") // Validate if it is empty then insert it in the alarmIdx else message 'Alarm Exists'
			.choice()
				.when(body().contains("NotFound"))
				.bean(AlarmCommonUtils.class, "onConvertAlarmToElastic")
				.log("Request to Elastic ${body}")
				.to(elasticIndexEndpoint)
				.log("After inserting elastic ${body}")
				.process(new Processor() {
					
					@Override
					public void process(Exchange exchange) throws Exception {
						AlarmDTO data = (AlarmDTO) exchange.getProperty("ALARM_DETAILS");
						exchange.getIn().setBody(data);
						Thread.sleep(1000);
					}
				})
				.bean(AlarmCommonUtils.class, "onAlarmSearchAgain")
				.to(alarmSearchIndexEndpoint)
				.bean(AlarmCommonUtils.class, "onAlarmValidation")
				.log("After validating alarm ${body}")
				.bean(AlarmCommonUtils.class, "onAlarmPayload")
				.log("After alarm payload ${body}")
				.choice()
					.when(body().isEqualTo(null))
						.log("Can't publish because it is null ${body}")
					.otherwise().to(mqttEndpoint).log("MQTT published ${body}")
				.endChoice()
			.endChoice()
			.otherwise().log("Alarm Already exists for reader not found..");
	}
}
