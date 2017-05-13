package com.emt.grpc.consul;

import com.ecwid.consul.v1.ConsulClient;
import com.emt.grpc.spring.GrpcServerConsulProperties;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by emt on 12.05.2017.
 */
public class DefaultConsulClientBuilder extends AbstractConsulClientBuilder {

  @Autowired
  private GrpcServerConsulProperties grpcServerConsulProperties;

  @Override
  ConsulClient consulClient() {
    return new ConsulClient(grpcServerConsulProperties.getHost(), grpcServerConsulProperties.getPort());
  }
}
