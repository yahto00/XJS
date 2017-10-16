package com.pipi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by yahto on 15/10/2017.
 */
@Controller
public class IndexController {
    /**
     * 访问首页
     *
     * @return
     * @author yahto
     */
    @RequestMapping("index")
    public String index() {
        return "index";
    }
}
