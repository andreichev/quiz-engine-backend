package com.university.itis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by apple on 08.02.2018.
 */

@Controller
public class DefaultController {
    @RequestMapping(value = "/")
    public String index() {
        return "site/index";
    }
}
