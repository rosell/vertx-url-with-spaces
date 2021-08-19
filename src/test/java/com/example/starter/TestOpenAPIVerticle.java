package com.example.starter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@ExtendWith(VertxExtension.class)
public class TestOpenAPIVerticle {

  @BeforeEach
  void deploy_verticle(final Vertx vertx, final VertxTestContext testContext) {
    vertx.deployVerticle(new OpenAPIVerticle(), testContext.succeeding(id -> testContext.completeNow()));
  }

  @Test
  void verticle_deployed(final Vertx vertx, final VertxTestContext testContext) throws Throwable {
    testContext.completeNow();
  }

  /**
   * Test the echo of the messages form path.
   *
   * @param msg         that the server has to respond.
   * @param vertx       event bus to use.
   * @param testContext context to manage the test.
   */
  @ParameterizedTest(name = "Should respond to {0}")
  @ValueSource(strings = { "abc", "a%20" })
  public void shouldAnswerWithSpacesOnPath(final String msg, final Vertx vertx, final VertxTestContext testContext) {

    final var client = WebClient.create(vertx);
    client.getAbs("http://localhost:8888/echo/" + msg).putHeader("content-type", "text/plain").send()
        .onComplete(testContext.succeeding(response -> testContext.verify(() -> {

          assertEquals(response.statusCode(), 200);
          final var expected = URLDecoder.decode(msg, Charset.defaultCharset());
          assertEquals(response.bodyAsString(), expected);
          testContext.completeNow();

        })));

  }

}
