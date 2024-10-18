package du.ac.kr.sb1015_2;

import du.ac.kr.sb1015_2.entity.MyData;
import du.ac.kr.sb1015_2.repository.MyDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class Sb10152ApplicationTests {

    @Autowired
    MyDataRepository repository;

    @Test
    void findAll() {
        List<MyData> list = repository.findAll();
        for (MyData myData : list) {
            System.out.println(myData);
        }
    }

    @Test
    void findById(){
        Long id = 4L;
        Optional<MyData> md = repository.findById(id);
        if(md.isPresent()){
            System.out.println(md.get());
        } else {
            System.out.println("데이터가 없다");
        }
    }

}
