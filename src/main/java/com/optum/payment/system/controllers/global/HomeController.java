package com.optum.payment.system.controllers.global;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Locale;

@Controller
@RequestMapping(value={"/payment/api/"})
public class HomeController {
    @RequestMapping(value={"/home"})
    public String root(Locale locale, ModelMap model) {
        model.addAttribute("content", "content");
        return "home";
    }
    @RequestMapping("/404.html")
    String pageNotFound(Model model, HttpServletRequest request)
    {
        return "404";
    }
}
