package com.ecommerce.order.common.error;

public interface ErrorCodeIfs {

    Integer getHttpStatusCode();
    Integer getErrorCodeNumber();
    String getDescription();
}
