package com.example.simpledms.repository;


import com.example.simpledms.model.Book;
import com.example.simpledms.model.Qna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName : com.example.jpaexam.repository
 * fileName : DeptRepository
 * author : ds
 * date : 2022-10-20
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-10-20         ds          최초 생성
 */
//@Service @Repository @Component 서버가 가동될때 객체를 자동 생성해주는 어노테이션
//JpaRepository<모델(엔티티)명,@ID의 속성자료형> : JPA 인터페이스를 상속 받아야 CRUD 함수를 사용가능
//JPQl 함수를 사용할 수 있음 : 응용
//@Query, 쿼리메소드 직접 sql문을 직접짜서 던질수 있다
public interface BookRepository extends JpaRepository<Book, Integer> {


    Page<Book> findAllByDestinationContaining(String destination, Pageable pageable);
    Page<Book> findAllByScheduleContaining(String schedule, Pageable pageable);

}
