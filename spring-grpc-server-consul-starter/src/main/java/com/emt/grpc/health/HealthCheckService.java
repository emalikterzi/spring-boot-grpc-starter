package com.emt.grpc.health;

import com.emt.grpc.spring.GrpcServerProperties;
import grpc.health.v1.HealthCheck;
import grpc.health.v1.HealthGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by emt on 13.05.2017.
 */

public class HealthCheckService {

  @Autowired
  private GrpcServerProperties grpcServerProperties;

  public void checkHealth() throws Exception {
    ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", grpcServerProperties.getPort()).usePlaintext(true)
            .build();
    HealthGrpc.HealthBlockingStub healthBlockingStub = HealthGrpc.newBlockingStub(managedChannel);
    HealthCheck.HealthCheckResponse healthCheckResponse = healthBlockingStub.check(HealthCheck.HealthCheckRequest.getDefaultInstance());
    managedChannel.shutdownNow();
    if (!healthCheckResponse.getStatus().equals(HealthCheck.HealthCheckResponse.ServingStatus.SERVING))
      throw new Exception("");
  }

}
