package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserDetails(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void updateUser(User user) {
         userRepository.save(user);
    }
}
