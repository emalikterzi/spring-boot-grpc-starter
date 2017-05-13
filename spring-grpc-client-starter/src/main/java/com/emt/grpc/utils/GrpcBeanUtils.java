package com.emt.grpc.utils;

import com.emt.grpc.client.AbstractManagedChannelBuilder;
import io.grpc.ClientInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.*;

/**
 * Created by emt on 13.05.2017.
 */
public class GrpcBeanUtils implements DisposableBean, InitializingBean {

  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private ChannelBuilder channelBuilder;
  private final Map<String, ManagedChannel> channelMap = new HashMap<>();

  private List<ClientInterceptor> clientInterceptorList = new ArrayList<>();

  protected ManagedChannel registerChannel(String key, ManagedChannelBuilder managedChannelBuilder) {
    if (!this.clientInterceptorList.isEmpty())
      managedChannelBuilder.intercept(this.clientInterceptorList);

    ManagedChannel managedChannel = managedChannelBuilder.build();
    channelMap.put(key, managedChannel);
    return managedChannel;
  }

  public ManagedChannel getChannel(String key) {
    return channelMap.get(key);
  }

  @Override
  public void destroy() throws Exception {
    if (this.channelMap.isEmpty())
      return;

    for (ManagedChannel managedChannel : this.channelMap.values()) {
      managedChannel.shutdown();
    }
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    try {

      AbstractManagedChannelBuilder abstractManagedChannelBuilder
              = new ArrayList<>(applicationContext.getBeansOfType(AbstractManagedChannelBuilder.class)
              .values()).get(0);

      Collection<ChannelJob> channelJobs = applicationContext.getBeansOfType(ChannelJob.class)
              .values();

      abstractManagedChannelBuilder.managedChannelBuilder(channelBuilder);
      for (String key : this.channelBuilder.getManagedChannelBuilders().keySet()) {
        ChannelBuilder.Builder builder
                = this.channelBuilder.getManagedChannelBuilders().get(key);
        for (ChannelJob channelJob : channelJobs) {
          channelJob.beforeCreate(builder);
        }
        this.registerChannel(key, builder.getManagedChannelBuilder());
      }
    } catch (Exception e) {
      throw new Exception("Configure Abstract Managed Channel Builder", e);
    }
  }
}
