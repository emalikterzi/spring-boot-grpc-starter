package com.emt.grpc.consul;

import java.util.List;

/**
 * Created by emt on 12.05.2017.
 */
public class NewServiceHolder {

  private int port;

  private String serviceName;
  private String serviceInstanceId;
  private List<String> tags;


  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getServiceInstanceId() {
    return serviceInstanceId;
  }

  public void setServiceInstanceId(String serviceInstanceId) {
    this.serviceInstanceId = serviceInstanceId;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

}
