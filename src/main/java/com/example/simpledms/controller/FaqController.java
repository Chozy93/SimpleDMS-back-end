package com.example.simpledms.controller;



import com.example.simpledms.model.Faq;
import com.example.simpledms.service.FaqService;
import com.example.simpledms.service.FaqService;
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
 * fileName : Faq07Controller
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
public class FaqController {

    @Autowired
    FaqService faqService; // @Autowired : 스프링부트가 가동될때 생성된 객체를 하나 받아오기

    @GetMapping("/faq")
    public ResponseEntity<Object> getFaqAll(@RequestParam(required = false) String title,
                                            @RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "3")int size) {


        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Faq> pageFaq= faqService.findByTitle(title, pageable);
            Map<String, Object> response = new HashMap<>();
            response.put("faq", pageFaq.getContent());
            response.put("currentPage", pageFaq.getNumber());
            response.put("totalItems", pageFaq.getTotalElements());
            response.put("totalPages", pageFaq.getTotalPages());

            if (!pageFaq.isEmpty()) {
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

    @DeleteMapping("/faq/all")
    public ResponseEntity<Object> removeAll() {

        try {
            faqService.removeAll();

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/faq")
    public ResponseEntity<Object> createFaq(@RequestBody Faq faq){
        try{
            Faq faq2 = faqService.save(faq);
            return new ResponseEntity<>(faq2,HttpStatus.OK);
        }catch(Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/faq/{no}")
    public ResponseEntity<Object> getById(@PathVariable int no){
        try{
            Optional<Faq> optionalFaq = faqService.getById(no);
            if(optionalFaq.isPresent()){
                return new ResponseEntity<>(optionalFaq,HttpStatus.OK);
            }else{return new ResponseEntity<>(HttpStatus.NO_CONTENT);}
        }catch(Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/faq/{no}")
    public ResponseEntity<Object> updateDept(@PathVariable int no,@RequestBody Faq faq){

        try{
            Faq faq2 = faqService.save(faq);
            return new ResponseEntity<>(faq2,HttpStatus.OK);
        }catch(Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/faq/deletion/{no}")
    public ResponseEntity<Object> deleteFaq(@PathVariable int no){
        try{
            faqService.deleteByNo(no);
            if( faqService.deleteByNo(no)){
                return new ResponseEntity<>(HttpStatus.OK);
            }else{return new ResponseEntity<>(HttpStatus.NO_CONTENT);}
        }catch(Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}










