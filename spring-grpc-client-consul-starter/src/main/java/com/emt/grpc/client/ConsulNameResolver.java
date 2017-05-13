package com.emt.grpc.client;

import io.grpc.NameResolver;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by emt on 12.05.2017.
 */
public class ConsulNameResolver extends NameResolver {

  @Autowired
  private ConsulScheduler consulScheduler;

  private GrpcResolveListenerHolder grpcResolveListenerHolder;

  private final String serviceName;


  public ConsulNameResolver() {
    this(null);
  }

  public ConsulNameResolver(String serviceName) {
    this.serviceName = serviceName;
  }

  @Override
  public String getServiceAuthority() {
    return this.serviceName;
  }

  @Override
  public void start(Listener listener) {
    this.grpcResolveListenerHolder = new GrpcResolveListenerHolder(listener, serviceName);
    this.consulScheduler.addListener(this.grpcResolveListenerHolder);
  }

  @Override
  public void shutdown() {
    this.consulScheduler.remove(this.grpcResolveListenerHolder);
  }
}
