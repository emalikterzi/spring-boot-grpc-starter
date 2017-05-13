package com.example;

import com.emt.grpc.annotation.GrpcService;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;

/**
 * Created by emt on 12.05.2017.
 */
@GrpcService
public class HelloService extends GreeterGrpc.GreeterImplBase {

  @Override
  public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
    HelloReply helloReply = HelloReply.newBuilder().setName("Enes")
            .setLastName("Terzi").setId(request.getId()).build();
    responseObserver.onNext(helloReply);
    responseObserver.onCompleted();
  }
}
