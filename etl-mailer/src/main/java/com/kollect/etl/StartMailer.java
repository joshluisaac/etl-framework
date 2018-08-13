package com.kollect.etl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StartMailer {
  public static void main(String[] args) {
    SpringApplication.run(StartMailer.class, args);
  }
}