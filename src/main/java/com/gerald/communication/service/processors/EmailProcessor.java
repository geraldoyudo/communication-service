package com.gerald.communication.service.processors;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class EmailProcessor extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		from("seda:EMAIL")
		.log("EMAIL Message Received")
		.log("${body}");
		
	}

}
