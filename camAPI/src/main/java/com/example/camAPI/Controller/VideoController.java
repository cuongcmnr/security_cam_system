/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.camAPI.Controller;

import com.example.camAPI.StreamHandler.VideoStreamHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author dvc
 */

@Controller
public class VideoController {
 @GetMapping("/video")
    public String videoPage() {
        return "video"; 
    }
}
