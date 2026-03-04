package com.engine.scm.config;

import com.engine.scm.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BizException.class)
    public ResponseEntity<Map<String, Object>> bizExceptionHandler(HttpServletRequest request, BizException exception) {
        log.error("业务异常: {}", exception.getMessage(), exception);

        Map<String, Object> response = new HashMap<>();
        response.put("code", exception.getCode());
        response.put("message", exception.getMessage());
        response.put("path", request.getRequestURI());

        HttpStatus status = HttpStatus.BAD_REQUEST;
        if ("NOT_FOUND".equals(exception.getCode())) {
            status = HttpStatus.NOT_FOUND;
        } else if ("CONFLICT".equals(exception.getCode())) {
            status = HttpStatus.CONFLICT;
        } else if ("FORBIDDEN".equals(exception.getCode())) {
            status = HttpStatus.FORBIDDEN;
        }

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Map<String, Object>> errorHandler(HttpServletRequest request, Exception exception) {
        log.error("系统异常: {}", exception.getMessage(), exception);

        Map<String, Object> response = new HashMap<>();
        response.put("code", "INTERNAL_ERROR");
        response.put("message", "Internal server error: " + exception.getMessage());
        response.put("path", request.getRequestURI());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
