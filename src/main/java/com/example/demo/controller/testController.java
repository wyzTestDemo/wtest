package com.example.demo.controller;

import com.example.demo.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Controller
public class testController {
    @GetMapping("/test")
    @ResponseBody
    public String testC() throws Exception {
        throw new Exception();
    }
    @RequestMapping(method = RequestMethod.POST,value = "/login")
    @ResponseBody
    public String testLogin(User user){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        subject.login(token);
        return "success";
    }
}
