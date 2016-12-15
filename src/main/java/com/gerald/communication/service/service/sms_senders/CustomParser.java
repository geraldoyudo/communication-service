package com.gerald.communication.service.service.sms_senders;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Component
public class CustomParser {
	@Autowired(required = false)
	private List<Translator> translators;
	
	public Object parse(String name, Map<String,String> values){
		if(values == null){
			return null;
		}
		if(name == null){
			return values;
		}
		for(Translator translator: translators){
			if(translator.supports(name)){
				return translator.translate(values);
			}
		}	
		return values;
		
	}

}
