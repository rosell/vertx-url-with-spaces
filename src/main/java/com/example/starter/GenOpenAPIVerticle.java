package com.example.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.serviceproxy.ServiceBinder;

/**
 * Verticle that echo a message.
 */
public class GenOpenAPIVerticle extends AbstractVerticle {

  /**
   * {@inheritDoc}
   */
  @Override
  public void start(final Promise<Void> startPromise) throws Exception {

    final var openAPIURL = this.getClass().getClassLoader().getResource("openapi.yaml").toString();
    RouterBuilder.create(this.vertx, openAPIURL).onSuccess(routerBuilder -> {

      routerBuilder.mountServiceInterface(Echos.class, "echo");
      new ServiceBinder(this.vertx).setAddress("echo").register(Echos.class, new EchosImpl());

      final var router = routerBuilder.createRouter();
      final var server = this.vertx.createHttpServer();
      server.requestHandler(router).listen(8888, http -> {
        if (http.succeeded()) {
          startPromise.complete();
          System.out.println("HTTP server started on port 8888");
        } else {
          startPromise.fail(http.cause());
        }
      });
    }).onFailure(err -> {

      startPromise.fail(err);

    });

  }
}
