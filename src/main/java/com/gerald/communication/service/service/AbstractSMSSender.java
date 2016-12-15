package com.gerald.communication.service.service;

import com.gerald.communication.service.entities.MessageData;

public abstract class AbstractSMSSender implements SMSSender{

	public abstract boolean supports(String type);
	public abstract void doSend(MessageData message);
	@Override
	public void send(MessageData message) {
		String service =  message.get("api", "").toString();
		if(service == null || !supports(service)){
			throw new SMSMessageNotSupportedException();
		}
		doSend(message);
	}


}
