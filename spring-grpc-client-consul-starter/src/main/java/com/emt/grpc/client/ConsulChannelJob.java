package com.emt.grpc.client;

import com.emt.grpc.utils.ChannelBuilder;
import com.emt.grpc.utils.ChannelJob;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by emt on 13.05.2017.
 */
public class ConsulChannelJob implements ChannelJob, InitializingBean, DisposableBean {

  private ScheduledExecutorService executor;

  @Autowired
  private ConsulNameResolverProvider consulNameResolverProvider;

  @Autowired
  private ConsulScheduler consulScheduler;

  @Override
  public void beforeCreate(ChannelBuilder.Builder builder) {
    if (builder.getTarget() != null &&
            builder.getTarget().startsWith(ConsulNameResolverProvider.SCHEME)) {
      builder.nameResolverFactory(consulNameResolverProvider);
    }
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    CustomizableThreadFactory customizableThreadFactory
            = new CustomizableThreadFactory("consul-scheduler");
    customizableThreadFactory.setDaemon(true);
    executor = Executors.newSingleThreadScheduledExecutor(customizableThreadFactory);
    executor.scheduleWithFixedDelay(consulScheduler, 5, 10, TimeUnit.SECONDS);
  }

  @Override
  public void destroy() throws Exception {
    executor.shutdown();
  }
}
