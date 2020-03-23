package com.huiblog.huiblog.controller.client;

import com.huiblog.huiblog.model.dto.CategoryDTO;
import com.huiblog.huiblog.model.dto.Paging;
import com.huiblog.huiblog.model.dto.PostDTO;
import com.huiblog.huiblog.model.dto.UserDto;
import com.huiblog.huiblog.model.mapper.UserMapper;
import com.huiblog.huiblog.security.CustomUserDetails;
import com.huiblog.huiblog.service.CategoryService;
import com.huiblog.huiblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/")
@Controller
public class ClientController {
    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = {"", "/{page}"})
    public String index(Model model, @PathVariable(required = false) Integer page) {
        int currPage = (page == null ? 0 : page - 1);

        addListPostToModel(currPage, model);

        addListPostToModelSideBar(model);

        addListCateToModel(model);

        isUser(model);

        return "index";
    }

    @GetMapping(value = {"/p/search", "/p/search/{page}"})
    public String search(Model model, @PathVariable(required = false) Integer page, @RequestParam(required = false) String searchKey) {
        int currPage = (page == null ? 0 : page - 1);
        Paging listPost = postService.getListPostFTS(currPage, searchKey);
        model.addAttribute("listPost", listPost);

        addListPostToModelSideBar(model);

        model.addAttribute("search", true);
        model.addAttribute("searchKey", searchKey);

        addListCateToModel(model);

        isUser(model);

        return "index";
    }

    @GetMapping("/p/{metaTitle}")
    public String post(Model model, @PathVariable(required = true) String metaTitle) {
        PostDTO postDTO = postService.getPostByMetaTitle(metaTitle);
        model.addAttribute("post", postDTO);

        addListPostToModelSideBar(model);

        addListCateToModel(model);

        isUser(model);

        return "post";
    }

    @GetMapping(value = {"/c/{metaName}", "/c/{metaName}/{page}"})
    public String cate(Model model, @PathVariable(required = true) String metaName, @PathVariable(required = false) Integer page) {
        int currPage = (page == null ? 0 : page - 1);
        Paging listPost = categoryService.getListPostByCategoryMetaName(metaName, currPage);
        model.addAttribute("listPost", listPost);
        model.addAttribute("metaName", "/c/" + metaName);

        addListPostToModelSideBar(model);

        addListCateToModel(model);

        isUser(model);

        return "category";
    }

    @GetMapping("/about")
    public String about(Model model) {
        addListCateToModel(model);

        isUser(model);

        return "about-me";
    }

    @GetMapping("/category")
    public String category(Model model) {
        addListCateToModel(model);

        isUser(model);

        return "category";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        addListCateToModel(model);

        isUser(model);

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

    private void addListCateToModel(Model model) {
        List<CategoryDTO> listCate = categoryService.getListCategory();
        model.addAttribute("listCate", listCate);
    }

    private void addListPostToModel(int currPage, Model model) {
        Paging listPost = postService.getListPost(currPage);
        model.addAttribute("listPost", listPost);
    }

    private void addListPostToModelSideBar(Model model) {
        Paging listPost = postService.getListPost(0);
        model.addAttribute("listPostSideBar", listPost);
    }

    private void isUser(Model model) {
        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            model.addAttribute("isUser", true);
            addUserToMoDel(model);
        }
    }

    public void addUserToMoDel(Model model) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userDto = UserMapper.toUserDto(user.getUser());
        model.addAttribute("info", userDto);
    }
}
