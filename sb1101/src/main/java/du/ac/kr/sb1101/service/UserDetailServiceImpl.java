package du.ac.kr.sb1101.service;


import du.ac.kr.sb1101.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@Log4j2
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Member member = Member.builder()
//                .id(1001L)
//                .username("홍길동")
//                .password(passwordEncoder().encode("1234"))
//                .email("hong@aaa.com")
//                .build();
//        Optional<Member> member = memberRepository.findByUsername(username);
        Optional<du.ac.kr.sb1101.entity.User> user = userRepository.findByEmail(username);

        return toUserDetails(user.get());
    }
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private UserDetails toUserDetails(du.ac.kr.sb1101.entity.User user){
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                //.authorities(new SimpleGrantedAuthority(member.getRole().toString()))
                .roles(user.getRole())
                .build();
    }
}
