package com.huiblog.huiblog.controller.admin;

import com.huiblog.huiblog.model.dto.CategoryDTO;
import com.huiblog.huiblog.model.dto.Paging;
import com.huiblog.huiblog.model.dto.UserDto;
import com.huiblog.huiblog.model.mapper.UserMapper;
import com.huiblog.huiblog.security.CustomUserDetails;
import com.huiblog.huiblog.service.CategoryService;
import com.huiblog.huiblog.service.CommentService;
import com.huiblog.huiblog.service.PostService;
import com.huiblog.huiblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @GetMapping("")
    public String index(Model model) {
        try {
            model.addAttribute("usersAmount", userService.getAmount());
            model.addAttribute("catesAmount", categoryService.getAmount());
            model.addAttribute("postsAmount", postService.getAmount());
            model.addAttribute("commentsAmount", commentService.getAmount());

            addUserToMoDel(model);

            return "admin/index";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping(value = {"/post", "/post/{page}"})
    public String post(Model model, @PathVariable(required = false) String page) {
        try {
            int pageTemp = 0;
            if(page != null) {
                pageTemp = Integer.parseInt(page);
            }
            int currPage = (page == null ? 0 : pageTemp - 1);
            Paging listPost = postService.getListPost(currPage);
            model.addAttribute("listPost", listPost);

            List<CategoryDTO> listCate = categoryService.getListCategory();
            model.addAttribute("listCate", listCate);

            addUserToMoDel(model);

            return "admin/post";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping(value = {"/post/search/", "/post/search/{page}"})
    public String searchPost(Model model, @PathVariable(required = false) String page, @RequestParam(required = false) String searchKey) {
        try {
            int pageTemp = 0;
            if(page != null) {
                pageTemp = Integer.parseInt(page);
            }
            int currPage = (page == null ? 0 : pageTemp - 1);
            Paging listPost = postService.getListPostFTS(currPage, searchKey);
            model.addAttribute("listPost", listPost);

            model.addAttribute("search", true);
            model.addAttribute("searchKey", searchKey);

            List<CategoryDTO> listCate = categoryService.getListCategory();
            model.addAttribute("listCate", listCate);

            addUserToMoDel(model);

            return "admin/post";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping(value = {"/category", "/category/{page}"})
    public String category(Model model, @PathVariable(required = false) String page) {
        try {
            int pageTemp = 0;
            if(page != null) {
                pageTemp = Integer.parseInt(page);
            }
            int currPage = (page == null ? 0 : pageTemp - 1);
            Paging listCate = categoryService.getListCategory(currPage);
            model.addAttribute("listCate", listCate);

            addUserToMoDel(model);

            return "admin/category";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping(value = {"/category/search/", "/category/search/{page}"})
    public String searchCategory(Model model, @PathVariable(required = false) String page, @RequestParam(required = false) String searchKey) {
        try {
            int pageTemp = 0;
            if(page != null) {
                pageTemp = Integer.parseInt(page);
            }
            int currPage = (page == null ? 0 : pageTemp - 1);
            Paging listCate = categoryService.getListPostFTS(currPage, searchKey);
            model.addAttribute("listCate", listCate);
            model.addAttribute("search", true);
            model.addAttribute("searchKey", searchKey);

            addUserToMoDel(model);

            return "admin/category";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping(value = {"/user", "/user/{page}"})
    public String user(Model model, @PathVariable(required = false) String page) {
        try {
            int pageTemp = 0;
            if(page != null) {
                pageTemp = Integer.parseInt(page);
            }
            int currPage = (page == null ? 0 : pageTemp - 1);
            Paging listUser = userService.getlistUser(currPage);
            model.addAttribute("listUser", listUser);

            addUserToMoDel(model);

            return "admin/user";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping(value = {"/user/search/", "/user/search/{page}"})
    public String searchUser(Model model, @PathVariable(required = false) String page, @RequestParam(required = false) String searchKey) {
        try {
            int pageTemp = 0;
            if(page != null) {
                pageTemp = Integer.parseInt(page);
            }
            int currPage = (page == null ? 0 : pageTemp - 1);
            Paging listUser = userService.getListUserFTS(currPage, searchKey);
            model.addAttribute("listUser", listUser);
            model.addAttribute("search", true);
            model.addAttribute("searchKey", searchKey);

            addUserToMoDel(model);

            return "admin/user";
        } catch (Exception e) {
            return "error";
        }
    }

    public void addUserToMoDel(Model model) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userDto = UserMapper.toUserDto(user.getUser());
        model.addAttribute("info", userDto);
    }
}

