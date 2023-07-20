package ru.ivanov.bootmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ivanov.bootmvc.model.Role;
import ru.ivanov.bootmvc.model.User;
import ru.ivanov.bootmvc.repository.RoleRepository;
import ru.ivanov.bootmvc.service.UserService;
import ru.ivanov.bootmvc.util.Init;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserService userService, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @ModelAttribute("user")
    public User getUser() {
        return new User();
    }

    @GetMapping("/admin")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/users/{id}")
    public String getById(@PathVariable("id") long id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("user", userService.getUserById(id));
        return "userById";
    }

    @GetMapping("/admin/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("allRoles", roleRepository.findAll());
        return "edit";
    }
    @Transactional
    @PatchMapping("/admin/{id}")
    public String updatePerson(@ModelAttribute("user") User updateUser, @RequestParam String[] selectedRoles) {
        String encodedPassword = passwordEncoder.encode(updateUser.getPassword());
        updateUser.setPassword(encodedPassword);
        for (String role : selectedRoles) {
            if (role.equals("ROLE_USER")) updateUser.getRoles().add(roleRepository.getRoleByName("ROLE_USER"));
            if (role.equals("ROLE_ADMIN")) updateUser.getRoles().add(roleRepository.getRoleByName("ROLE_ADMIN"));
            if (role.equals("ROLE_GUEST")) updateUser.getRoles().add(roleRepository.getRoleByName("ROLE_GUEST"));
        }
        userService.updateUser(updateUser);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String deletePerson(@PathVariable("id") long id) {
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