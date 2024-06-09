package com.mycompany.camera;

import org.bytedeco.javacv.FFmpegLogCallback;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.FrameRecorder;

public class Camera {
//For linux
    private static final String     CAMERA_DEVICE = "/dev/video0";
//    For window
//    private static final String     CAMERA_DEVICE = "/dev/video0"; 

    private String     SERVER_IP;
    private int        SERVER_PORT;
    private static final String     VIDEO_CODEC = "libx264";
    private static final String     PRESET = "veryfast";
    private static final String     TUNE = "zerolatency";
    private static final String     MAXRATE = "3000k";
    private static final String     BUFSIZE = "700k";
    private static final String     FORMAT = "mpegts";
    private static final int        PKT_SIZE = 1316;
    public Camera(String serverIp, int serverPort) {
        this.SERVER_IP = serverIp;
        this.SERVER_PORT = serverPort;
    }
    public void start() {
        String serverIp = SERVER_IP; // replace with your server IP
        int serverPort = SERVER_PORT; // replace with your server port
        Camera camera = new Camera(serverIp, serverPort);
        FFmpegLogCallback.set();
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(CAMERA_DEVICE);
        FFmpegFrameRecorder recorder = null;

        try {
            grabber.start();
            int width = grabber.getImageWidth();
            int height = grabber.getImageHeight();
            recorder = new FFmpegFrameRecorder(
                String.format("udp://%s:%d?pkt_size=%d", camera.SERVER_IP, camera.SERVER_PORT, PKT_SIZE),
                width, height
            );
            recorder.setVideoCodecName(VIDEO_CODEC);
            recorder.setFormat(FORMAT);
            recorder.setVideoOption("preset", PRESET);
            recorder.setVideoOption("tune", TUNE);
            recorder.setVideoBitrate(Integer.parseInt(MAXRATE.replace("k", "000")));
            recorder.setOption("bufsize", BUFSIZE);
            recorder.setAudioChannels(0);
            recorder.start();

            Frame frame;
            while ((frame = grabber.grab()) != null) {
                recorder.record(frame);
            }
        } catch (Exception | FrameRecorder.Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (recorder != null) {
                    recorder.stop();
                    recorder.release();
                }
                grabber.stop();
                grabber.release();
            } catch (Exception | FrameRecorder.Exception e) {
                e.printStackTrace();
            }
        }
    }
}