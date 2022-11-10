package com.example.simpledms.controller;



import com.example.simpledms.model.Dept;
import com.example.simpledms.model.Emp;
import com.example.simpledms.service.EmpService;
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
 * packageName : com.example.jpaexam.controller.exam07
 * fileName : Emp07Controller
 * author : ds
 * date : 2022-10-21
 * description : 부서 컨트롤러 쿼리 메소드
 * 요약 :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-10-21         ds          최초 생성
 */
@Slf4j
// CORS 보안 : 기본적으로 한사이트에서 포트를 달리 사용못함
// @CrossOrigin(허용할_사이트주소(Vue 사이트주소:포트)) : CORS 보안을 허용해주는 어노테이션

@RestController
@RequestMapping("/api")
public class EmpController {

    @Autowired
    EmpService empService; // @Autowired : 스프링부트가 가동될때 생성된 객체를 하나 받아오기

    @GetMapping("/emp")
    public ResponseEntity<Object> getEmpAll(@RequestParam(required = false) String ename,
                                            @RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue ="3")int size ) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Emp> pageEmp= empService.findByEname(ename, pageable);
            Map<String, Object> response = new HashMap<>();
            response.put("emp", pageEmp.getContent());
            response.put("currentPage", pageEmp.getNumber());
            response.put("totalItems", pageEmp.getTotalElements());
            response.put("totalPages", pageEmp.getTotalPages());
            if(!pageEmp.isEmpty()){
                return new ResponseEntity<>(response,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            } catch (Exception e) {
            log.debug(e.getMessage());
            // 서버에러 발생 메세지 전송(클라이언트)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/emp/all")
    public ResponseEntity<Object> removeAll() {

        try {
            empService.removeAll();

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/emp")
    public ResponseEntity<Object> createEmp(@RequestBody Emp emp){
        try{
            Emp emp2 = empService.save(emp);
            return new ResponseEntity<>(emp2,HttpStatus.OK);
        }catch(Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/emp/{eno}")
    public ResponseEntity<Object> getById(@PathVariable int eno){
        try{
            Optional<Emp> optionalEmp = empService.findById(eno);
            if(optionalEmp.isPresent()){
                return new ResponseEntity<>(optionalEmp,HttpStatus.OK);
            }else{return new ResponseEntity<>(HttpStatus.NO_CONTENT);}
        }catch(Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/emp/{eno}")
    public ResponseEntity<Object> updateDept(@PathVariable int eno,@RequestBody Emp emp){

        try{
            Emp emp2 = empService.save(emp);
            return new ResponseEntity<>(emp2,HttpStatus.OK);
        }catch(Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/emp/deletion/{eno}")
    public ResponseEntity<Object> deleteEmp(@PathVariable int eno){
        try{
            empService.deleteByEno(eno);
            if( empService.deleteByEno(eno)){
                return new ResponseEntity<>(HttpStatus.OK);
            }else{return new ResponseEntity<>(HttpStatus.NO_CONTENT);}
        }catch(Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}










