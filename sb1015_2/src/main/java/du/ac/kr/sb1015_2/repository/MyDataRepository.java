package du.ac.kr.sb1015_2.repository;

import du.ac.kr.sb1015_2.entity.MyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyDataRepository extends JpaRepository<MyData, Long> {
}
