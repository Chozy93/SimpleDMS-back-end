package com.example.simpledms.controller;


import com.example.simpledms.model.Qna;
import com.example.simpledms.service.QnaService;
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
 * fileName : Qna07Controller
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
public class QnaController {

    @Autowired
    QnaService qnaService; // @Autowired : 스프링부트가 가동될때 생성된 객체를 하나 받아오기

    @GetMapping("/qna")
    public ResponseEntity<Object> getQnaAll(@RequestParam String searchSelect,@RequestParam(required = false) String searchKeyword,
                                            @RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "3")int size) {

        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Qna> pageQna;
            if(searchSelect.equals("question")) {
                pageQna= qnaService.findByQuestion(searchKeyword, pageable);
            }else{
                pageQna = qnaService.findByQuestioner(searchKeyword, pageable);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("qna", pageQna.getContent());
            response.put("currentPage", pageQna.getNumber());
            response.put("totalItems", pageQna.getTotalElements());
            response.put("totalPages", pageQna.getTotalPages());

            if (!pageQna.isEmpty()) {
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



    @PostMapping("/qna")
    public ResponseEntity<Object> createQna(@RequestBody Qna qna){
        try{
            Qna qna2 = qnaService.save(qna);
            return new ResponseEntity<>(qna2,HttpStatus.OK);
        }catch(Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/qna/{qno}")
    public ResponseEntity<Object> getById(@PathVariable int qno){
        try{
            Optional<Qna> optionalQna = qnaService.findById(qno);
            if(optionalQna.isPresent()){
                return new ResponseEntity<>(optionalQna,HttpStatus.OK);
            }else{return new ResponseEntity<>(HttpStatus.NO_CONTENT);}
        }catch(Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/qna/{qno}")
    public ResponseEntity<Object> updateDept(@PathVariable int qno,@RequestBody Qna qna){

        try{
            Qna qna2 = qnaService.save(qna);
            return new ResponseEntity<>(qna2,HttpStatus.OK);
        }catch(Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/qna/deletion/{qno}")
    public ResponseEntity<Object> deleteQna(@PathVariable int qno){
        try{
            qnaService.deleteByQno(qno);
            if( qnaService.deleteByQno(qno)){
                return new ResponseEntity<>(HttpStatus.OK);
            }else{return new ResponseEntity<>(HttpStatus.NO_CONTENT);}
        }catch(Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}










