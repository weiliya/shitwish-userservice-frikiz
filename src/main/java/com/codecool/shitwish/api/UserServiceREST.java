package com.codecool.shitwish.api;

import com.codecool.shitwish.model.User;
import com.codecool.shitwish.service.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import com.codecool.shitwish.model.Password;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URI;

@RestController
@Scope("session")
public class UserServiceREST {

    @Autowired
    UserService userService;


    @GetMapping("/user/log-user/{userId}")
    public ResponseEntity logUser(Model model,@PathVariable Long userId) {
        User user = userService.find(userId);
        JSONObject object = new JSONObject();
        if (userService.loginUser(userId, user.getPassword()) != null) {
            object.put("email", user.getEmail());
            object.put("userId", userId);
            return new ResponseEntity(object.toString(), HttpStatus.OK);
        } else {
            object.put("message", "Cant find a user with this email address");
            return new ResponseEntity(object.toString(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/user/reg-user")
    public ResponseEntity<HttpStatus> saveUser(@RequestBody String email, @RequestBody String password) {
        System.out.println(email);
        System.out.println(password);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/get-user/{userId}")
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
            responseObject.put("message", "Didnt find user with this Id");
            return new ResponseEntity<>(responseObject.toString(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity logout(HttpSession session) {
        session.removeAttribute("email");
        session.removeAttribute("userId");
        return new ResponseEntity(HttpStatus.OK);
    }


}
