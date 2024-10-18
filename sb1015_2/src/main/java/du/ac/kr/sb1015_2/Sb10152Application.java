package du.ac.kr.sb1015_2;

import du.ac.kr.sb1015_2.entity.MyData;
import du.ac.kr.sb1015_2.repository.MyDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RequiredArgsConstructor
public class Sb10152Application {

    //@Autowired
    final MyDataRepository repository;

//    public Sb10152Application(MyDataRepository repository) {
//        this.repository = repository;
//    }

    public static void main(String[] args) {
        SpringApplication.run(Sb10152Application.class, args);
    }

    @PostConstruct
    public void init(){
        MyData d1 = new MyData();
        d1.setName("kim");
        d1.setAge(20);
        d1.setMail("kim@gilbut.co.kr");
        d1.setMemo("This is my data");
        repository.saveAndFlush(d1);

        MyData d2 = new MyData();
        d2.setName("lee");
        d2.setAge(20);
        d2.setMail("lee@flower");
        d2.setMemo("my girl friend.");
        repository.saveAndFlush(d2);

        MyData d3 = new MyData();
        d3.setName("choi");
        d3.setAge(25);
        d3.setMail("choi@happy");
        d3.setMemo("my work friend...");
        repository.saveAndFlush(d3);
    }
}
