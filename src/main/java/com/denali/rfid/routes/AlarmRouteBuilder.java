/**
 * 
 */
package com.denali.rfid.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zentere
 *
 */
@Component
public class AlarmRouteBuilder extends RouteBuilder {

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
		
		from(alarmEndpoint)
		.routeId(alarmRouterName)
			.log("Alarm Details -> ${body}")
				.multicast()
					.to("direct-vm:processMismatchReader")
					.to("direct-vm:processReaderNotFound")
					.to("direct-vm:processExpiredAlarm");
				
		// For reader miss-matches
//		from("direct-vm:processMismatchReader")
//		.routeId("MISS_MATCH_ROUTER")
//			.log("Alarm Details MISSMATCH_ROUTER -> ${body}")
//			.bean(AlarmCommon.class, "onReaderMismatch") // This is RfidVisitor.java
//			.setProperty("ALARM_DETAILS", body()) // This is AlarmDto.java
//			.bean(AlarmCommon.class, "onSearchAlarm")
//			.log("Search Request MISSMATCH_ROUTER ${body}")
//			.to(alarmSearchIndexEndpoint)
//			.log("Search Response MISSMATCH_ROUTER ${body}")
//			.bean(AlarmCommon.class, "onValidateAlarm") // Validate if it is empty then insert it in the alarmIdx else message 'Alarm Exists'
//			.choice()
//				.when(body().contains("NotFound"))
//				.bean(AlarmCommon.class, "onConvertAlarmToElastic")
//				.log("Request to Elastic ${body}")
//				.to(elasticIndexEndpoint)
//				.log("After inserting elastic ${body}")
//				.process(new Processor() {
//					
//					@Override
//					public void process(Exchange exchange) throws Exception {
//						Thread.sleep(1000);
//					}
//				})
//				.bean(AlarmCommon.class, "onSearchAlarm")
//				.to(alarmSearchIndexEndpoint)
//				.bean(AlarmCommon.class, "onValidateAlarm")
//				.log("After validating alarm ${body}")
//				.bean(AlarmCommon.class, "onAlarmPayload")
//				.log("After alarm payload ${body}")
//				.choice()
//					.when(body().isEqualTo(null))
//						.log("Can't publish because it is null ${body}")
//					.otherwise().to(mqttEndpoint).log("MQTT published ${body}")
//				.endChoice()
//			.endChoice()
//			.otherwise().log("Alarm Already exists for mismatch reader");
//		
//		// For reader not found scenario
//		from("direct-vm:processReaderNotFound")
//			.routeId("READER_NOT_FOUND_ROUTER")
//			.log("Alarm Details READER_NOT_FOUND_ROUTER -> ${body}")
//			.bean(AlarmCommon.class, "onReaderNotFound")
//			.setProperty("ALARM", body())
//			.bean(AlarmCommon.class, "onSearchAlarm")
//			.log("Search Request Expired ${body}")
//			.to(alarmSearchIndexEndpoint)
//			.log("Search Response MISSMATCH_ROUTER ${body}")
//			.bean(AlarmCommon.class, "onValidateAlarm")
//			.choice()
//				.when(body().contains("NotFound"))
//				.bean(AlarmCommon.class, "onConvertAlarmToElastic")
//				.to(elasticIndexEndpoint)
//				.process(new Processor() {
//					
//					@Override
//					public void process(Exchange exchange) throws Exception {
//						Thread.sleep(1000);
//					}
//				})
//				.bean(AlarmCommon.class, "onSearchAlarm")
//				.to(alarmSearchIndexEndpoint)
//				.bean(AlarmCommon.class, "onValidateAlarm")
//				.bean(AlarmCommon.class, "onAlarmPayload")
//				.choice()
//					.when(body().isEqualTo(null))
//						.log("Can't publish because it is null ${body}")
//					.otherwise().to(mqttEndpoint).log("MQTT published ${body}")
//				.endChoice()
//			.endChoice()
//			.otherwise().log("Alarm Already exists for reader not found");
//		
//		// For visitor from and to date expired scenario
//		from("direct-vm:processExpiredAlarm")
//			.routeId("VISITOR_EXPIRED_ROUTER")
//			.log("Alarm Details VISITOR_EXPIRED_ROUTER -> ${body}")
//			.bean(AlarmCommon.class, "onVisitorDateExpired")
//			.setProperty("ALARM", body())
//			.bean(AlarmCommon.class, "onSearchAlarm")
//			.log("Search Request Expired ${body}")
//			.to(alarmSearchIndexEndpoint)
//			.log("Search Response VISITOR_EXPIRED_ROUTER ${body}")
//			.bean(AlarmCommon.class, "onValidateAlarm")
//			.choice()
//				.when(body().contains("NotFound"))
//				.log("Before onConvertAlarmToElastic alarm ${body}")
//				.bean(AlarmCommon.class, "onConvertAlarmToElastic")
//				.to(elasticIndexEndpoint)
//				.log("Before Search alarm ${body}")
//				.process(new Processor() {
//					
//					@Override
//					public void process(Exchange exchange) throws Exception {
//						Thread.sleep(1000);
//					}
//				})
//				.bean(AlarmCommon.class, "onSearchAlarm")
//				.to(alarmSearchIndexEndpoint)
//				.log("Before Validating alarm ${body}")
//				.bean(AlarmCommon.class, "onValidateAlarm")
//				.log("After Validating alarm ${body}")
//				.bean(AlarmCommon.class, "onAlarmPayload")
//				.choice()
//					.when(body().isEqualTo(null))
//						.log("Can't publish because it is null ${body}")
//					.otherwise().to(mqttEndpoint).log("MQTT published ${body}")
//				.endChoice()
//			.endChoice()
//			.otherwise().log("Alarm Already exists for expired visitor");
	}
}
