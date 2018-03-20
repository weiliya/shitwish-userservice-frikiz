package com.codecool.shitwish.api;

import com.codecool.shitwish.model.User;
import com.codecool.shitwish.service.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.codecool.shitwish.model.Password;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.net.URI;

@RestController
@Scope("session")
public class UserServiceREST {

    @Autowired
    UserService userService;


    @PostMapping("/user/logUser/{userId}")
    public ResponseEntity logUser(Model model,@PathVariable Long userId) {
        User user = userService.find(userId);
        JSONObject object = new JSONObject();
        if (userService.loginUser(userId, user.getPassword()) != null) {
            object.put("email", user.getEmail());
            object.put("userId", userId);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            object.put("status", HttpStatus.NOT_FOUND);
            object.put("message", "Cant find a user with this email address");
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }


    @PostMapping("/user/regUser")
    public ResponseEntity saveUser(Model model) {
        final String endpoint = "";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        JSONObject response = null;
        headers.add("OS 10_3_1", "Safari/602.1");
        RequestEntity<String> request = new RequestEntity<>(
                headers, HttpMethod.GET, URI.create(endpoint));
        JSONObject responseObject = new JSONObject();
        String email = null;
        String psw = null;
        try {
            response = restTemplate.exchange(request, JSONObject.class).getBody();
            JSONObject object = new JSONObject(response);
            email = object.getString("email");
            psw = Password.hashPassword(object.getString("password"));
        } catch (HttpClientErrorException e) {
            System.out.println("Client side error occurred in AiController");

        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Json parse exception in AiController");
        }
        if  (userService.validateUser(email, psw)) {
            userService.saveUser(email, psw);
            return new ResponseEntity(HttpStatus.OK);

        } else {
            responseObject.put("message", "Already exists a user with this email address");
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/getUser/{userId}")
    public JSONObject getUser(Model model, @PathVariable Long userId) {
        JSONObject responseObject = new JSONObject();
        User user = userService.getUser(userId);
        if (userService.getAll().contains(user)) {
            responseObject.put("email", user.getEmail());
            responseObject.put("id", user.getId());
            responseObject.put("order", user.getOrder());
            responseObject.put("rating", user.getAverageRating());
            responseObject.put("status", HttpStatus.OK);
            return responseObject;
        } else {
            responseObject.put("message", "Didnt find user with this Id");
            return responseObject;
        }
    }

    @GetMapping("/logout")
    public ResponseEntity logout(HttpSession session) {
        session.removeAttribute("email");
        session.removeAttribute("userId");
        return new ResponseEntity(HttpStatus.OK);
    }


}
