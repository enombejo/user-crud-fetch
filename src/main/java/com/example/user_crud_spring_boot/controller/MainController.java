package com.example.user_crud_spring_boot.controller;


import com.example.user_crud_spring_boot.model.Role;
import com.example.user_crud_spring_boot.model.User;
import com.example.user_crud_spring_boot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
//@RequestMapping("/")
public class MainController {

    @Autowired
    UserService userService;

    @GetMapping("login")
    public String loginPage() {

        return "login";
    }


    @GetMapping("admin")
    public String getPage(ModelMap model, Principal principal) {
        model.addAttribute("principal", userService.getUserByName(principal.getName()));
        model.addAttribute("users", userService.listUser());
        return "admin";
    }


    @GetMapping("user")
    public String getUser(ModelMap model, Principal userS) {
        User user = userService.getUserByName(userS.getName());
        model.addAttribute("principal", user);
        model.addAttribute("user", user);
        System.out.println(user.getRoles().contains("ROLE_ADMIN"));
        //boolean containsRole = false;
        //user.getRoles().forEach(n -> containsRole |= n.getRole().equals("ROLE_ADMIN"));
        if (user.getRoles().contains(new Role("ROLE_ADMIN", user))) return "user_admin";
        else return "user";
        //model.addAttribute("messages", user.toString());
    }

}


