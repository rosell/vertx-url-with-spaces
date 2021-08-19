package com.example.starter;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.ext.web.api.service.ServiceRequest;
import io.vertx.ext.web.api.service.ServiceResponse;
import io.vertx.ext.web.api.service.WebApiServiceGen;

/**
 * The service to manage the echo requests.
 */
@WebApiServiceGen
public interface Echos {

  /**
   * Echo the message.
   *
   * @param msg           message to echo.
   * @param context       of the operation.
   * @param resultHandler handler of the response.
   */
  void getMsg(String msg, ServiceRequest context, Handler<AsyncResult<ServiceResponse>> resultHandler);

}
