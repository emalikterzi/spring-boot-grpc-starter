package com.emt.grpc.client;

import io.grpc.Attributes;
import io.grpc.NameResolver;
import io.grpc.NameResolverProvider;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.Nullable;
import java.net.URI;

/**
 * Created by emt on 12.05.2017.
 */
public class ConsulNameResolverProvider extends NameResolverProvider {

  @Autowired
  private BeanFactory beanFactory;

  protected static final String SCHEME = "consul";

  @Nullable
  @Override
  public NameResolver newNameResolver(URI targetUri, Attributes params) {
    ConsulNameResolver consulNameResolver = beanFactory.getBean(ConsulNameResolver.class,targetUri.getAuthority());
    return consulNameResolver;
  }

  @Override
  public String getDefaultScheme() {
    return SCHEME;
  }

  @Override
  protected boolean isAvailable() {
    return true;
  }

  @Override
  protected int priority() {
    return 1;
  }
}
