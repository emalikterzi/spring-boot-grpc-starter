package com.example;

import com.emt.grpc.annotation.GrpcService;
import io.grpc.examples.helloworld.PaymentGrpc;
import io.grpc.examples.helloworld.PaymentRequest;
import io.grpc.examples.helloworld.PaymentResponse;
import io.grpc.stub.StreamObserver;

/**
 * Created by emt on 13.05.2017.
 */
@GrpcService
public class PaymentService extends PaymentGrpc.PaymentImplBase {

  @Override
  public void paymentInfo(PaymentRequest request, StreamObserver<PaymentResponse> responseObserver) {
    PaymentResponse paymentResponse = PaymentResponse.newBuilder().setPaymentInfo("test")
            .build();
    responseObserver.onNext(paymentResponse);
    responseObserver.onCompleted();
  }
}
