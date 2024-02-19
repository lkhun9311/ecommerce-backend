package com.ecommerce.store.common.error;

public interface ErrorCodeIfs {

    Integer getHttpStatusCode();
    Integer getErrorCodeNumber();
    String getDescription();
}
