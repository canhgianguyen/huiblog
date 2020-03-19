package com.huiblog.huiblog.controller.client;

import com.huiblog.huiblog.model.dto.Paging;
import com.huiblog.huiblog.model.dto.PostDTO;
import com.huiblog.huiblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/")
@Controller
public class ClientController {
    @Autowired
    private PostService postService;

    @GetMapping(value = {"", "/{page}"})
    public String index(Model model, @PathVariable(required = false) Integer page) {
        int currPage = (page == null ? 0 : page - 1);
        Paging listPost = postService.getListPost(currPage);
        model.addAttribute("listPost", listPost);
        return "index";
    }

    @GetMapping("/p/{metaTitle}")
    public String post(Model model, @PathVariable(required = true) String metaTitle) {
        PostDTO postDTO = postService.getPostByMetaTitle(metaTitle);
        model.addAttribute("post", postDTO);
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
