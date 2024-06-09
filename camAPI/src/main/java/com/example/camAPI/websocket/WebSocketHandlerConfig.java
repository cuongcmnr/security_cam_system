/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.camAPI.websocket;

import com.example.camAPI.StreamHandler.VideoStreamHandler;
import com.example.camAPI.websocket.VideoWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSocketHandlerConfig {

    @Bean
    public VideoWebSocketHandler videoWebSocketHandler(VideoStreamHandler videoStreamHandler) {
        return new VideoWebSocketHandler(videoStreamHandler);
    }
}
