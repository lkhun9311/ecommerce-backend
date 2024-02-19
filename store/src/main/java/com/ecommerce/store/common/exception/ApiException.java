package com.ecommerce.store.common.exception;

import com.ecommerce.store.common.error.ErrorCodeIfs;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException implements ApiExceptionIfs {

    private final transient ErrorCodeIfs errorCodeIfs;
    private final String errorDescription;

    public ApiException(ErrorCodeIfs errorCodeIfs) {
        super(errorCodeIfs.getDescription());
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = errorCodeIfs.getDescription();
    }

    public ApiException(ErrorCodeIfs errorCodeIfs, String errorDescription) {
        super(errorDescription);
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = errorDescription;
    }

    public ApiException(ErrorCodeIfs errorCodeIfs, Throwable tx) {
        super(tx);
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = errorCodeIfs.getDescription();
    }

    public ApiException(ErrorCodeIfs errorCodeIfs, String errorDescription, Throwable tx) {
        super(tx);
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = errorDescription;
    }
}
