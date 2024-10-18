package du.ac.kr.chap17;

import du.ac.kr.chap17.dao.ArticleDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Sb1chap17ApplicationTests {

    @Autowired
    ArticleDao articleDao;

    @Test
    void  게시판건수_출력(){
        System.out.println(articleDao.selectCount());
    }

}
