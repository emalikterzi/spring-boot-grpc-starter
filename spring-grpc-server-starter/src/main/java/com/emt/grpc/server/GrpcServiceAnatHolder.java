package com.emt.grpc.server;

/**
 * Created by emt on 11.05.2017.
 */
public class GrpcServiceAnatHolder {

  private final boolean applyOnGlobalInterceptors;


  public GrpcServiceAnatHolder(boolean applyOnGlobalInterceptors) {
    this.applyOnGlobalInterceptors = applyOnGlobalInterceptors;
  }

  public boolean isApplyOnGlobalInterceptors() {
    return applyOnGlobalInterceptors;
  }
}
