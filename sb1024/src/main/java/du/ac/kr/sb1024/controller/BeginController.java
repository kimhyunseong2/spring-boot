package du.ac.kr.sb1024.controller;

import du.ac.kr.sb1024.entity.Member;
import du.ac.kr.sb1024.repository.MemberRepository;
import du.ac.kr.sb1024.survey.AnsweredData;
import du.ac.kr.sb1024.survey.Question;
import du.ac.kr.sb1024.survey.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class BeginController {

    @Autowired
    MemberRepository memberRepository;


    @GetMapping("/")
    public String index() {
        return "/sample/all";
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

    @Autowired
    SurveyService surveyService;

    @GetMapping("/survey/surveyForm")
    public String form(Model model) {
        List<Question> questions = createQuestions();
        for (Question question : questions) {
            System.out.println(question);
        }
        model.addAttribute("questions", questions);
        return "/survey/surveyForm";
    }

    private List<Question> createQuestions() {
        Question q1 = new Question("당신의 역할은 무엇입니까?",
                Arrays.asList("서버", "프론트", "풀스택"));
        Question q2 = new Question("많이 사용하는 개발도구는 무엇입니까?",
                Arrays.asList("이클립스", "인텔리J", "서브라임"));
        Question q3 = new Question("하고 싶은 말을 적어주세요.");
        return Arrays.asList(q1, q2, q3);
    }

    @PostMapping("/survey")
    public String submit(@ModelAttribute("ansData") AnsweredData data) {

        surveyService.save(data);
        return "/survey/submitted";
    }





}
