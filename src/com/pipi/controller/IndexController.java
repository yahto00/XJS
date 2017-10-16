package com.pipi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by yahto on 15/10/2017.
 */
@Controller
public class IndexController {
    @RequestMapping("index")
    public String index(){
        return "index";
    }
}
