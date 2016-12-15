package com.gerald.communication.service.service;

import com.gerald.communication.service.entities.MessageData;

public interface SMSSender {
	public void send(MessageData message);
}
