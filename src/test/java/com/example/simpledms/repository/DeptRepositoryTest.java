package com.example.simpledms.repository;

import com.example.simpledms.model.Dept;
import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * packageName : com.example.simpledms.repository
 * fileName : DeptRepositoryTest
 * author : ds
 * date : 2022-11-04
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-04         ds          최초 생성
 */
//@ExtendWith(SpringExtension.class) 테스트 시 스프링 함수 또는 기능을 제공해주는 어노테이션
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //아래 옵션은 외장 DB 로 테스트 진행 한다는 옵션
class DeptRepositoryTest {

   @Autowired
    DeptRepository deptRepository; // @Mock 붙이면 가짜 리파지토리

    @Test
    void findAllByDnameContaining() {
        Optional<Dept> optionalDept = Optional.ofNullable(Dept.builder()
                .dno(10)
                .dname("SALES")
                .loc("SEOUL")
                .build());

//        임시로 데이터를 insert

        Dept dept2 = deptRepository.save(optionalDept.get());

//        List<Dept> list = deptRepository.findAllByDnameContaining("SALES");
            deptRepository.deleteAll();
//        assertThat(dept2.getDname()).isEqualTo("SALES"); // save 테스트
//       assertThat(list.get(0).getDname()).isEqualTo("SALES"); //함수 테스트
        assertThat(deptRepository.findAll()).isEqualTo(Collections.emptyList()); // 삭제함수 테스트

    }
}