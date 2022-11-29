package com.example.simpledms.controller;


import com.example.simpledms.model.Booker;
import com.example.simpledms.service.BookerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * packageName : com.example.jpaexam.controller.exam07
 * fileName : Booker07Controller
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
public class BookerController {

    @Autowired
    BookerService bookerService; // @Autowired : 스프링부트가 가동될때 생성된 객체를 하나 받아오기

    @GetMapping("/booker")
    public ResponseEntity<Object> getBookerAll(@RequestParam String searchSelect,@RequestParam(required = false) String searchKeyword,
                                            @RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "3")int size) {

        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Booker> pageBooker;
            if(searchSelect.equals("name")) {
                pageBooker= bookerService.findByName(searchKeyword, pageable);
            }else if(searchSelect.equals("phone")){
                pageBooker = bookerService.findByPhone(searchKeyword, pageable);
            }else{
                pageBooker=null;
            }

            Map<String, Object> response = new HashMap<>();
            response.put("booker", pageBooker.getContent());
            response.put("currentPage", pageBooker.getNumber());
            response.put("totalItems", pageBooker.getTotalElements());
            response.put("totalPages", pageBooker.getTotalPages());

            if (!pageBooker.isEmpty()) {
//                데이터 + 성공 메세지 전송
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
//                데이터 없음 메세지 전송(클라이언트)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            log.debug(e.getMessage());
            // 서버에러 발생 메세지 전송(클라이언트)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping("/booker")
    public ResponseEntity<Object> createBooker(@RequestBody Booker booker){
        try{
            Booker booker2 = bookerService.save(booker);
            return new ResponseEntity<>(booker2,HttpStatus.OK);
        }catch(Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/booker/{qno}")
    public ResponseEntity<Object> getById(@PathVariable int qno){
        try{
            Optional<Booker> optionalBooker = bookerService.findById(qno);
            if(optionalBooker.isPresent()){
                return new ResponseEntity<>(optionalBooker,HttpStatus.OK);
            }else{return new ResponseEntity<>(HttpStatus.NO_CONTENT);}
        }catch(Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/booker/{bkno}")
    public ResponseEntity<Object> updateDept(@PathVariable int bkno,@RequestBody Booker booker){

        try{
            Booker booker2 = bookerService.save(booker);
            return new ResponseEntity<>(booker2,HttpStatus.OK);
        }catch(Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/booker/deletion/{bkno}")
    public ResponseEntity<Object> deleteBooker(@PathVariable int bkno){
        try{
            bookerService.deleteByBkno(bkno);
            if( bookerService.deleteByBkno(bkno)){
                return new ResponseEntity<>(HttpStatus.OK);
            }else{return new ResponseEntity<>(HttpStatus.NO_CONTENT);}
        }catch(Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}










