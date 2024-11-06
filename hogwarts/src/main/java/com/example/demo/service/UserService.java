package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserDetails(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findByRole("USER");
    }

    @Transactional
    public void updateUser(User user) {
         userRepository.save(user);
    }

    @Transactional
    public void deleteUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            userRepository.delete(user.get());  // 사용자 삭제
        } else {
            throw new UserNotFoundException("해당 이메일을 가진 사용자가 없습니다.");
        }
    }
}
