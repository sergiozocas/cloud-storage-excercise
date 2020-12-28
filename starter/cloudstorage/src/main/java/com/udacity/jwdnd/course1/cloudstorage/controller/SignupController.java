package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.data.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private UserService userService;

    public SignupController (UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getSignup(@ModelAttribute("signupForm") User user, Model model) {
        return "signup";
    }

    @PostMapping()
    public String postSignup(@ModelAttribute("signupForm") User user, Model model) {
        model.addAttribute("success", false);
        try {
            if (user.getPassword().equals(user.getUsername())) {
                model.addAttribute("message", "For security reasons username and password must be different!");
                return "signup";
            }
            if (userService.isUsernameAvailable(user.getUsername())) {
                if (userService.insertUser(user) > 0)
                    model.addAttribute("success", true);
                else
                    model.addAttribute("message", "");
            } else {
                model.addAttribute("message", "Username already exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "error");
        }
        return "signup";
    }

}
