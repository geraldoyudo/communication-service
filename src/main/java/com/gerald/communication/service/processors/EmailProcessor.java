package com.gerald.communication.service.processors;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.gerald.communication.service.service.EmailWriter;

@Component
public class EmailProcessor extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		from("seda:EMAIL")
		.log("EMAIL Message Received")
		.log("${body}")
		.process(new EmailWriter())
		.recipientList(simple("${headers.emailProtocol}://${headers.emailHost}"
				+ "?password=${headers.emailPassword}"
				+ "&username=${headers.emailUsername}"));
		
	}

}
