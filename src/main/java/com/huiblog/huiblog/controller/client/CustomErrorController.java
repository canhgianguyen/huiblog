package com.huiblog.huiblog.controller.client;

import com.huiblog.huiblog.model.dto.CategoryDTO;
import com.huiblog.huiblog.model.dto.UserDto;
import com.huiblog.huiblog.model.mapper.UserMapper;
import com.huiblog.huiblog.security.CustomUserDetails;
import com.huiblog.huiblog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/")
@Controller
public class CustomErrorController implements ErrorController {
    @Autowired
    private CategoryService categoryService;

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @GetMapping("/error")
    public String handleError(Model model, HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        addListCateToModel(model);
        isUser(model);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error-404";
            }
        }
        return "error";
    }

    private void addListCateToModel(Model model) {
        List<CategoryDTO> listCate = categoryService.getListCategory();
        model.addAttribute("listCate", listCate);
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
