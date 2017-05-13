package com.emt.grpc.server;

import io.grpc.BindableService;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptor;

import java.util.List;

/**
 * Created by emt on 11.05.2017.
 */
public abstract class AbstractServiceRegistry {


  public abstract void bindService(ServerBuilder serverBuilder,
                                   BindableService bindableService,
                                   List<ServerInterceptor> globalInterceptors, List<ServerInterceptor> serviceInterceptors,GrpcServiceAnatHolder grpcServiceAnatHolder);


}
