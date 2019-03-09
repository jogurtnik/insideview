package uk.co.punishell.insideview.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/showLoginPage")
    public String showLoginPage() {

        return "loginPage";
    }

    // Login form with error
    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginFailureMessage", "Incorrect username or password.");
        return "loginPage";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/logout-success";
    }

    @GetMapping("/logout-success")
    public String loginLogoutSuccess(Model model) {
        model.addAttribute("logoutSuccessMessage", "You have been successfully logged out.");
        return "loginPage";
    }
}
