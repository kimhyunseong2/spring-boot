package du.ac.kr.sb1014;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class Sb1014ApplicationTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    void insert_Memo() {
//        System.out.println(memoRepository.getClass().getName());
        IntStream.range(0, 10).forEach(i -> {
//            System.out.println(i);
            Memo memo = Memo.builder().memoText("샘플"+i)
                    .build();
//            System.out.println(memo);
            memoRepository.save(memo);
        });
    }

    @Test
    void select_Memo(){
        Long mno = 9L;
        Optional<Memo> result = memoRepository.findById(mno);
//        System.out.println(result.get());
        if(result.isPresent()){
            Memo memo = result.get();
        } else {
            System.out.println("없다!!");
        }
    }

    @Test
    void findAll_Memo(){
        List<Memo> result = memoRepository.findAll();
        for (Memo memo : result) {
            System.out.println(memo);
        }
    }

    @Test
    void update_Memo(){
        Memo memo = Memo.builder().id(1L).memoText("샘플100").build();
        memoRepository.save(memo);
    }

    @Test
    void delete_Memo(){
        Long mno = 1L;
        memoRepository.deleteById(mno);
    }

    @Test
    void 테스트_쿼리메서드(){
        List<Memo> result = memoRepository.findByIdBetween(2L, 7L);
        for (Memo memo : result) {
            System.out.println(memo);
        }
    }

    @Test
    void 테스트_쿼리메서드2(){
        List<Memo> result = memoRepository.findByMemoTextContaining("1");
        for (Memo memo : result) {
            System.out.println(memo);
        }
    }

    @Test
    void 테스트_쿼리어노테이션(){
        System.out.println(memoRepository.getCount() + "건의 자료가 있습니다.");
        List<Memo> result = memoRepository.getListDesc();
        for (Memo memo : result) {
            System.out.println(memo);
        }
    }

}
