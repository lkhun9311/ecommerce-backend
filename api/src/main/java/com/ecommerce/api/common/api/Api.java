package com.ecommerce.api.common.api;

import com.ecommerce.api.common.error.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api<T> {

    private Result result;

    @Valid
    private T body;

    public static <T> Api<T> ok(T data) {
        Api<T> api = new Api<>();
        api.result = Result.createOkResult();
        api.body = data;
        return api;
    }

    public static Api<Object> error(Result result) {
        Api<Object> api = new Api<>();
        api.result = result;
        return api;
    }

    public static Api<Object> error(ErrorCodeIfs errorCodeIfs) {
        Api<Object> api = new Api<>();
        api.result = Result.createErrorResult(errorCodeIfs);
        return api;
    }

    public static Api<Object> error(ErrorCodeIfs errorCodeIfs, Throwable tx) {
        Api<Object> api = new Api<>();
        api.result = Result.createErrorResult(errorCodeIfs, tx);
        return api;
    }

    public static Api<Object> error(ErrorCodeIfs errorCodeIfs, String description) {
        Api<Object> api = new Api<>();
        api.result = Result.createErrorResult(errorCodeIfs, description);
        return api;
    }
}
