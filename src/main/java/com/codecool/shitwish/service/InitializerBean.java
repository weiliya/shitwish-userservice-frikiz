package com.codecool.shitwish.service;

import com.codecool.shitwish.model.User;
import org.springframework.stereotype.Component;

@Component
public class InitializerBean {

    public InitializerBean(UserService userService){
        userService.add(new User("hello@hello.com", "hello"));
        userService.add(new User("szia@szia.hu", "szia"));
        userService.add(new User("hi@hi.org", "hi"));
        userService.add(new User("peti@peti.sk", "peti"));
        userService.add(new User("laci@laci.hu", "laci"));
    }

}
