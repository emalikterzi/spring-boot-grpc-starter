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
            .newChannelBuilder().withChannelId("1")
            .forAddress("localhost", 8383, ManagedChannelBuilder.class)
            .usePlaintext(true)
            .and()
            .newChannelBuilder().withChannelId("2")
            .forAddress("localhost", 8484, ManagedChannelBuilder.class)
            .usePlaintext(true);


  }
}
