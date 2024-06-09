/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.camAPI.StreamHandler;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.stereotype.Service;
import java.awt.image.BufferedImage;

@Service
public class VideoStreamHandler {
    private final String streamUrl;
    private FFmpegFrameGrabber grabber;
    private Java2DFrameConverter converter;

    public VideoStreamHandler() {
        this.streamUrl = "udp://@:5000?pkt_size=1316";
        this.converter = new Java2DFrameConverter();
        this.grabber = new FFmpegFrameGrabber(streamUrl);
//        try {
//            if (grabber != null) {
//                grabber.start();
//                System.out.println("FFmpeg grabber started");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
    public void start() {
        
        try {
            if (grabber != null) {
                grabber.start();
                System.out.println("FFmpeg grabber started");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("this function does nothing");
    }
    public BufferedImage getNextFrame() {
        try {
            Frame frame = grabber.grab();
            if (frame != null && frame.image != null) {
                return converter.convert(frame);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void stop() {
        try {
            if (grabber != null) {
                grabber.stop();
                grabber.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int getVideoWidth() {
        return grabber.getImageWidth();
    }

    public int getVideoHeight() {
        return grabber.getImageHeight();
    }
}
