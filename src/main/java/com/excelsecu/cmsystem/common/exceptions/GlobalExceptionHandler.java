package com.excelsecu.cmsystem.common.exceptions;

import com.baomidou.mybatisplus.extension.api.R;
import com.excelsecu.cmsystem.common.enums.ErrorType;
import com.excelsecu.cmsystem.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 校验错误拦截处理
     *
     * @param exception 错误信息集合
     * @return 错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> validationBodyException(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        String message = "";
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            if (!errors.isEmpty()) {
                errors.forEach(p -> {
                    FieldError fieldError = (FieldError) p;
                    log.error("Data check failure : object{" + fieldError.getObjectName() + "},field{" + fieldError.getField() +
                            "},errorMessage{" + fieldError.getDefaultMessage() + "}");

                });
                if (errors.size() > 0) {
                    FieldError fieldError = (FieldError) errors.get(0);
                    message = fieldError.getDefaultMessage();
                }
            }
        }
        return Result.failure(ErrorType.PARAM_INVALID, "".equals(message) ? ErrorType.PARAM_INVALID.getMessage() : message);
    }

    /**
     * 参数类型转换错误
     *
     * @param exception 错误
     * @return 错误信息
     */
    @ExceptionHandler(HttpMessageConversionException.class)
    public Result<?> parameterTypeException(HttpMessageConversionException exception) {
        exception.printStackTrace();
        return Result.failure(ErrorType.PARAM_TYPE);
    }

    /**
     * 参数错误
     *
     * @param exception 错误
     * @return 错误信息
     */
    @ExceptionHandler(ArgsException.class)
    public Result<?> argsException(ArgsException exception) {
        exception.printStackTrace();
        return Result.failure(ErrorType.PARAM_INVALID);
    }

}
