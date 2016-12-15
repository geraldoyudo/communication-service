package com.gerald.communication.service.processors;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SMSProcessor extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		from("seda:SMS")
		.log("SMS Message Received")
		.log("${body}");
		
	}

}
