package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String findAllUsers(Model model) {
        List<User> usersList = userService.allUsers();
        model.addAttribute("userList", usersList);
        return "admin";
    }

    @PostMapping(value = "/add")
    public String addUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/createdUser")
    public String newUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "add";
    }

    @RequestMapping(value = "/edit/{id}")
    public ModelAndView editPage(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("edit");
        User user = userService.findUserById(id);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
