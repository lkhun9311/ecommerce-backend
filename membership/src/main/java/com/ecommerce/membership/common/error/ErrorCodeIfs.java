package com.ecommerce.membership.common.error;

public interface ErrorCodeIfs {

    Integer getHttpStatusCode();
    Integer getErrorCodeNumber();
    String getDescription();
}
