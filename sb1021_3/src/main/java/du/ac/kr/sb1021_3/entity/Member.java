package du.ac.kr.sb1021_3.entity;

import du.ac.kr.sb1021_3.spring.WrongIdPasswordException;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;
    private String password;
    private LocalDateTime regdate;

    public Member(String email, String name, String password, LocalDateTime regdate) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.regdate = regdate;
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (!password.equals(oldPassword))
            throw new WrongIdPasswordException();
        this.password = newPassword;
    }

}
