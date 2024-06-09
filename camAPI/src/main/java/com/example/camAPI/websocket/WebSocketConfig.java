package com.example.camAPI.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    
    private final VideoWebSocketHandler videoWebSocketHandler;

    public WebSocketConfig(VideoWebSocketHandler videoWebSocketHandler) {
        this.videoWebSocketHandler = videoWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(videoWebSocketHandler, "/live/video").setAllowedOrigins("*");
    }
}