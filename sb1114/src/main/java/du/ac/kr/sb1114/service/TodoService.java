package du.ac.kr.sb1114.service;


import du.ac.kr.sb1114.model.TodoEntity;
import du.ac.kr.sb1114.persistence.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {


  @Autowired
  private TodoRepository repository;

  public String testService() {
    // TodoEntity 생성
    TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
    // TodoEntity 저장
    repository.save(entity);
    // TodoEntity 검색
    TodoEntity savedEntity = repository.findById(entity.getId()).get();
    return savedEntity.getTitle();
  }


}
