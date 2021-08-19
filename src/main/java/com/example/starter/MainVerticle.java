package com.example.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;

/**
 * Verticle that echo a message.
 */
public class MainVerticle extends AbstractVerticle {

  /**
   * {@inheritDoc}
   */
  @Override
  public void start(final Promise<Void> startPromise) throws Exception {

    final var server = this.vertx.createHttpServer();
    final var router = Router.router(this.vertx);
    router.get("/echo/:msg").respond(ctx -> {

      final var response = ctx.response();
      response.setStatusCode(200);
      response.putHeader("content-type", "text/plain");
      final String msg = ctx.pathParam("msg");
      return Future.succeededFuture(msg);

    });
    server.requestHandler(router).listen(8888, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });

  }
}
