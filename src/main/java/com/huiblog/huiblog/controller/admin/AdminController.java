package com.huiblog.huiblog.controller.admin;

import com.huiblog.huiblog.model.dto.CategoryDTO;
import com.huiblog.huiblog.model.dto.Paging;
import com.huiblog.huiblog.service.CategoryService;
import com.huiblog.huiblog.service.PostService;
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

    @Autowired
    private PostService postService;

    @GetMapping("")
    public String index() {
        return "admin/index";
    }

    @GetMapping(value = {"/post", "/post/{page}"})
    public String post(Model model, @PathVariable(required = false) Integer page) {
        int currPage = (page == null ? 0 : page - 1);
        Paging listPost = postService.getListPost(currPage);
        model.addAttribute("listPost", listPost);

        List<CategoryDTO> listCate = categoryService.getListCategory();
        model.addAttribute("listCate", listCate);

        return "admin/post";
    }

    @GetMapping(value = {"/post/search/", "/post/search/{page}"})
    public String searchPost(Model model, @PathVariable(required = false) Integer page, @RequestParam(required = false) String searchKey) {
        int currPage = (page == null ? 0 : page - 1);
        Paging listPost = postService.getListPostFTS(currPage, searchKey);
        model.addAttribute("listPost", listPost);

        model.addAttribute("search", true);
        model.addAttribute("searchKey", searchKey);

        List<CategoryDTO> listCate = categoryService.getListCategory();
        model.addAttribute("listCate", listCate);

        return "admin/post";
    }

    @GetMapping(value = {"/category", "/category/{page}"})
    public String category(Model model, @PathVariable(required = false) Integer page) {
        int currPage = (page == null ? 0 : page - 1);
        Paging listCate = categoryService.getListCategory(currPage);
        model.addAttribute("listCate", listCate);
        return "admin/category";
    }

    @GetMapping(value = {"/category/search/", "/category/search/{page}"})
    public String searchCategory(Model model, @PathVariable(required = false) Integer page, @RequestParam(required = false) String searchKey) {
        int currPage = (page == null ? 0 : page - 1);
        Paging listCate = categoryService.getListPostFTS(currPage, searchKey);
        model.addAttribute("listCate", listCate);
        model.addAttribute("search", true);
        model.addAttribute("searchKey", searchKey);
        return "admin/category";
    }

    @GetMapping("/user")
    public String category() {
        return "admin/user";
    }
}

