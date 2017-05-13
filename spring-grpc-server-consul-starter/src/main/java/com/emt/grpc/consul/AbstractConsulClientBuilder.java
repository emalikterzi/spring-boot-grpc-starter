package com.emt.grpc.consul;

import com.ecwid.consul.v1.ConsulClient;

/**
 * Created by emt on 12.05.2017.
 */

public abstract class AbstractConsulClientBuilder {

  abstract ConsulClient consulClient();

}
