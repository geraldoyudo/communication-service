package com.gerald.communication.service.processors;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.gerald.communication.service.service.SMSSender;

@Component
public class SMSProcessor extends RouteBuilder{
	@Autowired
	@Qualifier("generalSMSSender")
	private SMSSender generalSMSSender;
	@Override
	public void configure() throws Exception {
		from("seda:SMS")
		.log("SMS Message Received")
		.log("${body}")
		.bean(generalSMSSender);
		
	}

}
