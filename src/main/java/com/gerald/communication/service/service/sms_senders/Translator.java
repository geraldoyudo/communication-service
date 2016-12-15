package com.gerald.communication.service.service.sms_senders;

import java.util.Map;

public interface Translator {
	public boolean supports(String name);
	public Object translate(Map<String,String> message);

}
