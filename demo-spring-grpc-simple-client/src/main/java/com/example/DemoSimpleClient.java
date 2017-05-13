package com.example;

import com.emt.grpc.annotation.EnableGrpcClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableGrpcClient
public class DemoSimpleClient {

  public static void main(String[] args) {
    SpringApplication.run(DemoSimpleClient.class, args);
  }
}
