package du.ac.kr.sb1024.service;


import du.ac.kr.sb1024.entity.Member;
import du.ac.kr.sb1024.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class MemberRegisterService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	public Long regist(RegisterRequest req) {
		Optional<Member> member = memberRepository.findByEmail(req.getEmail());
		if (member.isPresent()) {
			throw new DuplicateMemberException("dup email " + req.getEmail());
		}
		if (!req.getPassword().equals(req.getConfirmPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		Member newMember = Member.builder()
				.email(req.getEmail())
				.password(req.getPassword())
				.regdate(LocalDateTime.now())
				.username(req.getName())
				.role("USER")
				.build();
		memberRepository.save(newMember);
		System.out.println("====>" + newMember);
		return newMember.getId();
	}

}
