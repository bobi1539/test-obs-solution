package com.test.obs.solution.controller;

import com.test.obs.solution.constant.Constant;
import com.test.obs.solution.constant.GlobalMessage;
import com.test.obs.solution.dto.response.BaseResponse;
import com.test.obs.solution.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RestControllerAdvice
@AllArgsConstructor
@Slf4j
public class ErrorHandlerController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> exception(Exception e) {
        log.error(Constant.ERROR, e);

        BaseResponse<Object> baseResponse = BaseResponse.builder()
                .code(GlobalMessage.INTERNAL_SERVER_ERROR.httpStatus.value())
                .message(GlobalMessage.INTERNAL_SERVER_ERROR.message)
                .build();
        return new ResponseEntity<>(baseResponse, GlobalMessage.INTERNAL_SERVER_ERROR.httpStatus);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BaseResponse<Object>> exception(BusinessException e) {
        log.error(Constant.ERROR, e);

        BaseResponse<Object> baseResponse = BaseResponse.builder()
                .code(e.getHttpStatus().value())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(baseResponse, e.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>> exception(MethodArgumentNotValidException e) {
        log.error(Constant.ERROR, e);

        AtomicReference<String> message = new AtomicReference<>("");
        List<FieldError> errors = e.getFieldErrors();
        errors.forEach(error -> message.set(error.getDefaultMessage()));
        BaseResponse<Object> baseResponse = BaseResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(message.get())
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
    }
}
