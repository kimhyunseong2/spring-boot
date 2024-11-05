package du.ac.kr.sb1101.controller;


import du.ac.kr.sb1101.entity.User;
import du.ac.kr.sb1101.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Controller
public class BeginController {

    @Autowired
    UserRepository userRepository;


    @GetMapping("/")
    public String index() {
        return "redirect:/sample/all";
    }

    @PostConstruct
    public void init() {
        User user = User.builder()
                .id(1001L)
                .username("관리자")
                .password(passwordEncoder().encode("1234"))
                .regdate(LocalDateTime.now())
                .email("admin@123.com")
                .role("ADMIN")
                .build();
        userRepository.save(user);
    }
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }






}
