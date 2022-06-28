package com.itech.guide.global.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.error("접근거부");
        String exception = (String) request.getAttribute("exception");
        response.sendRedirect("/exception/entrypoint");
    }


}
