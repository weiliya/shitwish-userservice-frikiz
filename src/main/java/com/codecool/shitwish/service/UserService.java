package com.codecool.shitwish.service;

import com.codecool.shitwish.model.Order;
import com.codecool.shitwish.model.User;
import com.codecool.shitwish.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findByOrder(Order order) {
        return userRepository.findByOrder(order);
    }

    public List<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findByRating(String rating) {
        return userRepository.findByRating(rating);
    }

    public void add(User user){
        userRepository.save(user);
    }

    public User find(Long id){
        return userRepository.findOne(id);
    }

    public void update(Long id, String password){
        User user = find(id);
        user.setPassword(password);
        userRepository.save(user);
    }

    public void remove(Long id) {userRepository.delete(id);}

    public List<User> all(){return userRepository.findAll();}

}
