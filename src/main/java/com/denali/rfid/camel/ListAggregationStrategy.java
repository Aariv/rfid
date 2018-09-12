package com.denali.rfid.camel;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.stereotype.Component;

/**
 * @author zentere
 *
 */
@Component
public class ListAggregationStrategy implements AggregationStrategy {

	/**
	 * Aggregates an old and new exchange together to create a single combined
	 * exchange
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		Object newBody = newExchange.getIn().getBody();
		List<Object> list = null;
		if (oldExchange == null) {
			list = new ArrayList<Object>();
			list.add(newBody);
			newExchange.getIn().setBody(list);
			return newExchange;
		} else {
			list = oldExchange.getIn().getBody(ArrayList.class);
			list.add(newBody);
			return oldExchange;
		}
	}
}