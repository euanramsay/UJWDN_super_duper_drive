package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String signupUser(@ModelAttribute User user, Model model , RedirectAttributes redirect) {
        if (userService.isUserNameAvailable(user.getUsername())) {
            Integer dbEntries = userService.createUser(user);
            if (dbEntries < 0) {
                redirect.addAttribute("error", "There was a problem signing you up");
            }
            redirect.addAttribute("success", true);
            return "redirect:/login";
        } else {
            redirect.addAttribute("error", "Username already exists");
        } return "redirect:/signup";
    }
}
