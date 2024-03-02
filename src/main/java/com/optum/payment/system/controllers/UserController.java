package com.optum.payment.system.controllers;

import com.optum.payment.system.entities.User;
import com.optum.payment.system.repositories.UserRepository;
import com.optum.payment.system.services.UserService;
import com.optum.payment.system.utils.JsonUtils;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping(value = "/payment/api/users")
@Validated
public class UserController {
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);
    public static final String ERR_MSG = "errMsg";
    public static final String ERR_PAGE = "/errors/error";
    public static final String USERS_PAGE = "/users/usersList";
    public static final String EDIT_PAGE = "/users/edit_user";
    public static final String NEW_PAGE = "/users/new_user";

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
                    logger.info(JsonUtils.toJson(usr))
            );
            return USERS_PAGE;
        } catch (Exception ex) {
            String exMessage = ex.getMessage();
            model.addAttribute(ERR_MSG, exMessage);
            model.addAttribute("viewName", ERR_PAGE);
            logger.error(exMessage);
            return "redirect:" + ERR_PAGE;
        }
    }

    @GetMapping(value = "/new")
    public ModelAndView showNewUserPage(Model model) {
        ModelAndView modelAndView = new ModelAndView(NEW_PAGE);
        Map<String, Object> attributes = model.asMap();
        if (attributes.isEmpty()) {
            modelAndView.addObject("user", new User());
        } else {
            attributes.forEach(modelAndView::addObject);
        }
        return modelAndView;
    }

    @PostMapping(value = "/save")
    public String saveUser(@Valid @ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView(EDIT_PAGE);
        try {
            userService.save(user);
            modelAndView.addObject("user", user);
            List<User> users = userService.findAll();
            modelAndView.addObject("users", users);
            users.forEach(usr ->
                    logger.info(JsonUtils.toJson(usr))
            );
            return EDIT_PAGE;
        } catch (Exception ex) {
            String errMsg = ex.getMessage();
            modelAndView.addObject(ERR_MSG, errMsg);
            modelAndView.setViewName(ERR_PAGE);
            return ERR_PAGE;
        }
    }

    @GetMapping(value = "/edit/{id}", produces = {APPLICATION_JSON_VALUE})
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
            return ERR_PAGE;
        }
    }


    @DeleteMapping(value = "/delete/{id}", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public String deleteUser(@PathVariable(name = "id") Long id) {
        userService.delete(id);
        return "redirect:/";
    }

}
