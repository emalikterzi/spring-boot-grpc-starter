package com.emt.grpc.spring;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;
import java.util.UUID;

/**
 * Created by emt on 10.05.2017.
 */
@ConfigurationProperties(value = "grpc.server.consul")
public class GrpcServerConsulProperties implements InitializingBean {

  private String host = "localhost";
  private int port = 8500;

  private boolean register = true;
  private String serviceName;
  private String serviceId;
  private String instanceId;

  private String interval = "10s";
  private String token;

  private List<String> tags;

  public String getInstanceId() {
    return instanceId;
  }

  public void setInstanceId(String instanceId) {
    this.instanceId = instanceId;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getInterval() {
    return interval;
  }

  public void setInterval(String interval) {
    this.interval = interval;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  private boolean failFast = true;
  @NestedConfigurationProperty
  private GrpcServerRetryProperties retry = new GrpcServerRetryProperties();


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

  public boolean isRegister() {
    return register;
  }

  public void setRegister(boolean register) {
    this.register = register;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  public boolean isFailFast() {
    return failFast;
  }

  public void setFailFast(boolean failFast) {
    this.failFast = failFast;
  }

  public GrpcServerRetryProperties getRetry() {
    return retry;
  }

  public void setRetry(GrpcServerRetryProperties retry) {
    this.retry = retry;
  }

  public String clientInstanceId() {
    return this.instanceId == null ? this.serviceName + UUID.randomUUID().toString() : this.instanceId;
  }

  public String serviceInstanceId() {
    return this.instanceId == null ? this.serviceName + UUID.randomUUID().toString() : this.instanceId;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    if (this.serviceName == null)
      throw new Exception("Service must be defined in config");
  }

  @ConfigurationProperties
  public static class GrpcServerRetryProperties {

    private int maxAttempts = 6;
    private int interval = 2000;

    public int getMaxAttempts() {
      return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
      this.maxAttempts = maxAttempts;
    }

    public int getInterval() {
      return interval;
    }

    public void setInterval(int interval) {
      this.interval = interval;
    }
  }


}
