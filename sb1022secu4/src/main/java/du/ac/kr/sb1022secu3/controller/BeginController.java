package du.ac.kr.sb1022secu3.controller;

import du.ac.kr.sb1022secu3.entity.Member;
import du.ac.kr.sb1022secu3.repository.MemberRepository;
import du.ac.kr.sb1022secu3.service.DuplicateMemberException;
import du.ac.kr.sb1022secu3.service.MemberRegisterService;
import du.ac.kr.sb1022secu3.service.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;

@Controller
public class BeginController {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberRegisterService memberRegisterService;

    @GetMapping("/")
    public String index() {
        return "/sample/all";
    }

    @RequestMapping("/register/step1")
    public String handleStep1(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register/step1";
    }

    @PostMapping("/register/step2")
    public String handleStep2(
            @RequestParam(value = "agree", defaultValue = "false") Boolean agree,
            Model model) {
        if (!agree) {
            return "register/step1";
        }
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register/step2";
    }

    @GetMapping("/register/step2")
    public String handleStep2Get() {
        return "redirect:/register/step1";
    }

    @PostMapping("/register/step3")
    public String handleStep3(RegisterRequest regReq) {
        try {
            memberRegisterService.regist(regReq);
            return "register/step3";
        } catch (DuplicateMemberException ex) {
            return "register/step2";
        }
    }

    @PostConstruct
    public void init() {
        Member member = Member.builder()
                .id(1001L)
                .username("hong")
                .password(passwordEncoder().encode("1234"))
                .email("hong@aaa.com")
                .role("ADMIN")
                .build();
        memberRepository.save(member);
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
