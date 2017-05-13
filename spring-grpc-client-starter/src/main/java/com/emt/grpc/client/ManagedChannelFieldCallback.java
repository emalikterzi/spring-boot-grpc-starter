package com.emt.grpc.client;

import com.emt.grpc.annotation.GrpcChannel;
import com.emt.grpc.utils.GrpcBeanUtils;
import io.grpc.ManagedChannel;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * Created by emt on 13.05.2017.
 */
public class ManagedChannelFieldCallback implements ReflectionUtils.FieldCallback {

  private static Class<GrpcChannel> GRPC_CHANNEL_CLASS = GrpcChannel.class;

  private final Object bean;
  private final GrpcBeanUtils grpcBeanUtils;


  public ManagedChannelFieldCallback(Object bean, GrpcBeanUtils grpcBeanUtils) {
    this.bean = bean;
    this.grpcBeanUtils = grpcBeanUtils;
  }

  @Override
  public void doWith(Field field)
          throws IllegalArgumentException, IllegalAccessException {
    if (!field.isAnnotationPresent(GRPC_CHANNEL_CLASS)) {
      return;
    }

    GrpcChannel grpcChannel = getAnat(field);
    String key = grpcChannel.channelId();
    ManagedChannel managedChannel = this.grpcBeanUtils.getChannel(key);

    if (managedChannel == null)
      throw new RuntimeException("Grpc Channel with id :" + key + " not found");

    ReflectionUtils.makeAccessible(field);
    field.set(bean, managedChannel);
  }

  private GrpcChannel getAnat(Field bean) {
    return bean.getAnnotation(GRPC_CHANNEL_CLASS);
  }

}
