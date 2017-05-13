package com.emt.grpc.server;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by emt on 10.05.2017.
 */
public class GrpcServerMain implements InitializingBean, DisposableBean {

  private boolean serverStatus;
  private final AbstractGrpcServerBuilder abstractGrpcServerBuilder;

  @Autowired
  public GrpcServerMain(AbstractGrpcServerBuilder abstractGrpcServerBuilder) {
    this.abstractGrpcServerBuilder = abstractGrpcServerBuilder;
  }

  public void afterPropertiesSet() throws Exception {
    try {
      this.abstractGrpcServerBuilder.init();
      this.serverStatus = true;
    } catch (Exception e) {
      throw new Exception(e);
    }
  }

  public void destroy() throws Exception {
    if (this.serverStatus)
      this.abstractGrpcServerBuilder.destroy();
  }
}
