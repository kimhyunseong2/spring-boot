package du.ac.kr.sb1024.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/member")
    public void member() {
        log.info("exMember....");
    }

    @GetMapping("/login")
    public void login(String errorCode, String logout) {
        log.info("login 페이지..........");
        if (logout != null) {
            log.info("user logout..........");
        }
    }

}
