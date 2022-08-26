package com.mera.labViewer.WebSocket;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.mera.labViewer.Lab.AllLabs;

@Component
public class MyTextWebSocketHandler extends TextWebSocketHandler {
 
	private AllLabs allLabs;
	
	@Autowired
    protected MyTextWebSocketHandler(@Qualifier("lab") AllLabs allLabs) {
		super();
		this.allLabs = allLabs;
	}

	@Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //sessions.add(session);
        super.afterConnectionEstablished(session);
    }
 
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //sessions.remove(session);
        super.afterConnectionClosed(session, status);
    }
 
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        ObjectMapper mapper = new ObjectMapper();
        
        try {
        	if (!message.getPayload().equals("check")) {
        		AllLabs recievedLabs = mapper.readValue(message.getPayload(), AllLabs.class);
    			allLabs.updateMessages(recievedLabs);
        	}
		} catch (JsonMappingException e) {
			//e.printStackTrace();
		} catch (JsonProcessingException e) {
			//e.printStackTrace();
		}
        
		session.sendMessage(new TextMessage(mapper.writeValueAsString(allLabs)));
    }

	@Override
	protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
		super.handlePongMessage(session, message);
		ObjectMapper mapper = new ObjectMapper();
		session.sendMessage(new TextMessage(mapper.writeValueAsString(allLabs)));
	}
    
    
}
