package du.ac.kr.sb1021_3.repository;

import du.ac.kr.sb1021_3.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
