package du.ac.kr.sb1021_2.spring;

import lombok.Data;

@Data
public class RegisterRequest {

	private String email;
	private String password;
	private String confirmPassword;
	private String name;
	private String agree;
}