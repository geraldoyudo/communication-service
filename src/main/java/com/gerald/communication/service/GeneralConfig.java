package com.gerald.communication.service;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

@Configuration
public class GeneralConfig {

	@Bean
	public RestTemplate restTemplate(){
		RestTemplate rt =  new RestTemplate();
		XmlMapper xmlMapper = new XmlMapper();
		xmlMapper.configure( ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true );
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(xmlMapper);
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_XML));
		rt.getMessageConverters().add(0, converter);
		return rt;
	}

}
