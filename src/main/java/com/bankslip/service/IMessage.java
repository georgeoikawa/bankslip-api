package com.bankslip.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.bankslip.web.message.MessageInfo;

@Component
public interface IMessage {
	
	String getMessage(String key);
	
	MessageInfo getMessageInfo(String key);
	
	List<MessageInfo> mergeMessages(final List<MessageInfo> messageList);
	
	String getOutputMessage(HttpStatus status);
}