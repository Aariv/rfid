/**
 * 
 */
package com.denali.rfid.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.denali.rfid.utils.AlarmCommon;

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
		
		//onException(RuntimeException.class).handled(true).log("Error has occured");
		
		from(alarmEndpoint)
		.routeId(alarmRouterName)
			.log("Alarm Details -> ${body}")
				.pipeline().multicast()
					.to("direct-vm:processMismatchAlarm")
					.to("direct-vm:processReaderNotFound")
					.to("direct-vm:processExpiredAlarm");
				
		
		from("direct-vm:processMismatchAlarm")
		.routeId("MISSMATCH_ROUTER")
			.log("Alarm Details MISSMATCH_ROUTER -> ${body}")
			.bean(AlarmCommon.class, "onReaderMismatch")
			.setProperty("ALARM", body())
			.bean(AlarmCommon.class, "onSearchAlarm")
			.log("Search Request MISSMATCH_ROUTER ${body}")
			.to(alarmSearchIndexEndpoint)
			.log("Search Response MISSMATCH_ROUTER ${body}")
			.bean(AlarmCommon.class, "onValidateAlarm")
			.choice()
				.when(body().contains("NotFound"))
				.bean(AlarmCommon.class, "onConvertAlarmToElastic")
				.to(elasticIndexEndpoint)
				.bean(AlarmCommon.class, "onSearchAlarm")
				.to(alarmSearchIndexEndpoint)
				.bean(AlarmCommon.class, "onValidateAlarm")
				.bean(AlarmCommon.class, "onAlarmPayload")
				.to(mqttEndpoint)
			.endChoice()
			.otherwise().log("Alarm Already exists for mismatch reader");
		
		
		from("direct-vm:processReaderNotFound")
			.routeId("READER_NOT_FOUND_ROUTER")
			.log("Alarm Details READER_NOT_FOUND_ROUTER -> ${body}")
			.bean(AlarmCommon.class, "onReaderNotFound")
			.setProperty("ALARM", body())
			.bean(AlarmCommon.class, "onSearchAlarm")
			.log("Search Request Expired ${body}")
			.to(alarmSearchIndexEndpoint)
			.log("Search Response MISSMATCH_ROUTER ${body}")
			.bean(AlarmCommon.class, "onValidateAlarm")
			.choice()
				.when(body().contains("NotFound"))
				.bean(AlarmCommon.class, "onConvertAlarmToElastic")
				.to(elasticIndexEndpoint)
				.bean(AlarmCommon.class, "onSearchAlarm")
				.to(alarmSearchIndexEndpoint)
				.bean(AlarmCommon.class, "onValidateAlarm")
				.bean(AlarmCommon.class, "onAlarmPayload")
				.to(mqttEndpoint)
			.endChoice()
			.otherwise().log("Alarm Already exists for reader not found");
		
		from("direct-vm:processExpiredAlarm")
			.routeId("VISITOR_EXPIRED_ROUTER")
			.log("Alarm Details VISITOR_EXPIRED_ROUTER -> ${body}")
			.bean(AlarmCommon.class, "onVisitorDateExpired")
			.setProperty("ALARM", body())
			.bean(AlarmCommon.class, "onSearchAlarm")
			.log("Search Request Expired ${body}")
			.to(alarmSearchIndexEndpoint)
			.log("Search Response MISSMATCH_ROUTER ${body}")
			.bean(AlarmCommon.class, "onValidateAlarm")
			.choice()
				.when(body().contains("NotFound"))
				.bean(AlarmCommon.class, "onConvertAlarmToElastic")
				.to(elasticIndexEndpoint)
				.bean(AlarmCommon.class, "onSearchAlarm")
				.to(alarmSearchIndexEndpoint)
				.bean(AlarmCommon.class, "onValidateAlarm")
				.bean(AlarmCommon.class, "onAlarmPayload")
				.to(mqttEndpoint)
			.endChoice()
			.otherwise().log("Alarm Already exists for expired visitor");
	}
}
