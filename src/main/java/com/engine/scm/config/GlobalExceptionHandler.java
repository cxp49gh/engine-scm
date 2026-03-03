//package com.kuandeng.data.config;
//
//import com.kuandeng.data.branch.entities.StandardResponse;
//import com.kuandeng.data.utils.CustomError;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * @author cuijianxing
// * 2019-05-22 15:51
// */
//@RestControllerAdvice
//@Slf4j
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(value = Exception.class)
//    public StandardResponse<Object> errorHandler(HttpServletRequest request, ServletResponse servletResponse, Exception exception) {
//        log.error("异常", exception);
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        String message = String.format("%s,Trace Id:%s", exception.getMessage(), response.getHeaders("X-B3-TraceId"));
//        return new StandardResponse<Object>().buildResponse(CustomError.EXCEPTION).setMessage(message);
//    }
//
//}
