package com.codecool.shitwish.api;

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
        session.setAttribute("email", email);
        // String userId = userService.getUserIdByUserName(name);
        //session.setAttribute("userId", userId);
        return "redirect:";
    }

    @PostMapping("/regUser")
    public String saveUser(Model model, @RequestParam("password") String psw, @RequestParam("email") String email) {
        model.addAttribute("title", "Login");
        String password = Password.hashPassword(psw);
        //userService.saveUser(name, psw,email);
        return "";
    }

    @PostMapping("/getUser/{userId}")
    public String updateUser(Model model, @PathVariable String userId) {
        model.addAttribute("email", userService.getUserEmail(userId));
        model.addAttribute("balance", userService.getBalance(userId));
        //userService.updateUserById(userId, editName, editedEmail);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("email");
        session.removeAttribute("userId");
        return "redirect:/";
    }


}
