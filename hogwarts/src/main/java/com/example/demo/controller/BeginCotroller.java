package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class BeginCotroller {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String main(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {

            String username = authentication.getName();
            model.addAttribute("username", username);
        }
        return "main";
    }

    @GetMapping("/info/profile")
    public String getProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();


            User user = userService.getUserDetails(username);


            model.addAttribute("userId", user.getId());
            model.addAttribute("userName", user.getUsername());
            model.addAttribute("email", user.getEmail());
        }

        return "/info/profile";
    }
    // 회원정보 수정 폼
    @GetMapping("/update")
    public String showUpdateForm(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName(); // 로그인한 사용자 이름

            User user = userService.getUserDetails(username);


            model.addAttribute("userId", user.getId());
            model.addAttribute("userName", user.getUsername());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("password", passwordEncoder().encode(user.getPassword()));
        }
        return "updateProfile"; //
    }

    // 회원정보 수정 처리
    @PostMapping("/update")
    public String updateProfile(@ModelAttribute User user, Model model) {

        userService.updateUser(user);

        return "redirect:/info/profile";
    }


    @PostConstruct
    public void init() {
        User user = User.builder()
                .id(1001L)
                .username("관리자")
                .password(passwordEncoder().encode("1234"))
                .regdate(LocalDateTime.now())
                .email("admin@korea.com")
                .role("ADMIN")
                .build();
        userRepository.save(user);
    }
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @GetMapping("/login")
    public String loginForm() {
        return "login";  // login.html 페이지 반환
    }

}
