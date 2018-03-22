package com.codecool.shitwish.api;

import com.codecool.shitwish.model.Password;
import com.codecool.shitwish.model.User;
import com.codecool.shitwish.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.http.HTTPException;

@RestController
public class UserServiceREST {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/user/log-user", method = RequestMethod.POST)
    public ResponseEntity logUser(@RequestParam("email") String email, @RequestParam("password") String password) {
        User user = userService.findByEmail(email);
        Gson gson = new Gson();
        try {
            if (userService.loginUser(user.getId(), user.getPassword()) != null) {
                return new ResponseEntity(gson.toJson(user), HttpStatus.OK);
            } else {
                return new ResponseEntity("failure", HttpStatus.BAD_REQUEST);
            }
        } catch (HTTPException e) {
            return new ResponseEntity("unexpected", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/user/reg-user", method = RequestMethod.POST)
    public ResponseEntity saveUser(@RequestParam("email") String email, @RequestParam("password") String password) {
        String psw = Password.hashPassword(password);
        Gson gson = new Gson();
        try {
            if (!userService.validateUser(email)) {
                userService.saveUser(psw, email);
                return new ResponseEntity("success", HttpStatus.OK);
            } else {
                return new ResponseEntity("failed", HttpStatus.OK);
            }
        } catch (HTTPException e) {
            return new ResponseEntity("unexpected", HttpStatus.OK);
        }
    }

    @GetMapping("/user/get-user/{userId}")
    public ResponseEntity getUser(Model model, @PathVariable Long userId) {
        Gson gson = new Gson();
        User user = userService.getUser(userId);
        if (userService.getAll().contains(user)) {
            return new ResponseEntity(gson.toJson(user), HttpStatus.OK);
        } else {
            return  new ResponseEntity(gson.toJson("failure"), HttpStatus.OK);
        }
    }

}
