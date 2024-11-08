package com.example.demo.controller;




import com.example.demo.service.DuplicateMemberException;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
public class RegisterController {
	@Autowired
	private MemberRepository memberRepository;

	@GetMapping("/register")
	public String registerForm(Model model) {
		model.addAttribute("registerRequest", new RegisterRequest());
		return "register";
	}

	// 회원가입 처리
	@PostMapping("/register")
	public String registerUser(@Validated RegisterRequest regReq, BindingResult errors,
							   RedirectAttributes redirectAttributes) {
		if(errors.hasErrors()) {
			return "register";
		}
		if (memberRepository.findByEmail(regReq.getEmail()).isPresent()) {
			// 이메일 중복이 있을 경우
			errors.rejectValue("email", "duplicate");
			return "register";
		}
		try {
			Member newUser = Member.builder()
					.email(regReq.getEmail())
					.password(passwordEncoder().encode(regReq.getPassword()))
					.regdate(LocalDateTime.now())
					.username(regReq.getName())
					.role("USER")
					.build();
			memberRepository.save(newUser);
			redirectAttributes.addFlashAttribute("Success", "회원가입이 완료했습니다");
			return "redirect:/login";
		} catch (DuplicateMemberException ex) {
			errors.reject("register.failed", "회원가입 실패");
			return "register";
		}

	}
	private PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(new RegisterRequestValidator());
	}


}





