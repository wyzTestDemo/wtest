package com.example.demo.exception;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@ResponseBody
public class WebException {
    @ExceptionHandler(value = { UnauthorizedException.class })
    public Map<String, String> unauthorized(Exception e) {
        Map<String,String> map = new HashMap<>();
        map.put("code","202");
        return map;
    }
    @ExceptionHandler
    public  Map<String, String> unknownAccount(UnknownAccountException e) {
        Map<String,String> map = new HashMap<>();
        map.put("status","204");
        map.put("message","账号不存在");
        return map;
    }
    @ExceptionHandler
    public Map<String, String> incorrectCredentials(IncorrectCredentialsException e) {
        Map<String,String> map = new HashMap<>();
        map.put("status","204");
        return map;
    }
    @ExceptionHandler
    public Map<String,String> all (Exception e){
        String msg = e.getMessage() == null ? "系统出现异常" : e.getMessage();
        System.out.println(msg);
        Map<String,String> map = new HashMap<>();
        map.put("testvue","测试");
        map.put("nb","测试1");
        map.put("yy","测试2");
        map.put("pp","测试3");
        return map;
    }

}

