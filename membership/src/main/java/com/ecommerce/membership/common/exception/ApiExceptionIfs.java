package com.ecommerce.membership.common.exception;

import com.ecommerce.membership.common.error.ErrorCodeIfs;

public interface ApiExceptionIfs {

    ErrorCodeIfs getErrorCodeIfs();
    String getErrorDescription();
}
