package com.bvrsoftware.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bvrsoftware.model.User;

public interface UserRepositry extends JpaRepository<User, Integer> {
       User findByEmail(String email);
}
