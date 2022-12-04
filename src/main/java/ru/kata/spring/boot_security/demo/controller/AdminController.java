package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private  final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String findAllUsers(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("users", userService.allUsers());
        model.addAttribute("user", authentication.getPrincipal());
        return "admin/admin";
    }

//    @PostMapping("/add")
//    public String addUser(@ModelAttribute("user") User user) {
//        userService.saveUser(user);
//        return "redirect:/admin";
//    }

    @GetMapping("/add")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getRoles());
        return "add";
    }

//    @GetMapping(value = "/edit/{id}")
//    public ModelAndView editPage(@PathVariable(name = "id") Long id) {
//        ModelAndView modelAndView = new ModelAndView("edit");
//        modelAndView.addObject("user", userService.findUserById(id));
//        modelAndView.addObject("roles",roleService.getRoles());
//        return modelAndView;
//    }

//    @PostMapping(value = "/edit")
//    public String editUser(@ModelAttribute("user") User user) {
//        userService.editUser(user);
//        return "redirect:/admin";
//    }

    @PostMapping(value = "/edit")
    public String editUser(@RequestParam Long id, @RequestParam String firstName,
                           @RequestParam String lastName, @RequestParam String age,
                           @RequestParam String email, @RequestParam String password,
                           @RequestParam(required = false) List<String> roleList) {
        User user = userService.findUserById(id);
        user.setName(firstName);
        user.setLastName(lastName);
        user.setAge(Byte.parseByte(age));
        user.setEmail(email);
        user.setPassword(password);
        if (roleList != null) {
            user.setRoles(new HashSet<>());
            for (String s : roleList) {
                user.getRoles().add(roleService.findRoleByName(s));
            }
        }
        userService.editUser(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/add")
    public String addUser(@RequestParam String firstName, @RequestParam String lastName, @RequestParam byte age,
                          @RequestParam String email, @RequestParam String password, @RequestParam Set<String> roleList) {
        User user = new User(firstName, lastName, age, email, password);
        user.setRoles(new HashSet<>());
        for (String s : roleList) {
            user.getRoles().add(roleService.findRoleByName(s));
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
    @GetMapping(value = "/user-admin")
    public String user(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByUserName(authentication.getName());
        model.addAttribute("user", user);
        return "admin/user-admin";
    }
}
