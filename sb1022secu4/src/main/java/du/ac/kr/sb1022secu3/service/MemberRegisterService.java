package du.ac.kr.sb1022secu3.service;

import du.ac.kr.sb1022secu3.entity.Member;
import du.ac.kr.sb1022secu3.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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


		Member newMember = Member.builder()
				.email(req.getEmail())
				.password(passwordEncoder.encode(req.getPassword()))
				.username(req.getUsername())
				.role("USER")
				.build();
		memberRepository.save(newMember);
		System.out.println("====>" + newMember);
		return newMember.getId();
	}

}
