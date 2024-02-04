package com.ecommerce.storeadmin.common.error.api;

import com.ecommerce.storeadmin.common.error.ErrorCode;
import com.ecommerce.storeadmin.common.error.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {
    private Integer resultCode;
    private String resultMessage;
    private String resultDescription;

    public static Result createOkResult() {
        return Result.builder()
                .resultCode(ErrorCode.OK.getErrorCodeNumber())
                .resultMessage(ErrorCode.OK.getDescription())
                .resultDescription("성공")
                .build();
    }

    public static Result createErrorResult(ErrorCodeIfs errorCodeIfs) {
        return Result.builder()
                .resultCode(errorCodeIfs.getErrorCodeNumber())
                .resultMessage(errorCodeIfs.getDescription())
                .resultDescription("에러 발생")
                .build();
    }

    public static Result createErrorResult(ErrorCodeIfs errorCodeIfs, Throwable tx) {
        return Result.builder()
                .resultCode(errorCodeIfs.getErrorCodeNumber())
                .resultMessage(errorCodeIfs.getDescription())
                .resultDescription(tx.getLocalizedMessage())
                .build();
    }

    public static Result createErrorResult(ErrorCodeIfs errorCodeIfs, String description) {
        return Result.builder()
                .resultCode(errorCodeIfs.getErrorCodeNumber())
                .resultMessage(errorCodeIfs.getDescription())
                .resultDescription(description)
                .build();
    }
}
