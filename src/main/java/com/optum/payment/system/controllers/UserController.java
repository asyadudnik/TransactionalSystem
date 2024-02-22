package com.optum.payment.system.controllers;

import com.optum.payment.system.entities.User;
import com.optum.payment.system.services.UserService;
import com.optum.payment.system.utils.JsonUtils;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    private static final String NAME_ERR_MESSAGE = "errMsg";

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/login/{id},{pwd}", produces = APPLICATION_JSON_VALUE)
    public User loginService(@PathVariable String id, @PathVariable String pwd) {
        return this.userService.register(id, pwd);
    }



    @GetMapping(value = "/", produces = APPLICATION_JSON_VALUE)
    public String listAll(ModelAndView model) {
        List<User> users = userService.findAll();
        logger.info("users size = {} ", users.size());
        Map<String, Object> map = new HashMap<>();
        map.put("users", users);

        model.addObject(map);
        try {
            users.forEach(usr ->
                    logger.info(JsonUtils.toJson(usr))
            );
            return "/users/usersList";
        } catch (Exception ex) {
            String errorMsg = ex.getMessage();
            Map<String, String> errMap = new HashMap<>();
            errMap.put(NAME_ERR_MESSAGE, errorMsg);
            model.addObject(errMap);
            logger.error(errorMsg);
            return "/errors/error";
        }
    }


    @GetMapping(value = "/new")
    public ModelAndView showNewUserPage() {
        ModelAndView modelAndView = new ModelAndView("/users/new_user");
        User user = new User();
        modelAndView.addObject("user", user);
        logger.info(user.getFullName());
        Map<String, Object> map = new HashMap<>();
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
            String errorMsg = ex.getMessage();
            modelAndView.addObject(NAME_ERR_MESSAGE, errorMsg);
            return "/errors/error";
        }
    }

    @PostMapping(value = "/edit/{userId}", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public String showEditUserPage(@PathVariable(name = "userId") Long id){
        ModelAndView modelAndView = new ModelAndView("/users/edit_user");
        User user = userService.get(id);
        if (user != null) {
            modelAndView.addObject("user", user);
            return modelAndView.getViewName();
        } else {
            var errorMsg = "USER NOT FOUND";
            modelAndView.addObject(NAME_ERR_MESSAGE, errorMsg);
            return "redirect:/errors/error";
        }
    }


    @DeleteMapping(value = "/delete/{id}", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public String deleteUser(@PathVariable(name = "id") Long id)  {
        userService.delete(id);
        return "redirect:/";
    }
}
