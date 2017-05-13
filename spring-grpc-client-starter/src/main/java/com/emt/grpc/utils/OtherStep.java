package com.emt.grpc.utils;

import io.grpc.*;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * Created by emt on 13.05.2017.
 */
public interface OtherStep {

  OtherStep usePlaintext(boolean usePlaintext);

  OtherStep intercept(ClientInterceptor... interceptorList);

  OtherStep compressorRegistry(CompressorRegistry compressorRegistry);

  OtherStep decompressorRegistry(DecompressorRegistry decompressorRegistry);

  OtherStep directExecutor();

  OtherStep executor(Executor executor);

  OtherStep idleTimeout(long value, TimeUnit unit);

  OtherStep loadBalancerFactory(LoadBalancer.Factory loadBalancerFactory);

  OtherStep maxInboundMessageSize(int maxInboundMessageSize);

  OtherStep userAgent(String userAgent);

  OtherStep overrideAuthority(String authority);

  OtherStep nameResolverFactory(NameResolver.Factory resolverFactory);

  OtherStep intercept(List<ClientInterceptor> interceptorList);

  ChannelBuilder and();
}
