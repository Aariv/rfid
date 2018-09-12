/**
 * 
 */
package com.denali.rfid.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.denali.rfid.utils.ReaderCommon;

/**
 * @author zentere
 *
 */
@Component
public class ReaderRouteBuilder extends RouteBuilder {

	@Value("${es.index.endpoint}")
	private String elasticIndexEndpoint;
	
	@Value("${reader.route.endpoint}")
	private String readerRouteEndpoint;
	
	@Value("${reader.route.name}")
	private String readerRouteName;
	
	@Value("${odoo.rest.readers.api}")
	private String readerAPI;
	
	@Override
	public void configure() throws Exception {
		onException(RuntimeException.class).handled(true).log("Error has occured");
		
		from(readerRouteEndpoint)
			.routeId(readerRouteName)
				.log("Reader Details from readerEndpoint-> ${body}")
					.setHeader(Exchange.HTTP_METHOD, constant("GET"))
					.to(readerAPI)
					.bean(ReaderCommon.class, "onReaderToElasticsearchMap")
					.to(elasticIndexEndpoint)
						.log("Readers details updated success in the index");
				
	}

}
