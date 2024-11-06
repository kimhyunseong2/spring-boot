package com.example.demo.controller;

import com.example.demo.entity.Board;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BoardService;
import com.example.demo.service.UserNotFoundException;
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
import java.util.List;
import java.util.Optional;

@Controller
public class BeginCotroller {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @GetMapping("/")
    public String main(Model model) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {

            String username = authentication.getName();
            model.addAttribute("username", username);
        }
        Board latestPost = boardService.selectLatestPost(); // 최신 게시글 조회
        model.addAttribute("latestPost", latestPost);
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

    @GetMapping("/deleteRegister")
    public String showDeleteForm() {
        return "deleteRegister";  // deleteRegister.html 페이지로 이동
    }

    // 이메일로 회원 탈퇴 처리하는 POST 요청
    @PostMapping("/deleteRegister")
    public String deleteUserByEmail(@RequestParam("email") String email) {
        try {
            userService.deleteUserByEmail(email);  // 사용자 삭제
            return "redirect:/logout";  // 탈퇴 후 로그아웃 처리
        } catch (UserNotFoundException e) {
            return "redirect:/error";  // 사용자 이메일이 없는 경우 에러 페이지로 리디렉션
        }
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
    public String loginForm(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String logout,
                            Model model) {

        if (error != null) {

            model.addAttribute("errorMessage", "로그인 실패: 아이디나 비밀번호를 확인하세요.");
        }

        if (logout != null) {

            model.addAttribute("logoutMessage", "로그아웃되었습니다.");

        }
        return "login";
    }

    @GetMapping("/role/admin")
    public void admin() {
    }

    @GetMapping("/role/admin2")
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "/role/admin2";
    }

    @GetMapping("/role/accessDenied")
    public void accessDenied() {
    }

}
