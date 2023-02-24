package com.zgw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Auther: zgw
 * @Date: 2021/4/12 - 04 - 12 - 13:25
 * @Description: com.zgw.controller
 * @version: 1.0
 */

@Controller
public class IndexController {

    /*@RequestMapping("/test")
    public String test(Model m){
        m.addAttribute("msg","<h1>hello</h1>");
        m.addAttribute("list", Arrays.asList("用户1","用户2"));
        return "test";
    }*/
    //首页定制管理
    @RequestMapping("/")
    public String index(){
        return "index";
    }


}
