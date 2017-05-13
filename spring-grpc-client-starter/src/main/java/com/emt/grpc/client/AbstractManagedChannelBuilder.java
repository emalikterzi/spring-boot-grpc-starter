package com.emt.grpc.client;

import com.emt.grpc.utils.ChannelBuilder;

/**
 * Created by emt on 13.05.2017.
 */

public abstract class AbstractManagedChannelBuilder {

  public abstract void managedChannelBuilder(ChannelBuilder channelBuilder);

}
