package com.codecool.shitwish.api;

import com.codecool.shitwish.model.User;
import com.codecool.shitwish.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.codecool.shitwish.model.Password;

import javax.servlet.http.HttpSession;

@RestController
public class UserServiceREST {

    @Autowired
    UserService userService;


    @GetMapping("/login")
    public String loadLoginPage(Model model) {
        model.addAttribute("title", "Login");
        return "user/login";
    }

    @PostMapping("/logUser")
    public String insertToSession(Model model, @RequestParam("email") String email, HttpSession session) {
        String userId = userService.getUser(Long.valueOf(session.getAttribute("userId")).getId());
        if (userService.loginUser(userId, session.getAttribute("password")) != null) {
            session.setAttribute("email", email);
            session.setAttribute("userId", userId);
           return "redirect:/";
        } else {
            model.addAttribute("Login", "Wrong email or password");
            return "redirect:/";
        }
    }

    @PostMapping("/logUser")
    public String loginUser(Model model,@RequestParam("email") String email, HttpSession session ) {
        if ( userService.loginUser(session.getAttribute("password"))

    }


    @PostMapping("/regUser")
    public String saveUser(Model model, @RequestParam("password") String psw, @RequestParam("email") String email) {
        model.addAttribute("title", "Login");
        String password = Password.hashPassword(psw);
        if  (userService.validateUser(email, password)) {
            userService.saveUser(email, psw);
            return "";
        } else {
            model.addAttribute("validate", "Already exists a user with this email address");
            return "redirect:/";
        }
    }

    @PostMapping("/getUser/{userId}")
    public String getUser(Model model, @PathVariable Long userId) {
        User user = userService.getUser(userId);
        model.addAttribute("email", user.getEmail());
        model.addAttribute("balance", user.getBalance());
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("email");
        session.removeAttribute("userId");
        return "redirect:/";
    }


}
