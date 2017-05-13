package com.emt.grpc.client;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.ConsulRawClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.health.model.HealthService;
import com.emt.grpc.spring.GrpcClientConsulProperties;
import io.grpc.Attributes;
import io.grpc.EquivalentAddressGroup;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by emt on 13.05.2017.
 */
public class ConsulScheduler implements Runnable {

  private static Logger logger = Logger.getLogger(ConsulScheduler.class.getName());

  @Autowired
  private GrpcClientConsulProperties grpcClientConsulProperties;
  @Autowired
  private ConsulClient consulClient;

  private final List<GrpcResolveListenerHolder> grpcResolveListenerHolders
          = Collections.synchronizedList(new ArrayList<GrpcResolveListenerHolder>());

  @Override
  public void run() {
    synchronized (grpcResolveListenerHolders) {
      for (GrpcResolveListenerHolder grpcResolveListenerHolder : grpcResolveListenerHolders) {
        try {
          Response<List<HealthService>> listResponse =
                  consulClient.getHealthServices(grpcResolveListenerHolder.getServiceName(),
                          true, QueryParams.DEFAULT, grpcClientConsulProperties.getToken());


          List<HealthService> healthServices = listResponse.getValue();

          if (healthServices.isEmpty())
            continue;
          List<EquivalentAddressGroup> equivalentAddressGroups = this.getServers(healthServices);

          grpcResolveListenerHolder.getListener().onAddresses(equivalentAddressGroups, Attributes.EMPTY);
        } catch (Exception e) {
          logger.warning("Couldnt connect consul instance");
        }
      }
    }
  }

  private List<EquivalentAddressGroup> getServers(List<HealthService> healthServices) {
    List<EquivalentAddressGroup> equivalentAddressGroups = new ArrayList<>();
    for (HealthService healthService : healthServices) {
      SocketAddress socketAddress = new InetSocketAddress(healthService.getService().getAddress(), healthService.getService().getPort());
      EquivalentAddressGroup equivalentAddressGroup = new EquivalentAddressGroup(socketAddress, Attributes.EMPTY);
      equivalentAddressGroups.add(equivalentAddressGroup);
    }
    return equivalentAddressGroups;
  }

  public void addListener(GrpcResolveListenerHolder grpcResolveListenerHolder) {
    synchronized (grpcResolveListenerHolders) {
      grpcResolveListenerHolders.add(grpcResolveListenerHolder);
    }
  }

  public void remove(GrpcResolveListenerHolder grpcResolveListenerHolder) {
    this.grpcResolveListenerHolders.remove(grpcResolveListenerHolder);
  }
}
