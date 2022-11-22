package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class SignUpController {

    @Autowired
    private UserService userService;

    @GetMapping("/signUp")
    public String registrationNewUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "signUp";
    }
}
