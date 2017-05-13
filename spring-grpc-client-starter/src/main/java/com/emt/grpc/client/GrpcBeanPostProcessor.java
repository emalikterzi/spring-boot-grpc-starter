package com.emt.grpc.client;

import com.emt.grpc.utils.GrpcBeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

/**
 * Created by emt on 13.05.2017.
 */
public class GrpcBeanPostProcessor implements BeanPostProcessor {

  @Autowired
  private GrpcBeanUtils grpcBeanUtils;

  @Override
  public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
    this.scanManagedChannelAnat(o, s);
    return o;
  }

  @Override
  public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
    return o;
  }

  protected void scanManagedChannelAnat(Object bean, String beanName) {
    this.configureFieldInjection(bean);
  }

  private void configureFieldInjection(Object bean) {
    Class<?> managedBeanClass = bean.getClass();
    ReflectionUtils.FieldCallback fieldCallback =
            new ManagedChannelFieldCallback(bean, this.grpcBeanUtils);
    ReflectionUtils.doWithFields(managedBeanClass, fieldCallback);
  }
}
