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
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.FFmpegLogCallback;
@Service
public class VideoStreamHandler {
    private final String streamUrl;
    private final FFmpegFrameGrabber grabber;
    private final Java2DFrameConverter converter;
    private FFmpegFrameRecorder recorder ;
    private boolean isRecording = false;
    private Thread recordingThread;
    
    public VideoStreamHandler() {
        FFmpegLogCallback.set();
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
    }
    public BufferedImage getNextFrame() {
            try {
                Frame frame = grabber.grab();
                if (frame != null) {
                    BufferedImage currentFrame = converter.convert(frame);
                    return currentFrame;
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

   
   public void toggleRecording() {
        if (!isRecording) {
            startRecording();
        } else {
            stopRecording();
        }
        isRecording = !isRecording;
    }
 
public void startRecording() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String outputFilePath = "videos/recorded_video_" + dateFormat.format(new Date()) + ".mp4";
            File directory = new File("videos");
            if (!directory.exists() && !directory.mkdirs()) {
                throw new IOException("Failed to create directory: " + directory.getAbsolutePath());
            }

            recorder = new FFmpegFrameRecorder(outputFilePath, grabber.getImageWidth(), grabber.getImageHeight());
            recorder.setFrameRate(grabber.getFrameRate());
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
//            recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
            recorder.setFormat("mp4");
            recorder.start();

            recordingThread = new Thread(() -> {
                try {
                    while (isRecording) {
                        Frame frame = grabber.grab();
                        if (frame != null) {
                            try {
                                recorder.record(frame);
                            } catch (FFmpegFrameRecorder.Exception ex) {
                                Logger.getLogger(VideoStreamHandler.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            recordingThread.start();

            System.out.println("Recording started");
        } catch (IOException e) {
            System.err.println("Failed to start recording: " + e.getMessage());
            e.printStackTrace();
        }
    }



private void stopRecording() {
    try {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
            System.out.println("Recording stopped");
        }
    } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
        System.err.println("Failed to stop recording: " + e.getMessage());
        e.printStackTrace();
    }
}
    public int getVideoWidth() {
        return grabber.getImageWidth();
    }
 
    public int getVideoHeight() {
        return grabber.getImageHeight();
    }
    public boolean isRecording() {
        return isRecording;
    }
}

