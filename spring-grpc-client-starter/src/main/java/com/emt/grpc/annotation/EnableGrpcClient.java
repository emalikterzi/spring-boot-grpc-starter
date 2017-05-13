package com.emt.grpc.annotation;

import java.lang.annotation.*;

/**
 * Created by emt on 26.08.2016.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableGrpcClient {

}
