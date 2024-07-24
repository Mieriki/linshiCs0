package com.mugen.inventory.controller.exception;


import com.mugen.inventory.utils.RestBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RuntimeController {
    @ExceptionHandler(RuntimeException.class)
    public RestBean<Void> bindateError(RuntimeException exception) {
        log.warn("Resolved [{}: {}]", exception.getClass().getName(), exception.getMessage());
        return RestBean.failure(400,  exception.getMessage());
    }
}
