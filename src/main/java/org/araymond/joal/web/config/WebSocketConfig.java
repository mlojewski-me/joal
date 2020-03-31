package org.araymond.joal.web.config;

import org.araymond.joal.web.annotations.ConditionalOnWebUi;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

/**
 * Created by raymo on 22/06/2017.
 */
@ConditionalOnWebUi
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureWebSocketTransport(final WebSocketTransportRegistration registration) {
        registration
                .setMessageSizeLimit(5000 * 1024) // Max incoming message size => 5Mo
                .setSendBufferSizeLimit(5000 * 1024); // Max outgoing buffer size => 5Mo
    }

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry config) {
        config.enableSimpleBroker(
                "/global",
                "/announce",
                "/config",
                "/torrents",
                "/speed"
        );
        // Message received with one of those destinationPrefixes will be automatically router to controllers @MessageMapping
        config.setApplicationDestinationPrefixes("/joal");
    }

    // Handshake endpoint
    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        registry.addEndpoint("ws")
                .setAllowedOrigins("*");
    }

}
