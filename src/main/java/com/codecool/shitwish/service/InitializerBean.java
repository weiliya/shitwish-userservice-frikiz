package com.codecool.shitwish.service;


import com.codecool.shitwish.model.User;
import org.springframework.stereotype.Component;

@Component
public class InitializerBean {


    public InitializerBean(UserService userService) {
        userService.saveUser("vm", "j");
        userService.saveUser("ccc", "m");
    }
}
