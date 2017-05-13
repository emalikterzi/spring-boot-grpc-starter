package com.emt.grpc.utils;

import io.grpc.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * Created by emt on 13.05.2017.
 */
public class ChannelBuilder implements IdStep {

  private final Map<String, Builder> managedChannelBuilders = new HashMap<>();

  protected Map<String, Builder> getManagedChannelBuilders() {
    return this.managedChannelBuilders;
  }

  public IdStep newChannelBuilder() {
    return this;
  }

  public HostStep newChannelBuilder(String channelId) {
    Builder builder = new Builder(this);
    if (this.managedChannelBuilders.containsKey(channelId))
      throw new RuntimeException("Multiple id in Channel Builder id :" + channelId);
    managedChannelBuilders.put(channelId, builder);

    return builder;
  }

  @Override
  public HostStep withChannelId(String channelId) {
    return this.newChannelBuilder(channelId);
  }

  public static class Builder implements HostStep, OtherStep {
    private ManagedChannelBuilder managedChannelBuilder;
    private final ChannelBuilder channelBuilder;
    private String target;


    public Builder(ChannelBuilder channelBuilder) {
      this.channelBuilder = channelBuilder;
    }

    protected ManagedChannelBuilder getManagedChannelBuilder() {
      return this.managedChannelBuilder;
    }

    @Override
    public OtherStep forAddress(String host, int port, Class<? extends ManagedChannelBuilder> aClass) {
      try {

        this.managedChannelBuilder =
                (ManagedChannelBuilder) aClass.getMethod("forAddress", String.class, int.class).invoke(null, host, port);
      } catch (IllegalAccessException e) {

      } catch (InvocationTargetException e) {

      } catch (NoSuchMethodException e) {

      }
      return this;
    }

    @Override
    public OtherStep forTarget(String target, Class<? extends ManagedChannelBuilder> aClass) {
      try {

        this.managedChannelBuilder =
                (ManagedChannelBuilder) aClass.getMethod("forTarget", String.class).invoke(null, target);
        this.setTarget(target);

      } catch (IllegalAccessException e) {

      } catch (InvocationTargetException e) {

      } catch (NoSuchMethodException e) {

      }
      return this;
    }

    @Override
    public OtherStep intercept(List<ClientInterceptor> interceptorList) {
      this.managedChannelBuilder.intercept(interceptorList);
      return this;
    }

    @Override
    public ChannelBuilder and() {
      return channelBuilder;
    }

    @Override
    public OtherStep usePlaintext(boolean usePlaintext) {
      this.managedChannelBuilder.usePlaintext(usePlaintext);
      return this;
    }

    @Override
    public OtherStep intercept(ClientInterceptor... interceptorList) {
      this.managedChannelBuilder.intercept(interceptorList);
      return this;
    }


    @Override
    public OtherStep compressorRegistry(CompressorRegistry compressorRegistry) {
      this.managedChannelBuilder.compressorRegistry(compressorRegistry);
      return this;
    }

    @Override
    public OtherStep decompressorRegistry(DecompressorRegistry decompressorRegistry) {
      this.managedChannelBuilder.decompressorRegistry(decompressorRegistry);
      return this;
    }


    @Override
    public OtherStep directExecutor() {
      this.managedChannelBuilder.directExecutor();
      return this;
    }

    public String getTarget() {
      return target;
    }

    public void setTarget(String target) {
      this.target = target;
    }

    @Override
    public OtherStep executor(Executor executor) {
      this.managedChannelBuilder.executor(executor);
      return this;
    }

    @Override
    public OtherStep idleTimeout(long value, TimeUnit unit) {
      this.managedChannelBuilder.idleTimeout(value, unit);
      return this;
    }

    @Override
    public OtherStep loadBalancerFactory(LoadBalancer.Factory loadBalancerFactory) {
      this.managedChannelBuilder.loadBalancerFactory(loadBalancerFactory);
      return this;
    }

    @Override
    public OtherStep maxInboundMessageSize(int maxInboundMessageSize) {
      this.managedChannelBuilder.maxInboundMessageSize(maxInboundMessageSize);
      return this;
    }


    @Override
    public OtherStep userAgent(String userAgent) {
      this.managedChannelBuilder.userAgent(userAgent);
      return this;
    }

    @Override
    public OtherStep overrideAuthority(String authority) {
      this.managedChannelBuilder.overrideAuthority(authority);
      return this;
    }

    @Override
    public OtherStep nameResolverFactory(NameResolver.Factory resolverFactory) {
      this.managedChannelBuilder.nameResolverFactory(resolverFactory);
      return this;
    }
  }
}
