package com.emt.grpc.server;

import com.emt.grpc.annotation.GrpcServerInterceptor;
import com.emt.grpc.annotation.GrpcService;
import com.emt.grpc.spring.GrpcServerProperties;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by emt on 10.05.2017.
 */
public abstract class AbstractGrpcServerBuilder {

  private final static Logger logger = Logger.getLogger(AbstractGrpcServerBuilder.class.getName());

  private final static Class<GrpcService> GRPC_SERVICE_ANAT_CLASS = GrpcService.class;
  private final static Class<GrpcServerInterceptor> GRPC_INTERCEPTOR_CLASS = GrpcServerInterceptor.class;
  private final static Class<BindableService> BINDABLE_SERVICE_CLASS = BindableService.class;
  private final static Class<ServerInterceptor> SERVER_INTERCEPTOR_CLASS = ServerInterceptor.class;

  @Autowired
  private GrpcServerProperties grpcServerProperties;

  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private AbstractServiceRegistry abstractServiceRegistry;

  private Server server;

  public abstract void serverBuilder(ServerBuilder serverBuilder);

  protected void init() throws Exception {

    final List<BindableService> grpcServiceInstances = getGrpcServiceInstances();
    final List<ServerInterceptor> globalServerInterceptors = getGlobalInterceptors();

    if (grpcServiceInstances.isEmpty()) {
      logger.warning(String.format("Not found grpc service"));
      return;
    }
    ServerBuilder serverBuilder = ServerBuilder.forPort(this.grpcServerProperties.getPort());
    this.serverBuilder(serverBuilder);

    for (BindableService bindableService : grpcServiceInstances) {
      logger.info(String.format("Binding grpc service : %s", bindableService.getClass().getName()));
      this.bindService(serverBuilder, bindableService, new ArrayList<>(globalServerInterceptors));
    }

    try {
      this.server = serverBuilder.build().start();
      logger.info(String.format("Grpc server started port: %d ", this.server.getPort()));
      this.blockUntilShutdown();
    } catch (IOException e) {
      throw new Exception(e);
    }
  }

  private void blockUntilShutdown() {
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          server.awaitTermination();
        } catch (InterruptedException e) {

        }
      }
    });
    thread.start();
  }

  public synchronized boolean getServerStatus() {
    return this.server != null && !this.server.isShutdown() && !this.server.isTerminated();
  }

  protected void destroy() {
    if (server != null)
      server.shutdown();
  }

  private void bindService(ServerBuilder serverBuilder,
                           BindableService bindableService,
                           List<ServerInterceptor> globalInterceptors) {

    GrpcService grpcService = bindableService.getClass().getAnnotation(GRPC_SERVICE_ANAT_CLASS);
    List<ServerInterceptor> grpcServiceInterceptors = this.getServicesInterceptor(grpcService);
    abstractServiceRegistry.bindService(serverBuilder, bindableService, globalInterceptors,
            grpcServiceInterceptors, new GrpcServiceAnatHolder(grpcService.applyOnGlobalInterceptors()));

  }

  private List<ServerInterceptor> getServicesInterceptor(GrpcService grpcService) {
    List<ServerInterceptor> interceptors = new ArrayList<>();
    Class<? extends ServerInterceptor>[] aClass = grpcService.interceptors();
    for (Class<? extends ServerInterceptor> each : aClass) {
      if (each.getName().equals(EmptyServerInterceptor.class.getName())) {
        continue;
      }
      ServerInterceptor serverInterceptor = applicationContext.getBean(each);
      interceptors.add(serverInterceptor);
    }
    return interceptors;
  }

  private List<BindableService> getGrpcServiceInstances() {
    List<Object> grpcServiceInstances =
            new ArrayList<>(applicationContext.getBeansWithAnnotation(GrpcService.class).values());

    if (grpcServiceInstances.isEmpty())
      return Collections.EMPTY_LIST;

    List<BindableService> validatedList = new ArrayList<>();
    for (Object each : grpcServiceInstances) {
      if (each instanceof BindableService)
        validatedList.add(BINDABLE_SERVICE_CLASS.cast(each));
    }
    return validatedList;
  }


  private List<ServerInterceptor> getGlobalInterceptors() {
    List<Object> globalServerInterceptors =
            new ArrayList<>(applicationContext
                    .getBeansWithAnnotation(GRPC_INTERCEPTOR_CLASS).values());

    if (globalServerInterceptors.isEmpty())
      return Collections.EMPTY_LIST;

    List<ServerInterceptor> validatedList = new ArrayList<>();

    for (Object each : globalServerInterceptors) {
      GrpcServerInterceptor anat = each.getClass().getAnnotation(GRPC_INTERCEPTOR_CLASS);
      if ((each instanceof ServerInterceptor) && anat.isGlobal())
        validatedList.add(SERVER_INTERCEPTOR_CLASS.cast(each));
    }

    return validatedList;
  }

}
