package ru.kata.spring.boot_security.demo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String listUsers(Model model) {
        List<User> userList = userService.listUsers();
        model.addAttribute("usersAttribute", userList);
        return "usersActions";
    }

    @GetMapping("/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        return "addUser";
    }

    @PostMapping("/add")
    public String addUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, @RequestParam("roleIds") List<Long> roleIds, Model model) {
        if (bindingResult.hasErrors()) {
            List<Role> roles = roleService.getAllRoles();
            model.addAttribute("roles", roles);
            return "addUser";
        }
        List<Role> roles = new ArrayList<>(roleService.getRolesByIds(roleIds));
        userService.add(user, roles);
        return "redirect:/admin";
    }

    @GetMapping("/update")
    public String showUpdateForm(@RequestParam Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "editUser";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("roleIds") List<Long> roleIds, Model model) {
        if (user == null || user.getId() == null) {
            model.addAttribute("Error", "Пользователь не найден");
            return "editUser";
        }
        List<Role> roles = roleService.getRolesByIds(roleIds);
        if (roles.isEmpty()) {
            model.addAttribute("error", "Роли не найдены");
            model.addAttribute("user", user);
            model.addAttribute("roles", roleService.getAllRoles());
            return "editUser";
        }
        userService.update(user, roles);
        return "redirect:/admin";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
