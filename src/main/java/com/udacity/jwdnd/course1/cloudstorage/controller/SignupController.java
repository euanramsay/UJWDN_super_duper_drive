package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignupController {

    private UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String getSignupView() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signupUser(@ModelAttribute User user, Model model) {
        if( userService.isUserNameAvailable(user.getUsername())) {
            Integer dbEntries = userService.createUser(user);
            if (dbEntries < 0) {
                model.addAttribute("error", "There was a problem signing you up");
            }
            model.addAttribute("success", true);
        } else {
            model.addAttribute("error", "Username already exists");
        } return "signup";
    }
}
