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
    public String index(Model model, @PathVariable(required = false) String page) {
        try {
            int pageTemp = 0;
            if(page != null) {
                pageTemp = Integer.parseInt(page);
            }
            int currPage = (page == null ? 0 : pageTemp - 1);

            addListPostToModel(currPage, model);
            addListPostToModelSideBar(model);
            addListCateToModel(model);
            isUser(model);

            return "index";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping(value = {"/p/search", "/p/search/{page}"})
    public String search(Model model, @PathVariable(required = false) String page, @RequestParam(required = false) String searchKey) {
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

            addListPostToModelSideBar(model);
            addListCateToModel(model);
            isUser(model);

            return "index";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/p/{metaTitle}")
    public String post(Model model, @PathVariable(required = true) String metaTitle) {
        try {
            PostDTO postDTO = postService.getPostByMetaTitle(metaTitle);
            model.addAttribute("post", postDTO);

            addListPostToModelSideBar(model);
            addListCateToModel(model);
            isUser(model);

            return "post";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping(value = {"/c/{metaName}", "/c/{metaName}/{page}"})
    public String cate(Model model, @PathVariable(required = true) String metaName, @PathVariable(required = false) String page) {
        try {
            int pageTemp = 0;
            if(page != null) {
                pageTemp = Integer.parseInt(page);
            }
            int currPage = (page == null ? 0 : pageTemp - 1);
            Paging listPost = categoryService.getListPostByCategoryMetaName(metaName, currPage);
            model.addAttribute("listPost", listPost);
            model.addAttribute("metaName", "/c/" + metaName);

            addListPostToModelSideBar(model);
            addListCateToModel(model);
            isUser(model);

            return "category";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/about")
    public String about(Model model) {
        try {
            addListCateToModel(model);
            isUser(model);

            return "about-me";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/category")
    public String category(Model model) {
        try {
            addListCateToModel(model);
            isUser(model);

            return "category";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        try {
            addListCateToModel(model);
            isUser(model);

            return "contact";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/signin")
    public String signIn(Model model) {
        try {
            addListCateToModel(model);
            isUser(model);

            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
                return "signin";
            }
            return "signedin";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/signup")
    public String signUp(Model model) {
        try {
            addListCateToModel(model);
            isUser(model);

            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
                return "signup";
            }
            return "signedin";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/403")
    public String forbidden() {
        try {
            return "403";
        } catch (Exception e) {
            return "error";
        }
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
