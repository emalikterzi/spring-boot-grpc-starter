package com.emt.grpc.spring;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.ConsulRawClient;
import com.emt.grpc.annotation.EnableGrpcClient;
import com.emt.grpc.client.ConsulChannelJob;
import com.emt.grpc.client.ConsulNameResolver;
import com.emt.grpc.client.ConsulNameResolverProvider;
import com.emt.grpc.client.ConsulScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

/**
 * Created by emt on 10.05.2017.
 */
@Configuration
@EnableConfigurationProperties(value = GrpcClientConsulProperties.class)
public class GrpcClientConsulAutoConfiguration {


  @Autowired
  private GrpcClientConsulProperties grpcClientConsulProperties;

  @Bean
  @Primary
  @ConditionalOnClass(value = {EnableGrpcClient.class})
  public ConsulChannelJob consulChannelJob() {
    return new ConsulChannelJob();
  }


  @Bean
  @Scope(BeanDefinition.SCOPE_PROTOTYPE)
  public ConsulNameResolverProvider consulNameResolverProvider() {
    return new ConsulNameResolverProvider();
  }


  @Bean
  @Scope(BeanDefinition.SCOPE_PROTOTYPE)
  protected ConsulNameResolver createPictureBean(String serviceName) {
    return new ConsulNameResolver(serviceName);
  }

  @Bean
  public ConsulScheduler consulScheduler() {
    return new ConsulScheduler();
  }

  @Bean
  public ConsulClient consulRawClient() {
    return new ConsulClient(this.grpcClientConsulProperties.getHost(), this.grpcClientConsulProperties.getPort());
  }
}
