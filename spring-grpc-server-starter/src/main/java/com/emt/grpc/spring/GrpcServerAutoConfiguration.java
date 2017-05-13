package com.emt.grpc.spring;

import com.emt.grpc.annotation.EnableGrpcServer;
import com.emt.grpc.server.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by emt on 10.05.2017.
 */
@Configuration
@EnableConfigurationProperties(value = {GrpcServerProperties.class})
public class GrpcServerAutoConfiguration {


  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnClass(value = {EnableGrpcServer.class})
  public AbstractGrpcServerBuilder abstractGrpcServerBuilder() {
    return new DefaultGrpcServerBuilder();
  }

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnClass(value = {EnableGrpcServer.class})
  public AbstractServiceRegistry abstractServiceRegistry() {
    return new DefaultServiceRegistry();
  }


  @Bean
  @ConditionalOnClass(value = {EnableGrpcServer.class})
  public GrpcServerMain grpcServerMain(AbstractGrpcServerBuilder abstractGrpcServerBuilder) {
    return new GrpcServerMain(abstractGrpcServerBuilder);
  }

}
