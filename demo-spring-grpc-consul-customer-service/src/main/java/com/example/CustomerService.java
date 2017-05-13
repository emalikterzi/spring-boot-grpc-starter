package com.example;

import com.emt.grpc.annotation.GrpcService;
import io.grpc.examples.helloworld.CustomerRequest;
import io.grpc.examples.helloworld.CustomerResponse;
import io.grpc.examples.helloworld.CustomerServiceGrpc;
import io.grpc.stub.StreamObserver;

/**
 * Created by emt on 13.05.2017.
 */
@GrpcService
public class CustomerService extends CustomerServiceGrpc.CustomerServiceImplBase {

  @Override
  public void customerInfo(CustomerRequest request, StreamObserver<CustomerResponse> responseObserver) {
    CustomerResponse customerResponse =
            CustomerResponse.newBuilder().setName("Enes").setLastName("Terzi").build();
    responseObserver.onNext(customerResponse);
    responseObserver.onCompleted();
  }
}
