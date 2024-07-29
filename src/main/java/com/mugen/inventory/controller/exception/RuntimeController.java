package com.mugen.inventory.controller.exception;


import com.mugen.inventory.utils.RestBean;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLSyntaxErrorException;

@Slf4j
@RestControllerAdvice
public class RuntimeController {
    @ExceptionHandler(SQLSyntaxErrorException.class)
    public RestBean<Void> bindateError(SQLSyntaxErrorException e) {
        log.warn("Resolved [{}: {}]", e.getClass().getName(), e.getMessage());
        return RestBean.failure(400,  "对象构建失败");
    }
}
