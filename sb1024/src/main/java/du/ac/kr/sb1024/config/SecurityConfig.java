package du.ac.kr.sb1024.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Log4j2
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.withUsername("user")
//                .password(passwordEncoder().encode("1234"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("------------filterChain------------");
        http.authorizeHttpRequests()
//                .antMatchers("/**").permitAll()
//                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/register/**").permitAll()
                .antMatchers("/sample/all").permitAll()
//                .antMatchers("/board/**").permitAll()
//                .antMatchers("/login").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/sample/admin").hasRole("ADMIN")
                .anyRequest().authenticated();
//        http.formLogin();
        http.csrf().disable();
        http.formLogin().loginPage("/sample/login").permitAll();
        http.logout().logoutSuccessUrl("/sample/login");
        http.exceptionHandling().accessDeniedPage("/sample/accessDenied");
//        http.csrf()
//                .ignoringAntMatchers("/h2-console/**")
//                .and().headers().frameOptions().sameOrigin();

        return http.build();
    }
}
