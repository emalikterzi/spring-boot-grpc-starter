package com.emt.grpc.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by emt on 13.05.2017.
 */
@ConfigurationProperties(value = "grpc.client.consul")
public class GrpcClientConsulProperties {

  private String host = "localhost";
  private int port = 8500;
  private String token;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }
}
