package com.example.simpledms.controller;


import com.example.simpledms.model.Dept;
import com.example.simpledms.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * packageName : com.example.jpacustomexam.controller.exam01
 * fileName : DeptController
 * author : ds
 * date : 2022-10-20
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-10-20         ds          최초 생성
 */
@Slf4j

@RestController
// return 값이 json 데이터 형태로 출력됨
@RequestMapping("/api")
public class DeptController {
//    스프링부트 : DI(@Autowired)의존성주입
    @Autowired
DeptService deptService; //@Autowired : 스프링부트가 가동될때 생성된 객체를 하나 받아오기 어노테이션


//    클라이언트 : Get 방식(url) -> 서버 @GetMapping("url") -> DB : select 요청
//    쿼리스트링방식 : ? @RequestParam
//    파라메터방식 : /{} @PathVariable
    @GetMapping("/dept")//select 요청(DB 입장),get 방식(web 입장)
    public ResponseEntity<Object> getDeptAll(@RequestParam(required = false) String dname,
                                             @RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "3") int size) {
        try {
//            Pageable 객체 정의 ( page, size 값 설정 )
            Pageable pageable = PageRequest.of(page, size);

//            Page 객체 정의
            Page<Dept> deptPage;


                deptPage = deptService.findAllByDnameContaining(dname, pageable);


//            맵 자료구조에 넣어서 전송
            Map<String, Object> response = new HashMap<>();
            response.put("dept", deptPage.getContent());
            response.put("currentPage", deptPage.getNumber());
            response.put("totalItems", deptPage.getTotalElements());
            response.put("totalPages", deptPage.getTotalPages());

            if (deptPage.isEmpty() == false) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }



    @DeleteMapping("/dept/all")
    public ResponseEntity<Object> deleteAll(){
      try{
          deptService.removeAll();
          return new ResponseEntity<>(HttpStatus.OK);
      }catch(Exception e){
          log.debug(e.getMessage());
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @PostMapping("/dept")
    public ResponseEntity<Object> createDept(@RequestBody Dept dept){
      try{
          Dept dept2 = deptService.save(dept);
          return new ResponseEntity<>(dept2,HttpStatus.OK);
      }catch(Exception e){
          log.debug(e.getMessage());
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
    @GetMapping("/dept/{dno}")
    public ResponseEntity<Object> getById(@PathVariable int dno){
      try{
          Optional<Dept> optionalDept = deptService.findById(dno);
          if(optionalDept.isPresent()){
              return new ResponseEntity<>(optionalDept,HttpStatus.OK);
          }else{return new ResponseEntity<>(HttpStatus.NO_CONTENT);}
      }catch(Exception e){
          log.debug(e.getMessage());
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }


    @PutMapping("/dept/{dno}")
    public ResponseEntity<Object> updateDept(@PathVariable int dno,@RequestBody Dept dept){
        try{
            Dept dept2 = deptService.save(dept);
            return new ResponseEntity<>(dept2,HttpStatus.OK);
        }catch(Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/dept/deletion/{dno}")
    public ResponseEntity<Object> deleteDept(@PathVariable int dno){
        try{
            deptService.deleteByDno(dno);
            if( deptService.deleteByDno(dno)){
                return new ResponseEntity<>(HttpStatus.OK);
            }else{return new ResponseEntity<>(HttpStatus.NO_CONTENT);}
        }catch(Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}

