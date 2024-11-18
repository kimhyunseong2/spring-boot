package com.example.demo.controller;

import com.example.demo.entity.Board;
import com.example.demo.entity.Member;
import com.example.demo.entity.Notification;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.BoardService;
import com.example.demo.service.NotificationService;
import com.example.demo.service.UserNotFoundException;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
public class BeginCotroller {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/")
    public String main(Model model) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {

            String username = authentication.getName();
            model.addAttribute("username", username);
            List<Notification> notifications = notificationService.getNotificationsByRecipient(username);
            model.addAttribute("notifications", notifications);
        }
        List<Board> latestPost = boardService.selectLatestPost(); // 최신 게시글 조회
        model.addAttribute("latestPost", latestPost);
        return "main";
    }

    @GetMapping("/info/profile")
    public String getProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String username = authentication.getName();
            model.addAttribute("username", username);
            Member member = userService.getUserDetails(username);
            model.addAttribute("userId", member.getId());
            model.addAttribute("userName", member.getUsername());
            model.addAttribute("email", member.getEmail());

        return "/info/profile";
    }
    // 회원정보 수정 폼
    @GetMapping("/update")
    public String showUpdateForm(Model model, Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            model.addAttribute("username", username);
            Member member = userService.getUserDetails(username);
            model.addAttribute("member", member);
            System.out.println(member);
        }
        return "updateProfile";
    }

    @PostMapping("/update")
    public String updateProfile(@ModelAttribute Member member) {
        userService.updateUser(member);
        return "redirect:/info/profile";
    }


    @GetMapping("/deleteRegister")
    public String showDeleteForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        model.addAttribute("username", username);
        return "deleteRegister";  // deleteRegister.html 페이지로 이동
    }

    // 이메일로 회원 탈퇴 처리하는 POST 요청
    @PostMapping("/deleteRegister")
    public String deleteUserByEmail(@RequestParam("email") String email, HttpServletRequest request,
                                    RedirectAttributes redirectAttributes, HttpServletResponse response) {
        try {
            userService.deleteUserByEmail(email);  // 사용자 삭제
            SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
            logoutHandler.logout(request, response, null);

            redirectAttributes.addFlashAttribute("Message", "회원탈퇴가 완료되었습니다");
            return "redirect:/login";
        } catch (UserNotFoundException e) {
            String referer = request.getHeader("Referer");  // 이전 페이지 URL을 가져옵니다.


            redirectAttributes.addFlashAttribute("errorMessage", "해당 이메일이 아닙니다.");

            // 이전 페이지로 리디렉션
            return "redirect:" + referer; // 사용자 이메일이 없는 경우 에러 페이지로 리디렉션
        }
    }


    @PostConstruct
    public void init() {
        Member member = Member.builder()
                .id(1001L)
                .username("관리자")
                .password(passwordEncoder().encode("1234"))
                .regdate(LocalDateTime.now())
                .email("admin@korea.com")
                .role("ADMIN")
                .build();
        memberRepository.save(member);
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




}
