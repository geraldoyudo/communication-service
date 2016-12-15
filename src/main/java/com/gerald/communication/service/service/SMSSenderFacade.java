package com.gerald.communication.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gerald.communication.service.entities.MessageData;

@Component("generalSMSSender")
public class SMSSenderFacade implements SMSSender{
	@Autowired(required = false)
	private List<AbstractSMSSender> senders;
	
	@Override
	public void send(MessageData message) {
		if(senders == null || senders.isEmpty())
			throw new SMSMessageNotSupportedException();
		boolean processed = false;
		for(SMSSender sender: senders){
			try{
				sender.send(message);
				processed = true;
				break;
			}catch(SMSMessageNotSupportedException ex){
				continue;
			}
		}
		if(!processed)
			throw new SMSMessageNotSupportedException("SMS not supported by existing implementations");
	}
}
