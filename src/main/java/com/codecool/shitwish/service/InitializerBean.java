package com.codecool.shitwish.service;


import com.codecool.shitwish.model.User;
import org.springframework.stereotype.Component;

@Component
public class InitializerBean {


    public InitializerBean(UserService userService) {
        userService.saveUser("kalap", "gomb@a.hu");
        userService.saveUser("banan", "ana@nasz.hu");
    }
}
