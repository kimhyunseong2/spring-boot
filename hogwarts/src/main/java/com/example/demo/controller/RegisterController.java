package com.example.demo.controller;





import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import javassist.bytecode.DuplicateMemberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class RegisterController {
	@Autowired
	private UserRepository userRepository;


	@GetMapping("/register")
	public String registerForm(Model model) {
		model.addAttribute("registerRequest", new RegisterRequest());
		return "register";
	}

	// 회원가입 처리
	@PostMapping("/register")
	public String registerUser(RegisterRequest registerRequest, BindingResult bindingResult, Model model) {
		// 이메일 중복 체크
		Optional<User> existingUser = userRepository.findByEmail(registerRequest.getEmail());
		if (existingUser.isPresent()) {
			bindingResult.rejectValue("email", "error.email", "이미 등록된 이메일입니다.");
		}

		// 비밀번호와 확인 비밀번호가 일치하는지 체크
		if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
			bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "비밀번호가 일치하지 않습니다.");
		}

		if (bindingResult.hasErrors()) {
			return "register";  // 폼에 오류가 있으면 다시 회원가입 폼을 반환
		}

		User newUser = User.builder()
				.email(registerRequest.getEmail())
				.password(passwordEncoder().encode(registerRequest.getPassword()))
				.regdate(LocalDateTime.now())
				.username(registerRequest.getName())
				.role("USER")
				.build();
		userRepository.save(newUser);
		return"redirect:/login";
	}
	private PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}





