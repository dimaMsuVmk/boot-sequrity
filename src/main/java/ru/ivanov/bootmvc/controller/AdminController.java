package ru.ivanov.bootmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ivanov.bootmvc.model.User;
import ru.ivanov.bootmvc.service.UserService;

@Controller
public class AdminController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @ModelAttribute("user")
    public User getUser(){
        return new User();
    }
    @GetMapping("/admin")
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
    @GetMapping("/admin/{id}/edit")
    public String edit (@PathVariable("id") long id,Model model){
        model.addAttribute("user",userService.getUserById(id));
        return "edit";
    }
    @PatchMapping("/admin/{id}")
    public String updatePerson(@ModelAttribute("user") User updateUser){
        String encodedPassword = passwordEncoder.encode(updateUser.getPassword());
        updateUser.setPassword(encodedPassword);
        userService.updateUser(updateUser);
        return "redirect:/admin";
    }
    @DeleteMapping("/admin/{id}")
    public String deletePerson(@PathVariable("id") long id){
        userService.removeUserById(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin")
    public String create(@ModelAttribute User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userService.save(user);
        return "redirect:/admin";
    }
}