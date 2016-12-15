package com.gerald.communication.service.entities;

import java.util.Map;

import lombok.Data;

@Data
public class Message {
	private String[] to;
	private String from;
	private String body;
	private MessageType type = MessageType.EMAIL;
	private Map<String,Object> properties;
	public enum MessageType {EMAIL, SMS}
}
