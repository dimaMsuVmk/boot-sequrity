package ru.ivanov.bootmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ivanov.bootmvc.model.User;
import ru.ivanov.bootmvc.service.UserService;
import org.springframework.security.core.Authentication;

import java.util.Collections;

@Controller
public class HelloController {
    private final UserService userService;

    @Autowired
    public HelloController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public User getUser(){
        return new User();
    }
    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }
    @GetMapping("/users/{id}")
    public String getById(@PathVariable("id") long id, Model model) {
        model.addAttribute("id",id);
        model.addAttribute("user", userService.getUserById(id));
        return  "userById";
    }
    @GetMapping("/users/{id}/edit")
    public String edit (@PathVariable("id") long id,Model model){
        model.addAttribute("user",userService.getUserById(id));
        return "edit";
    }
    @PatchMapping("/users/{id}")
    public String updatePerson(@ModelAttribute("user") User updateuser){
        userService.updateUser(updateuser);
        return "redirect:/users";
    }
    @DeleteMapping("/users/{id}")
    public String deletePerson(@PathVariable("id") long id){
        userService.removeUserById(id);
        return "redirect:/users";
    }

    @PostMapping("/users")
    public String create(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:/users";
    }
    @GetMapping("/user")
    public String showUserInfo(Model model){
        //получаем объект Authentication, который мы положили в Cookie сессии при аутентификации
        //в классе AuthProviderImpl, в его методе configure
        //UsernamePasswordAuthenticationToken implements Authentication
        //return new UsernamePasswordAuthenticationToken(userDetails,password, Collections.emptyList());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //из Authentication(который является объектом UsernamePasswordAuthenticationToken) мы получим
        //доступ к UserDetails/User,который в него положили при аутентификации
        //UsernamePasswordAuthenticationToken(Object principal, Object credentials,Collection authorities)
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = (User) userDetails;
        model.addAttribute("user",user);
        return "user_info";
    }
}