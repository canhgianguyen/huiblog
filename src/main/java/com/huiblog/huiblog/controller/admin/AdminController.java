package com.huiblog.huiblog.controller.admin;


import com.huiblog.huiblog.model.dto.CategoryDTO;
import com.huiblog.huiblog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin")
@Controller
public class AdminController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public String index() {
        return "admin/index";
    }

    @GetMapping("/post")
    public String post() {
        return "admin/post";
    }

    @GetMapping("/category")
    public String about(Model model) {
        List<CategoryDTO> listCate = categoryService.getListCategory();
        model.addAttribute("listCate", listCate);
        return "admin/category";
    }

    @GetMapping("/user")
    public String category() {
        return "admin/user";
    }
}

