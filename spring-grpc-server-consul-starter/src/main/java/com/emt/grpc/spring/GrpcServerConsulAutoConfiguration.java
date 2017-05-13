package com.emt.grpc.spring;

import com.emt.grpc.consul.AbstractConsulClientBuilder;
import com.emt.grpc.consul.ConsulConfiguration;
import com.emt.grpc.consul.DefaultConsulClientBuilder;
import com.emt.grpc.server.AbstractGrpcServerBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by emt on 10.05.2017.
 */
@Configuration
@EnableConfigurationProperties(value = {GrpcServerConsulProperties.class})
public class GrpcServerConsulAutoConfiguration {


  @Bean
  @ConditionalOnClass(value = {AbstractGrpcServerBuilder.class})
  public ConsulConfiguration consulMain() {
    return new ConsulConfiguration();
  }

  @Bean
  @ConditionalOnMissingBean
  public AbstractConsulClientBuilder abstractConsulClientBuilder() {
    return new DefaultConsulClientBuilder();
  }


}
