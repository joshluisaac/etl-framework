package com.kollect.etl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class ExtractorServer {

  public static void main(String[] args) {
    SpringApplication.run(ExtractorServer.class, args);
  }

}