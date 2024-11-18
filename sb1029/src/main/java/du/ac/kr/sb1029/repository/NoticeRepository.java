package du.ac.kr.sb1029.repository;

import du.ac.kr.sb1029.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    @Override
    Optional<Notice> findById(Integer id);

}
