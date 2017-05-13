package com.emt.grpc.annotation;

import com.emt.grpc.server.EmptyServerInterceptor;
import io.grpc.ServerInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * Created by emt on 25.08.2016.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface GrpcService {

  Class<? extends ServerInterceptor>[] interceptors() default EmptyServerInterceptor.class;

  boolean applyOnGlobalInterceptors() default false;

}
