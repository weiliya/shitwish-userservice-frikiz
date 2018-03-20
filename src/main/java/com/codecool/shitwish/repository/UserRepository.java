package com.codecool.shitwish.repository;

import com.codecool.shitwish.model.Order;
import com.codecool.shitwish.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    List<User> findByOrder(Order order);

    List<User> findByEmail(String email);

    List<User> findByRating(String averageRating);

    User findOne(Long id);
}