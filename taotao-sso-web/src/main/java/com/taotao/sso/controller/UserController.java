package com.taotao.sso.controller;

import com.taotao.commom.utils.CookieUtils;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Value("${COOKIE_TOKEN_KEY}")
    private  String COOKIE_TOKEN_KEY;
    @RequestMapping(value = "/check/{param}/{type}",method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult checkData(@PathVariable String param,@PathVariable Integer type){
        TaotaoResult result = userService.checkData(param, type);
        return  result;
    }
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult register(TbUser tbUser){
        TaotaoResult result = userService.createUser(tbUser);
        return  result;
    }

    @RequestMapping(value ="login",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult login(String userName,String passWord,HttpServletRequest request,HttpServletResponse response){
        TaotaoResult result =userService.loginUser(userName,passWord);
        System.out.println(result);
        //存入cookie 要把token存入浏览器
        String token = result.getData().toString();
        CookieUtils.setCookie(request,response,COOKIE_TOKEN_KEY,token);
        return  result;
    }
    @RequestMapping(value ="/token/{token}",method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult getUserByToken(@PathVariable String token){
        TaotaoResult result = userService.getUserByToken(token);
        return  result;
    }

    }




