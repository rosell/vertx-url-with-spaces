package com.example.starter;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.api.service.ServiceRequest;
import io.vertx.ext.web.api.service.ServiceResponse;

/**
 * Implementation of the {@link Echos}.
 */
public class EchosImpl implements Echos {

  /**
   * {@inheritDoc}
   */
  @Override
  public void getMsg(final String msg, final ServiceRequest context,
      final Handler<AsyncResult<ServiceResponse>> resultHandler) {

    resultHandler.handle(Future.succeededFuture(ServiceResponse.completedWithPlainText(Buffer.buffer(msg))));
  }

}
