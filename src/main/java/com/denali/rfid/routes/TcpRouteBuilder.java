/**
 * 
 */
package com.denali.rfid.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.denali.rfid.utils.RFIDCommon;

/**
 * @author zentere
 *
 */
@Component
public class TcpRouteBuilder extends RouteBuilder {

	@Value("${tcp.server.endpoint}")
	private String tcpServerEndpoint;

	@Value("${seda.stage1.endpoint}")
	private String sedaStage1Endpoint;

	@Value("${seda.stage2.endpoint}")
	private String sedaStage2Endpoint;

	@Value("${tcp.route.name}")
	private String tcpRouteName;

	@Override
	public void configure() throws Exception {
		
		onException(RuntimeException.class).handled(true).log("Error has occured");
		
		// Get data from tcp port 4001
		from(tcpServerEndpoint).routeId(tcpRouteName)
			.log("Payload from TCP -> ${body}")
			// Process the stream data and extract header, epc etc
			.bean(RFIDCommon.class, "onProcessRfid")
				.log("After process -> ${body}")
					.to(sedaStage1Endpoint, sedaStage2Endpoint);
	}

}
