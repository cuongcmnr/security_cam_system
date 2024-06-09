package com.example.camAPI;

import com.example.camAPI.StreamHandler.VideoStreamHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class CamApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamApiApplication.class, args);
	}

}
