package com.codecool.shitwish.api;

import com.codecool.shitwish.model.User;
import com.codecool.shitwish.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.codecool.shitwish.model.Password;

import javax.servlet.http.HttpSession;

@RestController
public class UserServiceREST {

    @Autowired
    UserService userService;


    @PostMapping("/logUser")
    public JSONObject insertToSession(Model model, @RequestParam("email") String email, HttpSession session) {
        String userId = userService.getUser(Long.valueOf(session.getAttribute("userId")).getId());
        if (userService.loginUser(userId, session.getAttribute("password")) != null) {
            session.setAttribute("email", email);
            session.setAttribute("userId", userId);
            JSONObject object = new JSONObject()
                    .put("email", email)
                    .put("userId", userId)
                    .put("status", HttpStatus.OK);

           return object ;
        } else {
            JSONObject object = new JSONObject()
                    .put("status", HttpStatus.NOT_FOUND);
            return object;
        }
    }


    @PostMapping("/regUser")
    public JSONObject saveUser(Model model, @RequestParam("password") String psw, @RequestParam("email") String email) {
        model.addAttribute("title", "Login");
        String password = Password.hashPassword(psw);
        if  (userService.validateUser(email, password)) {
            userService.saveUser(email, psw);
            JSONObject object = new JSONObject()
                    .put("status", HttpStatus.OK);
            return object;

        } else {
            JSONObject object = new JSONObject()
                    .put("status", HttpStatus.NOT_FOUND);
            return object;
        }
    }

    @PostMapping("/getUser/{userId}")
    public JSONObject getUser(Model model, @PathVariable Long userId) {
        User user = userService.getUser(userId);
        if (userService.getAllUser().contains(user)) {
            JSONObject object = new JSONObject()
                    .put("email", user.getEmail())
                    .put("id", user.getId())
                    .put("order", user.getOrder())
                    .put("rating", user.getAvarageRating())
                    .put("status", HttpStatus.OK);
            return object;
        } else {
            JSONObject object = new JSONObject()
                    .put("status", HttpStatus.NOT_FOUND);
            return object;
        }
       
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("email");
        session.removeAttribute("userId");
        return "redirect:/";
    }


}
