package du.ac.kr.sb1101.service;



import du.ac.kr.sb1101.entity.User;
import du.ac.kr.sb1101.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MemberRegisterService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	public Long regist(RegisterRequest req) {
		Optional<User> user = userRepository.findByEmail(req.getEmail());
		if (user.isPresent()) {
			throw new DuplicateMemberException("dup email " + req.getEmail());
		}
		if (!req.getPassword().equals(req.getConfirmPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		User newUser = User.builder()
				.email(req.getEmail())
				.password(passwordEncoder().encode(req.getPassword()))
				.regdate(LocalDateTime.now())
				.username(req.getName())
				.role("USER")
				.build();
		userRepository.save(newUser);
		System.out.println("====>" + newUser);
		return newUser.getId();
	}

	private PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
