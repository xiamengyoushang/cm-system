package com.excelsecu.cmsystem.common.result;

import com.excelsecu.cmsystem.common.enums.ErrorType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

@Data
@Builder
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int code;
    private String message;
    private T data;

    private static final Result EMPTY_SUCCESS_RESULT = Result.success(Collections.emptyMap());

    public static Result<?> success() {
        return EMPTY_SUCCESS_RESULT;
    }

    public static <T> Result<?> success(T obj) {
        ResultBuilder<T> resultBuilder = new ResultBuilder<T>();
        return resultBuilder
                .data(obj)
                .code(ErrorType.SUCCESS.getCode())
                .message(ErrorType.SUCCESS.getMessage())
                .build();
    }

    public static Result<?> failure(int code, String message) {
        return Result.builder()
                .code(code)
                .data(Collections.emptyMap())
                .message(message).build();
    }

    public static Result<?> failure(ErrorType resultStatus) {
        return Result.builder()
                .code(resultStatus.getCode())
                .data(Collections.emptyMap())
                .message(resultStatus.getMessage()).build();
    }

    public static Result<?> failure(ErrorType resultStatus, Throwable e) {
        return Result.builder()
                .data(e)
                .code(resultStatus.getCode())
                .data(Collections.emptyMap())
                .message(resultStatus.getMessage()).build();
    }

    public static Result<?> failure(ErrorType resultStatus, String message) {
        return Result.builder()
                .code(resultStatus.getCode())
                .data(Collections.emptyMap())
                .message(message).build();
    }

    @JsonIgnore
    public boolean isDataEmpty() {
        return (data instanceof Map)
                && ((Map) data).isEmpty();
    }

}
