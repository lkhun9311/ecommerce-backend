package com.ecommerce.apigateway.common.error;

public interface ErrorCodeIfs {

    Integer getHttpStatusCode();
    Integer getErrorCodeNumber();
    String getDescription();
}
