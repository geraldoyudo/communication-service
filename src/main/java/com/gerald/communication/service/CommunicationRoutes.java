package com.gerald.communication.service;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import com.gerald.communication.service.entities.Message;

@Component
public class CommunicationRoutes extends RouteBuilder {
	
	@Override
	public void configure() throws Exception {
		 from("servlet:///send?httpMethodRestrict=POST")
	        .unmarshal().json(JsonLibrary.Jackson, Message.class)
	        .recipientList(
	        		simple("seda:${body.type}"))
	        .ignoreInvalidEndpoints()
	        .transform().constant("OK");
		
	}

}
