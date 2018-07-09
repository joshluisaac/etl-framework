package com.kollect.etl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class Server {

  @Bean
  public static void main(String[] args) {
    SpringApplication.run(Server.class, args);
  }

}