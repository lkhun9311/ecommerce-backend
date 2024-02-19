package com.ecommerce.apigateway.common.exception;

import com.ecommerce.apigateway.common.error.ErrorCodeIfs;

public interface ApiExceptionIfs {

    ErrorCodeIfs getErrorCodeIfs();
    String getErrorDescription();
}
