package com.example;

import com.emt.grpc.annotation.GrpcChannel;
import io.grpc.ManagedChannel;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * Created by emt on 13.05.2017.
 */
@Service
public class TestClass implements InitializingBean {

  @GrpcChannel(channelId = "1")
  ManagedChannel managedChannel;

  @GrpcChannel(channelId = "2")
  ManagedChannel managedChannel2;


  @Override
  public void afterPropertiesSet() throws Exception {
    GreeterGrpc.GreeterBlockingStub greeterBlockingStub = GreeterGrpc.newBlockingStub(managedChannel);

    HelloReply helloReply
            = greeterBlockingStub.sayHello(HelloRequest.newBuilder().setId(1).build());

    System.out.println(helloReply);
  }
}
