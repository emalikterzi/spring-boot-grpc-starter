package com.emt.grpc.spring;

import com.emt.grpc.annotation.EnableGrpcClient;
import com.emt.grpc.utils.ChannelBuilder;
import com.emt.grpc.client.GrpcBeanPostProcessor;
import com.emt.grpc.utils.GrpcBeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by emt on 10.05.2017.
 */
@Configuration
public class GrpcClientAutoConfiguration {

  @Bean
  @ConditionalOnClass(value = {EnableGrpcClient.class})
  public GrpcBeanPostProcessor grpcBeanProcessor() {
    return new GrpcBeanPostProcessor();
  }

  @Bean
  public GrpcBeanUtils grpcBeanUtils() {
    return new GrpcBeanUtils();
  }

  @Bean
  public ChannelBuilder channelBuilder() {
    return new ChannelBuilder();
  }

}
