package du.ac.kr.sb1021_2;

import du.ac.kr.sb1021_2.entity.Member;
import du.ac.kr.sb1021_2.repository.MemberRepository;
import du.ac.kr.sb1021_2.spring.MemberDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class Sb10212ApplicationTests {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberDao memberDao;

    @Test
    void 입력_테스트() {
        Member member = Member.builder()
                .email("hong@korea.com")
                .password("1234")
                .name("홍길동")
                .regdate(LocalDateTime.now())
                .build();
        System.out.println(memberRepository.save(member));

        Optional<Member> members = memberDao.selectByEmail("hong@korea.com");
        System.out.println(members);
    }

}
