package com.codecool.shitwish.repository;

import com.codecool.shitwish.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}