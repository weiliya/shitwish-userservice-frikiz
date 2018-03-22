package com.codecool.shitwish.api;

import com.codecool.shitwish.model.Password;
import com.codecool.shitwish.model.User;
import com.codecool.shitwish.service.UserService;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.http.HTTPException;

@RestController
public class UserServiceREST {

    @Autowired
    UserService userService;

    @PostMapping("/user/log-user")
    public String logUser(@RequestParam("email") String email, @RequestParam("password") String password) {
        User user = userService.findByEmail(email);
        Gson gson = new Gson();
        JSONObject object = new JSONObject();
        try {
            if (userService.loginUser(user.getId(), user.getPassword()) != null) {
                object.put("email", user.getEmail());
                object.put("userId", user.getId());
                return gson.toJson(object);
            } else {
                return "failed";
            }
        } catch (HTTPException e) {
            return "unexpected";
        }
    }

    @PostMapping("/user/reg-user")
    public String saveUser(@RequestParam("email") String email, @RequestParam("password") String password) {
        String psw = Password.hashPassword(password);
        Gson gson = new Gson();
        try {
            if (!userService.validateUser(email)) {
                userService.saveUser(psw, email);
                return "success";
            } else {
                return "exists";
            }
        } catch (HTTPException e) {
            return "unexpected";
        }
    }

    @GetMapping("/user/get-user/{userId}")
    public String getUser(Model model, @PathVariable Long userId) {
        Gson gson = new Gson();
        User user = userService.getUser(userId);
        if (userService.getAll().contains(user)) {
            return gson.toJson(user);
        } else {
            return gson.toJson("failure");
        }
    }

}
