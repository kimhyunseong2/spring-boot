package du.ac.kr.sb1021_3.spring;

import lombok.Data;

@Data
public class RegisterRequest {

	private String email;
	private String password;
	private String confirmPassword;
	private String name;
	private boolean agree;


}
