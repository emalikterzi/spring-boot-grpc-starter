package com.example;

import com.emt.grpc.annotation.GrpcChannel;
import io.grpc.ManagedChannel;
import io.grpc.examples.helloworld.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * Created by emt on 13.05.2017.
 */
@Component
public class TestClient implements InitializingBean {

  @GrpcChannel(channelId = "customer")
  ManagedChannel customerService;

  @GrpcChannel(channelId = "payment")
  ManagedChannel paymentService;

  @Override
  public void afterPropertiesSet() throws Exception {

    CustomerServiceGrpc.CustomerServiceBlockingStub customerServiceBlockingStub
            = CustomerServiceGrpc.newBlockingStub(customerService);

    CustomerResponse customerResponse
            = customerServiceBlockingStub.customerInfo(CustomerRequest.getDefaultInstance()
            .newBuilderForType().setId(1).build());

    PaymentGrpc.PaymentBlockingStub paymentBlockingStub
            = PaymentGrpc.newBlockingStub(paymentService);

    PaymentResponse paymentResponse
            = paymentBlockingStub.paymentInfo(PaymentRequest.getDefaultInstance()
            .newBuilderForType().setId(1).build());

  }
}
