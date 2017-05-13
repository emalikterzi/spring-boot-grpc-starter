package com.emt.grpc.utils;

import io.grpc.ManagedChannelBuilder;

/**
 * Created by emt on 13.05.2017.
 */
public interface HostStep {

  OtherStep forAddress(String host, int port, Class<? extends ManagedChannelBuilder> aClass);


  OtherStep forTarget(String target, Class<? extends ManagedChannelBuilder> aClass);

}
