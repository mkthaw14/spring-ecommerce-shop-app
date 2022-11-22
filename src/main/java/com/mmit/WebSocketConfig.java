package com.mmit;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableWebSocketMessageBroker
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		//enableSimpleBroker()  to enable a simple memory-based message broker to carry 
		//the messages back to the client on destinations prefixed with /user
		registry.enableSimpleBroker("/user");
		
		/*the /app prefix for messages that are bound for methods annotated with @MessageMapping. 
		 * This prefix will be used to define all the message mappings. For example, /app/hello is 
		 * the endpoint that the GreetingController.greeting() method is mapped to handle.*/
		registry.setApplicationDestinationPrefixes("/app");
		registry.setUserDestinationPrefix("/user");
	}
	
	
	/*This method registers the /customer-support-websocket, 
	 * enabling SockJS fallback options so that alternate 
	 * transports can be used if WebSocket is not available. 
	 * The SockJS client will attempt to connect to /customer-support-websocket
	 * and use the best available transport (websocket, xhr-streaming, xhr-polling, and so on).*/
	
	/*
	 * The registerStompEndpoints method registers the “/customer-support-websocket” endpoint, enabling Spring’s STOMP support. 
	 * Keep in mind that we are also adding an endpoint here that works without the SockJS for the sake of elasticity.*/
	
	/*
	 * It also enables the SockJS fallback options so that alternative messaging options may be used if WebSockets are not available. 
	 * This is useful since WebSocket is not supported in all browsers yet and may be precluded by restrictive network proxies.*/
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		
		//This line is responsible for defining the channels on server.  
		//So, the client can connect to end-points defined by the server.

		registry.addEndpoint("/customer-support-websocket");
		registry.addEndpoint("/customer-support-websocket").withSockJS();
	}
}
