package du.ac.kr.sb1018.service;

import du.ac.kr.sb1018.entity.Dept;
import du.ac.kr.sb1018.entity.Emp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Slf4j
@Service
public class EmService {
    @PersistenceUnit
    private EntityManagerFactory emf;

    @PersistenceContext
    private EntityManager em;

    public List<Dept> selectDeptList() {
        TypedQuery<Dept> query = em.createQuery("select d from Dept d", Dept.class);
        return  query.getResultList();
    }
    public List<Emp> selectEmpList(int deptNo) {
        String jpql = "SELECT d FROM Emp d WHERE d.deptno = :deptNo";
        TypedQuery<Emp> query2 = em.createQuery(jpql, Emp.class);
        query2.setParameter("deptNo", deptNo);
        return query2.getResultList();
    }

    public Dept updateDept(int deptNo, String deptName) {
        // 트랜잭션 시작
        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Dept dept = em.find(Dept.class, deptNo); // find = select
        dept.setDname(deptName);
        if (dept != null) {
            dept.setDname(deptName);
            log.info("update dept {} with name {}", deptNo, deptName);
        } else{
            log.info("해당 {} 부서가 없습니다.", deptNo);
        }
        transaction.commit();
        return dept;
    }
    public void removeDept(int deptNo) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Dept dept = em.find(Dept.class, deptNo);
        if (dept != null) {
            em.remove(dept); // remove = delete
            log.info("부서 번호 {} 를 삭제했습니다.", deptNo);
        } else{
            log.warn("부서 번호 {} 에 해당하는 부서가 존재하지 않습니다.", deptNo);
        }
        transaction.commit();
    }

    public void persistDept(Dept dept) {
        // 트랜잭션 시작
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        //Dept 엔티티 객체 생성
        Dept newDept = new Dept();
        newDept.setDeptno(dept.getDeptno());
        newDept.setDname(dept.getDname());
        newDept.setLoc(dept.getLoc());

        // 엔티티를 테이터베이스에 저장
        em.persist(newDept); // persist = insert

        // 트랜잭션 커밋
        em.getTransaction().commit();

    }

}
