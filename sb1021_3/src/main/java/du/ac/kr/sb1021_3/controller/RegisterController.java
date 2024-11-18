package du.ac.kr.sb1021_3.controller;

import du.ac.kr.sb1021_3.spring.DuplicateMemberException;
import du.ac.kr.sb1021_3.spring.MemberRegisterService;
import du.ac.kr.sb1021_3.spring.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class RegisterController {

	@Autowired
	MemberRegisterService memberRegisterService;
	
	@GetMapping("/")
	public String root() {
		return "redirect:/register/step1";
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

}
