package com.emt.grpc.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * Created by emt on 10.05.2017.
 */
@ConfigurationProperties(value = "grpc.server")
public class GrpcServerProperties {

  @Value(value = "8500")
  private int port;

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }
}
