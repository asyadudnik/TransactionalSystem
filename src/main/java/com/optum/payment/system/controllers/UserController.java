package com.optum.payment.system.controllers;

import com.optum.payment.system.entities.User;
import com.optum.payment.system.repositories.UserRepository;
import com.optum.payment.system.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.optum.payment.system.utils.JsonUtils.toJson;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping(value = "/payment/api/users")
@Validated
@Slf4j
public class UserController {
    public static final String ERR_MSG = "errMsg";
    public static final String ERR_PAGE = "/errors/error";
    public static final String USERS_PAGE = "/users/usersList";
    public static final String EDIT_PAGE = "/users/edit_user";
    public static final String NEW_PAGE = "/users/new_user";
    public static final String REDIRECT = "redirect:";

    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository) {

        this.userRepository = userRepository;
        this.userService = new UserService(userRepository);
    }


    @GetMapping(value = "/login/{id},{pwd}", produces = APPLICATION_JSON_VALUE)
    public User loginService(@PathVariable String id, @PathVariable String pwd) {
        return this.userService.register(id, pwd);
    }


    @GetMapping(value = "/", produces = APPLICATION_JSON_VALUE)
    public String listAll(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<User> users = userRepository.findAll(PageRequest.of(page, 10));
        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("viewName", USERS_PAGE);
        try {
            users.forEach(usr ->
                    log.info(toJson(usr))
            );
            return USERS_PAGE;
        } catch (Exception ex) {
            String exMessage = ex.getMessage();
            model.addAttribute(ERR_MSG, exMessage);
            model.addAttribute("viewName", ERR_PAGE);
            log.error(exMessage);
            return REDIRECT + ERR_PAGE;
        }
    }

    @GetMapping(value = "/new")
    public String showNewUserPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        if (log.isDebugEnabled()) {
            log.info(toJson(user));
        }
        return NEW_PAGE;
    }

    @PostMapping(value = "/save")
    public String saveUser(@Valid @ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView(USERS_PAGE);
        try {
            userService.save(user);
            modelAndView.addObject("user", user);
            List<User> users = userService.findAll();
            modelAndView.addObject("users", users);
            users.forEach(usr ->
                    log.info(toJson(usr))
            );
            return REDIRECT + USERS_PAGE;
        } catch (Exception ex) {
            String errMsg = ex.getMessage();
            modelAndView.addObject(ERR_MSG, errMsg);
            modelAndView.setViewName(ERR_PAGE);
            return REDIRECT + ERR_PAGE;
        }
    }

    @PostMapping(value = "/edit/{id}", produces = {APPLICATION_JSON_VALUE})
    public String showEditUserPage(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView(EDIT_PAGE);
        User user = userService.get(id);
        if (user != null) {
            modelAndView.addObject("user", user);
            return modelAndView.getViewName();
        } else {
            var errMsg = "USER NOT FOUND";
            modelAndView.addObject(ERR_MSG, errMsg);
            modelAndView.setViewName(ERR_PAGE);
            return REDIRECT + ERR_PAGE;
        }
    }


    @DeleteMapping(value = "/delete/{id}", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public String deleteUser(@PathVariable(name = "id") Long id) {
        userService.delete(id);
        return REDIRECT + USERS_PAGE;
    }

}
