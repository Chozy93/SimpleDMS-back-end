package com.example.simpledms.controller;


import com.example.simpledms.model.Book;
import com.example.simpledms.service.BookService;
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
 * fileName : Book07Controller
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
public class BookController {

    @Autowired
    BookService bookService; // @Autowired : 스프링부트가 가동될때 생성된 객체를 하나 받아오기

    @GetMapping("/book")
    public ResponseEntity<Object> getBookAll(@RequestParam String searchSelect,@RequestParam(required = false) String searchKeyword,
                                            @RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "3")int size) {

        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Book> pageBook;
            if(searchSelect.equals("destination")) {
                pageBook= bookService.findByDestination(searchKeyword, pageable);
            }else{
                pageBook = bookService.findBySchedule(searchKeyword, pageable);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("book", pageBook.getContent());
            response.put("currentPage", pageBook.getNumber());
            response.put("totalItems", pageBook.getTotalElements());
            response.put("totalPages", pageBook.getTotalPages());

            if (!pageBook.isEmpty()) {
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


    @GetMapping("/book/{bno}")
    public ResponseEntity<Object> getById(@PathVariable int bno){
        try{
            Optional<Book> optionalBook = bookService.findById(bno);
            if(optionalBook.isPresent()){
                return new ResponseEntity<>(optionalBook,HttpStatus.OK);
            }else{return new ResponseEntity<>(HttpStatus.NO_CONTENT);}
        }catch(Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}










