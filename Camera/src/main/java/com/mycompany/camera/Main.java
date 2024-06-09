package com.mycompany.camera;
import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NewJFrame frame = new NewJFrame();
            frame.setVisible(true);
            frame.getOkButton().addActionListener(e -> {
                String serverIp = frame.getServerIpField().getText();
                String serverPort = frame.getServerPortField().getText();
                Camera camera = new Camera(serverIp, Integer.parseInt(serverPort));
                camera.start();
            });
        });
    }
}

