package com.huiblog.huiblog.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/")
@Controller
public class ClientController {
    @GetMapping("")
    public String index() {
        return "index";
    }

    @GetMapping("/post")
    public String post() {
        return "post";
    }

    @GetMapping("/about")
    public String about() {
        return "about-me";
    }

    @GetMapping("/category")
    public String category() {
        return "category";
    }
}
