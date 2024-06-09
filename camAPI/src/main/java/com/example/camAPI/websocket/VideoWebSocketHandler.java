/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.camAPI.websocket;

/**
 *
 * @author dvc
 */

import com.example.camAPI.StreamHandler.VideoStreamHandler;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class VideoWebSocketHandler extends BinaryWebSocketHandler {
    private final VideoStreamHandler videoStreamHandler;
    private volatile boolean streaming = true;

    public VideoWebSocketHandler(VideoStreamHandler videoStreamHandler) {
        this.videoStreamHandler = videoStreamHandler;
    }
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        videoStreamHandler.start();
        streaming = true;
       
        new Thread(() -> {
            while (streaming) {
                BufferedImage frame = videoStreamHandler.getNextFrame();
                if (frame != null) {
                    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                        ImageIO.write(frame, "jpeg", baos);
                        baos.flush();
                        byte[] imageBytes = baos.toByteArray();
                        session.sendMessage(new BinaryMessage(imageBytes));
                    } catch (IOException e) {
                        e.printStackTrace();
                        
                    }
                }
            }
        }).start();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        streaming = false;
        videoStreamHandler.stop();
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        exception.printStackTrace();
        session.close(CloseStatus.SERVER_ERROR);
        streaming = false;
        videoStreamHandler.stop();
    }
}
