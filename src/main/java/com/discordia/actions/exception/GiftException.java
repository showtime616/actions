package com.discordia.actions.exception;

public class GiftException extends Exception {
  public GiftException() {
  }

  public GiftException(String message) {
    super(message);
  }

  public GiftException(String message, Throwable cause) {
    super(message, cause);
  }

  public GiftException(Throwable cause) {
    super(cause);
  }

  public GiftException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
