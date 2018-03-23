package com.kollect.etl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.kollect.etl.service.TransferService;
import com.kollect.etl.service.TransferServiceImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


@SpringBootApplication
@EnableScheduling
/*@EnableAsync*/
public class Server {

  @Bean
  public TransferService transferService() {
    return new TransferServiceImpl();
  }
  

  

/*    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("PowerETL-");
        executor.initialize();
        return executor;
    }*/

  public static void main(String[] args) {
    SpringApplication.run(Server.class, args);
  }

}