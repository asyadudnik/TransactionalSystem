package com.optum.payment.system.controllers;

import com.optum.payment.system.entities.User;
import com.optum.payment.system.services.UserService;
import com.optum.payment.system.utils.JsonUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping(value = "/payment/api/users")
@Validated
public class UserController {
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/")
    public String listAll(ModelAndView model) {
        List<User> users = userService.findAll();
        model.addObject(users);
        try {
            users.forEach(usr ->
                    logger.info(JsonUtils.toJson(usr))
            );
            return "/users/usersList";
        } catch (Exception ex) {
            String errMsg = ex.getMessage();
            Map<String, String> errMap = new HashMap<>();
            errMap.put("error", errMsg);
            model.addObject(errMap);
            logger.error(errMsg);
            return "/errors/error";
        }
    }

    @GetMapping(value = "/new")
    public ModelAndView showNewUserPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("/users/new_user");
        User user = new User();
        modelAndView.addObject("user", user);
        logger.info(user.getFullName());
        Map<String, Object> map=new HashMap<>();
        map.put("user", user);
        modelAndView.addObject("map", map);
        return modelAndView;
    }

    @PostMapping(value = "/save")
    public String saveUser(@Valid @ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView("/users/new_user");

        try {
            userService.save(user);
            modelAndView.addObject("user", user);
            List<User> users = userService.findAll();
            modelAndView.addObject("users", users);
            users.forEach(usr ->
                    logger.info(JsonUtils.toJson(usr))
            );
            return "/users/edit_user";
        } catch (Exception ex) {
            String errMsg = ex.getMessage();
            modelAndView.addObject("errMsg", errMsg);
            return "/errors/error";
        }
    }

    @PostMapping(value = "/edit/{userId}", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public String showEditUserPage(@PathVariable(name = "userId") Long id) throws ChangeSetPersister.NotFoundException {
        ModelAndView modelAndView = new ModelAndView("/users/edit_user");
        User user = userService.get(id);
        if (user != null) {
            modelAndView.addObject("user", user);
            return modelAndView.getViewName();
        } else {
            var errMsg = "USER NOT FOUND";
            modelAndView.addObject("errMsg", errMsg);
            return "redirect:/errors/error";
        }
    }


    @DeleteMapping(value = "/delete/{id}", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public String deleteUser(@PathVariable(name = "id") Long id) throws ChangeSetPersister.NotFoundException {
        userService.delete(id);
        return "redirect:/";
    }
}
