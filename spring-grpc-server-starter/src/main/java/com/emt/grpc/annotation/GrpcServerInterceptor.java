package com.emt.grpc.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by emt on 10.05.2017.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface GrpcServerInterceptor {

  boolean isGlobal() default false;

}
