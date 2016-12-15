package com.gerald.communication.service.processors;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class EmailProcessor extends RouteBuilder{

	@Autowired
	@Qualifier("emailWriter")
	private Processor emailWriter;
	
	@Override
	public void configure() throws Exception {
		from("seda:EMAIL")
		.log("EMAIL Message Received")
		.log("${body}")
		.bean(emailWriter)
		.recipientList(simple("${headers.emailProtocol}://${headers.emailHost}"
				+ "?password=${headers.emailPassword}"
				+ "&username=${headers.emailUsername}"));
		
	}

}
