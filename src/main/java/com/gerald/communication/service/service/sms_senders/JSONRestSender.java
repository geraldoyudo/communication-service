package com.gerald.communication.service.service.sms_senders;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.gerald.communication.service.entities.MessageData;
import com.gerald.communication.service.service.AbstractSMSSender;

@Component
public class JSONRestSender extends AbstractSMSSender{
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private CustomParser objectParser;
	
	@Override
	public boolean supports(String type) {
		return type.equals("single/rest/general");
	}

	@Override
	public void doSend(MessageData message) {
		String restApiUrl = message.get("url", "").toString();
		String httpMethod =  message.get("httpMethod", "").toString();
		String toKey =  message.get("keys.to", "to").toString();
		String fromKey =  message.get("keys.from", "from").toString();
		String bodyKey =  message.get("keys.body", "body").toString();
		String username =  message.get("username", "").toString();
		String password =  message.get("password", "").toString();
		String authType =  message.get("authType", "http-basic").toString();
		String userKey =  message.get("keys.user", "username").toString();
		String passwordKey =  message.get("keys.password", "password").toString();
		String format =  message.get("format", "json").toString();
		String parser =  message.get("parser", "").toString();
		Map<String,String> extra =(Map<String,String>) message.get("extra");
		
		Map<String, String> messageData = new HashMap<>();
		messageData.put(toKey, message.getTo()[0]);
		messageData.put(fromKey, message.getFrom());
		messageData.put(bodyKey, message.getBody());
		try{
			if(extra != null)
			messageData.putAll(extra);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		String response = "";
		HttpHeaders headers = new HttpHeaders();
		if(format.equalsIgnoreCase("xml")){
			headers.setContentType(MediaType.TEXT_XML);
		}else{
			headers.setContentType(MediaType.APPLICATION_JSON);  
		}
		if(authType.equals("http-basic")){
			String authorization = "Basic " + Base64Utils.encodeToString
					(String.format("%s:%s", username,password).getBytes());
			
		
			headers.set("Authorization", authorization);
     		HttpEntity<Map<String, String>> request = new HttpEntity<Map<String, String>>(messageData, headers);
     		if(httpMethod.equalsIgnoreCase("get")){
     			response = restTemplate.exchange(restApiUrl, HttpMethod.GET, request, String.class).getBody();
     		}else{
     			if(!parser.isEmpty()){
     				request = new HttpEntity(objectParser.parse(parser, messageData), headers);
     			}
     			response = restTemplate.postForObject(restApiUrl, request, String.class);
     		}
     	
		}else{
			messageData.put(userKey, username);
			messageData.put(passwordKey, password);
			System.out.println(httpMethod);
			if(httpMethod.equalsIgnoreCase("get")){
				MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
				for(Entry<String,String> entry: messageData.entrySet()){
					params.add(entry.getKey(), entry.getValue());
				}
				String uri = UriComponentsBuilder.fromUriString(restApiUrl).queryParams(params).build().toUriString();
				response = restTemplate.getForObject(uri, String.class );
     		}else{
     			System.out.println("Posting to " + restApiUrl);
     			HttpEntity<Map<String,String>> request = new HttpEntity<Map<String,String>>(messageData,headers);
     			if(!parser.isEmpty()){
     				request = new HttpEntity(objectParser.parse(parser, messageData), headers);
     			}
     			response = restTemplate.postForObject(restApiUrl, request, String.class);
     			System.out.println("Finished");
     		}	
		}
		System.out.println("SMS Data = " + messageData);
		System.out.println("Response = " + response );
	}
	
}
