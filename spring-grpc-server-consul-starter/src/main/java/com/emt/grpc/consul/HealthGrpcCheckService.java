package com.emt.grpc.consul;

import com.emt.grpc.annotation.GrpcService;
import grpc.health.v1.HealthCheck;
import grpc.health.v1.HealthGrpc;
import io.grpc.stub.StreamObserver;

/**
 * Created by emt on 13.05.2017.
 */
@GrpcService
public class HealthGrpcCheckService extends HealthGrpc.HealthImplBase {
  @Override
  public void check(HealthCheck.HealthCheckRequest request, StreamObserver<HealthCheck.HealthCheckResponse> responseObserver) {
    HealthCheck.HealthCheckResponse healthCheckResponse = HealthCheck.HealthCheckResponse.newBuilder().setStatus(HealthCheck.HealthCheckResponse.ServingStatus.SERVING).build();
    responseObserver.onNext(healthCheckResponse);
    responseObserver.onCompleted();
  }
}
