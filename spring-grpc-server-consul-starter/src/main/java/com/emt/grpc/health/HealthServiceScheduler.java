package com.emt.grpc.health;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.agent.model.NewService;

/**
 * Created by emt on 14.05.2017.
 */
public class HealthServiceScheduler implements Runnable {

  private final NewService newServiceHolder;
  private final ConsulClient consulClient;
  private final HealthCheckService healthCheckService;

  public HealthServiceScheduler(NewService newServiceHolder, ConsulClient consulClient,
                                HealthCheckService healthCheckService) {
    this.newServiceHolder = newServiceHolder;
    this.consulClient = consulClient;
    this.healthCheckService = healthCheckService;
  }


  @Override
  public void run() {
    try {
      this.healthCheckService.checkHealth();
      this.consulClient.agentCheckPass(this.getServiceId());
    } catch (Exception e) {

    }
  }

  private String getServiceId() {
    return String.format("service:%s", this.newServiceHolder.getId());
  }

  public void destroy() {

  }


}
