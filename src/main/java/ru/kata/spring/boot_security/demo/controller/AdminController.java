package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.Arrays;

@Controller
public class AdminController {
    private UserServiceImpl myUserService;
    private RoleServiceImpl myRoleService;

    public AdminController(UserServiceImpl myUserService, RoleServiceImpl myRoleService) {
        this.myUserService = myUserService;
        this.myRoleService = myRoleService;
    }

    @GetMapping("/admin")
    public String showAdminPage(Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("users", myUserService.getUsers());
        model.addAttribute("currentuser", currentUser);
        model.addAttribute("newuser", new User());
        model.addAttribute("allroles", myRoleService.getAllRoles());
        return "admin";
    }

    @RequestMapping("/admin/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        myUserService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/user")
    public String showAdminPageForUser(Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("currentuser", currentUser);

        return "adminUserView";
    }


    @GetMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") long id, Model model) {
        myUserService.removeUser(id);
        return "redirect:/admin";
    }


    @PostMapping("/admin/updateUser")
    public String updateUser(@ModelAttribute("user") User user) {
        myUserService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/adminEdit/{id}")
    public String openEditModal(@PathVariable(value = "id") long id, Model model) {
        User viewUser = myUserService.getUserById(id);
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("users", myUserService.getUsers());
        model.addAttribute("currentuser", currentUser);
        model.addAttribute("viewuser", viewUser);
        model.addAttribute("userroles", Arrays.asList(viewUser.getRoles().toArray()));
        model.addAttribute("allroles", myRoleService.getAllRoles());
        return "adminEditWindow";
    }

    @GetMapping("/admin/adminDelete/{id}")
    public String openDeleteModal(@PathVariable(value = "id") long id, Model model) {
        User viewUser = myUserService.getUserById(id);
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("users", myUserService.getUsers());
        model.addAttribute("currentuser", currentUser);
        model.addAttribute("viewuser", viewUser);
        model.addAttribute("userroles", Arrays.asList(viewUser.getRoles().toArray()));
        return "adminDeleteWindow";
    }



}
