package com.example.simpledms.repository;


import com.example.simpledms.model.Dept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
@Repository
public interface DeptRepository extends JpaRepository<Dept, Integer> {
// 1)쿼리 메소드 방식 (오히려 sql문을 더 잘알아야 한다.)
//    쿼리문을 자동으로 만들어줌 : 함수의 이름을 참조하여 만듬
// 1-1)사용 규칙
//         find : 데이터 찾기 select
//         All : 여러 건(배열) *
//         By : 고정(항상) from
//         클래스 모델 속성명 : 있으면 매개변수로 들어옴 where(조건)절의 컬럼명
//         OrderBy : 정렬(고정) ->order by
//         속성명Desc : 내림차순
//         속성명Asc : 오름차순
//  ex)전체 조회 내림차순 정렬
//    부서명으로(dname) 조회하는 like 검색 함수
//    1) 쿼리메소드 방식으로 함수 정의
Page<Dept> findAllByDnameContaining(String dname,Pageable pageable);




}
