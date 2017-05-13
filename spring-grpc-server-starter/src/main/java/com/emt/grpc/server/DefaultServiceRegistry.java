package com.emt.grpc.server;

import io.grpc.BindableService;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptor;
import io.grpc.ServerInterceptors;

import java.util.List;

/**
 * Created by emt on 11.05.2017.
 */
public class DefaultServiceRegistry extends AbstractServiceRegistry {
  @Override
  public void bindService(ServerBuilder serverBuilder,
                          BindableService bindableService,
                          List<ServerInterceptor> globalInterceptors,
                          List<ServerInterceptor> grpcServiceInterceptors,
                          GrpcServiceAnatHolder grpcServiceAnatHolder) {
    if (grpcServiceAnatHolder.isApplyOnGlobalInterceptors()) {
      if (grpcServiceInterceptors.isEmpty() == false || globalInterceptors.isEmpty() == false) {
        grpcServiceInterceptors.addAll(globalInterceptors);
        serverBuilder.addService(ServerInterceptors.intercept(bindableService, grpcServiceInterceptors));
      } else {
        serverBuilder.addService(bindableService);
      }
    } else {
      if (grpcServiceInterceptors.isEmpty())
        serverBuilder.addService(bindableService);
      else
        serverBuilder.addService(ServerInterceptors.intercept(bindableService, grpcServiceInterceptors));
    }
  }
}
