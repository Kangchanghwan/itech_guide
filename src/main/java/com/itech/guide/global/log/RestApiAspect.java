package com.itech.guide.global.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;


@Aspect
@Slf4j
@Component
public class RestApiAspect {

    @Autowired
    private  ObjectMapper mapper;

    @Pointcut("within(com.itech.guide.domain.member.controller..*)" + "&& @annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void pointcut(){

    }

    @Before("pointcut()")
    public void logMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        PostMapping method = signature.getMethod().getAnnotation(PostMapping.class);

        Map<String, Object> parameters = getParameters(joinPoint);
        try {
            log.info("==> path(s): {}, method(s): {}, arguments: {} ",
                    method.path(), method.name(), mapper.writeValueAsString(parameters));
        } catch (JsonProcessingException e) {
            log.error("Error while converting", e);
        }
    }

    @AfterReturning(pointcut = "pointcut()", returning = "entity")
    public void logMethodAfter(JoinPoint joinPoint, ResponseEntity<?> entity) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        PostMapping mapping = signature.getMethod().getAnnotation(PostMapping.class);

        try {
            log.info("<== path(s): {}, method(s): {}, retuning: {}",
                    mapping.path(), mapping.name(), mapper.writeValueAsString(entity));
        } catch (JsonProcessingException e) {
            log.error("Error while converting", e);
        }
    }


    private Map<String, Object> getParameters(JoinPoint joinPoint) {
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();

        HashMap<String, Object> map = new HashMap<>();

        String[] parameterNames = signature.getParameterNames();

        for (int i = 0; i < parameterNames.length; i++) {
            map.put(parameterNames[i], joinPoint.getArgs()[i]);
        }

        return map;
    }

}
