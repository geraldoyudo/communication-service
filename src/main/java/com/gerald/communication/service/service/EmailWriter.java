package com.gerald.communication.service.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultAttachment;
import org.springframework.util.Base64Utils;

import com.gerald.communication.service.entities.MessageData;
import com.sun.istack.ByteArrayDataSource;

public class EmailWriter implements Processor{
	
	@Override
	public void process(Exchange exchange) throws Exception {
		Message in = exchange.getIn();
		MessageData message = (MessageData)in.getBody();
		Map<String,Object> headers = new HashMap<>();
		headers.put("To", Arrays.toString(message.getTo()).replace("[", "").replace("]", ""));
		headers.put("From", message.getFrom());
		headers.put("Subject", message.get("subject"));
		headers.put("Bcc", Arrays.toString((String[])message.get("bcc",new String[]{})).replace("[", "").replace("]", ""));
		headers.put("Cc", Arrays.toString((String[])message.get("cc",new String[]{})).replace("[", "").replace("]", ""));
		headers.put("emailHost", message.get("host"));
		headers.put("emailProtocol", message.get("protocol", "smtp"));
		headers.put("emailUsername", message.get("username"));
		headers.put("emailPassword", message.get("password"));
		headers.putAll(message.getProperties());
		in.setBody(message.getBody());
		in.setHeaders(headers);
		try{
			List<Map<String, Object>> attachments =(List<Map<String,Object>>) message.get("attachments");
			if(attachments != null){
				for(Map<String,Object> attachment: attachments){
					try{
						String body = attachment.get("body").toString();
						byte[]  data = Base64Utils.decodeFromString(body);
						System.out.println(data.length);
					DefaultAttachment att = new DefaultAttachment
							(new DataHandler(
									new ByteArrayDataSource(data, attachment.get("content-type").toString())));
					if(attachment.get("content-description") != null)
						att.addHeader("Content-Description", attachment.get("content-description").toString());
					in.addAttachmentObject(attachment.get("name").toString(), att);
					}catch(ClassCastException ex){
						ex.printStackTrace();
						continue;
					}
				}
			}
		}catch(ClassCastException ex){
			ex.printStackTrace();
		}
		
	}
}
