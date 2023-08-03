package com.prd;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class GrpcServer {

  @Autowired
  private ProductServiceImpl productService;

  public static void main(String[] args) throws IOException, InterruptedException {
    System.out.println("* * * * * * * * ");
    ApplicationContext context = SpringApplication.run(GrpcServer.class, args);
    GrpcServer server = context.getBean(GrpcServer.class);
    server.start();
    server.blockUntilShutdown();
  }

  private Server grpcServer;

  private void start() throws IOException {
    int port = 9090; // Set the port on which the server will listen

    grpcServer = ServerBuilder.forPort(port)
            .addService(productService)
            .build();

    System.out.println("Starting gRPC server on port: " + port);
    grpcServer.start();

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      System.out.println("Shutting down gRPC server");
      grpcServer.shutdown();
    }));
  }

  private void blockUntilShutdown() throws InterruptedException {
    if (grpcServer != null) {
      grpcServer.awaitTermination();
    }
  }
}
