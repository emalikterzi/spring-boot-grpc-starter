package com.emt.grpc.utils;

/**
 * Created by emt on 13.05.2017.
 */
public interface ChannelJob {

  void beforeCreate(ChannelBuilder.Builder builder);

}
