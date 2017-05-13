package com.emt.grpc.consul;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.agent.model.NewService;
import com.emt.grpc.server.AbstractGrpcServerBuilder;
import com.emt.grpc.spring.GrpcServerConsulProperties;
import com.emt.grpc.spring.GrpcServerProperties;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import java.util.logging.Logger;


/**
 * Created by emt on 12.05.2017.
 */
public class ConsulConfiguration implements InitializingBean, DisposableBean, Runnable {

  private static final Logger logger = Logger.getLogger(ConsulConfiguration.class.getName());

  @Autowired
  private AbstractGrpcServerBuilder abstractGrpcServerBuilder;

  @Autowired
  private AbstractConsulClientBuilder abstractConsulClientBuilder;

  @Autowired
  private GrpcServerConsulProperties grpcServerConsulProperties;

  @Autowired
  private GrpcServerProperties grpcServerProperties;

  private ConsulClient consulClient;

  private Thread thread;

  private NewServiceHolder newServiceHolder;

  private volatile boolean isDestroyed = false;

  @Value(value = "${server.port:#{8080}}")
  private String tomcatPort;

  @Override
  public void afterPropertiesSet() throws Exception {
    thread = new Thread(this);
    thread.start();
  }

  @Override
  public void destroy() throws Exception {
    this.thread.interrupt();
    if (this.consulClient != null && this.newServiceHolder != null) {
      this.consulClient.agentServiceDeregister(newServiceHolder.getServiceInstanceId());
    }
  }

  private boolean checkConsulActiveStatus() {
    if (this.abstractGrpcServerBuilder.getServerStatus() &&
            this.abstractConsulClientBuilder.consulClient() != null &&
            this.grpcServerConsulProperties.isRegister()) {
      this.consulClient = this.abstractConsulClientBuilder.consulClient();
      return true;
    }
    return false;
  }

  private boolean checkConsulConnection() {
    try {
      this.consulClient.getStatusLeader();
      logger.info(String.format("Consul connection ok to --- Host:%s , Port:%s",
              grpcServerConsulProperties.getHost(), grpcServerConsulProperties.getPort()));
      return true;
    } catch (Exception e) {

      try {
        if (!this.grpcServerConsulProperties.isFailFast() && this.tryToReconnect()) {
          logger.info(String.format("Consul connection ok to --- Host:%s , Port:%s"));
          return true;
        }
      } catch (InterruptedException e1) {
        return false;
      }

      logger.warning(String.format("Consul connection failed to --- Host:%s , Port:%s"
              , grpcServerConsulProperties.getHost(), grpcServerConsulProperties.getPort()));
      return false;
    }
  }

  private boolean tryToReconnect() throws InterruptedException {
    int currentTry = 0;
    int maxTry = this.grpcServerConsulProperties.getRetry().getMaxAttempts();
    int interval = this.grpcServerConsulProperties.getRetry().getInterval();
    interval = interval < 1000 ? 1000 : interval;

    while (!isDestroyed && (currentTry < maxTry)) {
      try {
        logger.warning(String.format("Trying to reconnect consul instance tryCount :%s", (currentTry + 1)));
        this.consulClient.getStatusLeader();
        return true;
      } catch (Exception e) {
        currentTry++;
        Thread.sleep(interval);
      }
    }
    return false;
  }

  @Override
  public void run() {
    if (this.checkConsulActiveStatus() && this.checkConsulConnection()) {
      NewService newService = this.buildService();
      this.consulClient.agentServiceRegister(newService, this.grpcServerConsulProperties.getToken());
    }
  }

  private NewService buildService() {
    this.createServiceHolder();
    NewService newService = new NewService();
    newService.setPort(this.newServiceHolder.getPort());
    newService.setName(this.newServiceHolder.getServiceName());
    newService.setTags(this.newServiceHolder.getTags());
    newService.setId(this.newServiceHolder.getServiceInstanceId());
    return newService;
  }

  private void createServiceHolder() {
    NewServiceHolder newServiceHolder = new NewServiceHolder();
    newServiceHolder.setTags(this.grpcServerConsulProperties.getTags());
    newServiceHolder.setServiceInstanceId(this.grpcServerConsulProperties.serviceInstanceId());
    newServiceHolder.setServiceName(this.grpcServerConsulProperties.getServiceName());
    newServiceHolder.setPort(this.grpcServerProperties.getPort());
    this.newServiceHolder = newServiceHolder;
  }

}
