package com.codecool.shitwish.api;

import com.codecool.shitwish.model.User;
import com.codecool.shitwish.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.codecool.shitwish.model.Password;

@RestController
public class UserServiceREST {

    @Autowired
    UserService userService;


    @PostMapping("/user/log-user")
    public ResponseEntity logUser(@RequestParam("email") String email, @RequestParam("password") String password) {
        User user = userService.findByEmail(email);
        JSONObject object = new JSONObject();
        if (userService.loginUser(user.getId(), user.getPassword()) != null) {
            object.put("email", user.getEmail());
            object.put("userId", user.getId());
            return new ResponseEntity(object.toString(), HttpStatus.OK);
        } else {
            object.put("message", "Cant find a user with this email address");
            return new ResponseEntity(object.toString(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/user/reg-user")
    public ResponseEntity<String> saveUser(@RequestParam("email") String email, @RequestParam("password") String password) {
        String psw = Password.hashPassword(password);
        if  (userService.validateUser(email)) {
            userService.saveUser(psw, email);
            return new ResponseEntity("registered user",HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping("/user/get-user/{userId}")
    public ResponseEntity getUser(Model model, @PathVariable Long userId) {
        JSONObject responseObject = new JSONObject();
        User user = userService.getUser(userId);
        if (userService.getAll().contains(user)) {
            responseObject.put("email", user.getEmail());
            responseObject.put("id", user.getId());
            responseObject.put("order", user.getOrder());
            responseObject.put("rating", user.getAverageRating());
            responseObject.put("status", HttpStatus.OK);
            return new ResponseEntity<>(responseObject.toString(), HttpStatus.OK);
        } else {
            responseObject.put("message", "Didn't find user with this id");
            return new ResponseEntity<>(responseObject.toString(), HttpStatus.CONFLICT);
        }
    }


}
