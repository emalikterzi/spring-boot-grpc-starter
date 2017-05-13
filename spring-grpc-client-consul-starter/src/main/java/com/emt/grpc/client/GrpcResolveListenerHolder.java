package com.emt.grpc.client;

import io.grpc.NameResolver;

import java.util.Objects;

/**
 * Created by emt on 13.05.2017.
 */
public class GrpcResolveListenerHolder {

  private final NameResolver.Listener listener;
  private final String serviceName;


  public GrpcResolveListenerHolder(NameResolver.Listener listener, String serviceName) {
    this.listener = listener;
    this.serviceName = serviceName;
  }

  public NameResolver.Listener getListener() {
    return listener;
  }

  public String getServiceName() {
    return serviceName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GrpcResolveListenerHolder that = (GrpcResolveListenerHolder) o;
    return Objects.equals(serviceName, that.serviceName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(serviceName);
  }
}
