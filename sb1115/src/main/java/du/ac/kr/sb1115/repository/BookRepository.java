package du.ac.kr.sb1115.repository;

import du.ac.kr.sb1115.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
