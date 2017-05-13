package com.emt.grpc.annotation;

import java.lang.annotation.*;

/**
 * Created by emt on 25.08.2016.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GrpcChannel {

  String channelId();

}
