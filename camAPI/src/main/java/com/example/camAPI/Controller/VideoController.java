/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.camAPI.Controller;

import com.example.camAPI.StreamHandler.VideoStreamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 *
 * @author dvc
 */

@Controller
public class VideoController {
    @Autowired
    private VideoStreamHandler videoStreamHandler;
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/video")
       public String videoPage() {
           return "video"; 
       }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String correctUsername = "admin";
        String correctPassword = "password";

        if (correctUsername.equals(loginRequest.getUsername()) && correctPassword.equals(loginRequest.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Invalid credentials"));
        }
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/toggleRecording")
    @ResponseBody
    public String toggleRecording() {
        videoStreamHandler.toggleRecording();
        return videoStreamHandler.isRecording() ? "Recording started" : "Recording stopped";
    }
}

class LoginRequest {
    private String username;
    private String password;

    // Getters and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    // Getter

    public String getMessage() {
        return message;
    }
}