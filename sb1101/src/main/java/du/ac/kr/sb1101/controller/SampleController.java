package du.ac.kr.sb1101.controller;

import du.ac.kr.sb1101.entity.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@Log4j2
@RequestMapping("/sample/")
public class SampleController {
    @GetMapping("/accessDenied")
        public void accessDenied() {}

    @GetMapping("/all")
    public void all() {
        log.info("exAll....");
    }

    @GetMapping("/admin")
    public void admin() {
        log.info("exAdmin....");
    }

    @GetMapping("/user")
    public void user() {
        log.info("exMember....");
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,
                        @RequestParam(required = false) String logout,
                        Model model) {
        log.info("login 페이지..........");

        if (error != null) {
            log.info("login error..........");
            model.addAttribute("errorMessage", "로그인 실패: 아이디나 비밀번호를 확인하세요.");
        }

        if (logout != null) {
            log.info("user logout..........");
            model.addAttribute("logoutMessage", "로그아웃되었습니다.");
        }

        return "/sample/login";
    }





}
