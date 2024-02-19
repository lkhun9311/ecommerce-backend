package com.ecommerce.store.common.exception;

import com.ecommerce.store.common.error.ErrorCodeIfs;

public interface ApiExceptionIfs {

    ErrorCodeIfs getErrorCodeIfs();
    String getErrorDescription();
}
