package com.engine.scm.config;

import brave.Span;
import brave.Tracer;
import brave.propagation.TraceContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author cuijianxing
 * 2019-06-21 12:50
 */
@Component
@Order(9999)
public class TraceFilter extends GenericFilterBean {

    private final Tracer tracer;

    public TraceFilter(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Span currentSpan = this.tracer.currentSpan();
        TraceContext traceContext = currentSpan.context();
        response.addHeader("X-B3-TraceId", traceContext.traceIdString());
        response.addHeader("X-B3-SpanId", traceContext.spanIdString());
        filterChain.doFilter(servletRequest, response);
    }
}
