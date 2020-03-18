package com.huiblog.huiblog.controller.client;

import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/signin")
    public String signIn() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            return "signin";
        }
        return "signedin";
    }

    @GetMapping("/signup")
    public String signUp() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            return "signup";
        }
        return "signedin";
    }

    @GetMapping("/403")
    public String forbidden() {
        return "403";
    }
}
