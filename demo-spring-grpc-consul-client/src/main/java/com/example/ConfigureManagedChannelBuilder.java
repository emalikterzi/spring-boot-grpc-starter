package com.example;

import com.emt.grpc.utils.ChannelBuilder;
import com.emt.grpc.client.AbstractManagedChannelBuilder;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Configuration;

/**
 * Created by emt on 13.05.2017.
 */
@Configuration
public class ConfigureManagedChannelBuilder extends AbstractManagedChannelBuilder {


  @Override
  public void managedChannelBuilder(ChannelBuilder channelBuilder) {
    channelBuilder
            .newChannelBuilder().withChannelId("payment")
            .forTarget("consul://payment-service", ManagedChannelBuilder.class)
            .usePlaintext(true)
            .and()
            .newChannelBuilder().withChannelId("customer")
            .forTarget("consul://customer-service", ManagedChannelBuilder.class)
            .usePlaintext(true);


  }
}
