package com.ecommerce.storeadmin.common.exception;

import com.ecommerce.storeadmin.common.error.ErrorCodeIfs;

public interface ApiExceptionIfs {

    ErrorCodeIfs getErrorCodeIfs();
    String getErrorDescription();
}
