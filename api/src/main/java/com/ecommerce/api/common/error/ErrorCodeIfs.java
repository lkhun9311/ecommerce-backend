package com.ecommerce.api.common.error;

public interface ErrorCodeIfs {

    Integer getHttpStatusCode();
    Integer getErrorCodeNumber();
    String getDescription();
}
