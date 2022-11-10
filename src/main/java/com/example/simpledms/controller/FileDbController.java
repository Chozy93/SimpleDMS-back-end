package com.example.simpledms.controller;


import com.example.simpledms.model.FileDb;
import com.example.simpledms.service.FileDbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * packageName : com.example.jpacustomexam.controller.exam01
 * fileName : FileDbController
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
public class FileDbController {
//    스프링부트 : DI(@Autowired)의존성주입
    @Autowired
FileDbService fileDbService; //@Autowired : 스프링부트가 가동될때 생성된 객체를 하나 받아오기 어노테이션


//    클라이언트 : Get 방식(url) -> 서버 @GetMapping("url") -> DB : select 요청
//    쿼리스트링방식 : ? @RequestParam
//    파라메터방식 : /{} @PathVariable
    @GetMapping("/fileDb")//select 요청(DB 입장),get 방식(web 입장)
    public ResponseEntity<Object> getFileDbAll(@RequestParam(required = false) String title,
                                             @RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "3") int size) {
        try {
//            Pageable 객체 정의 ( page, size 값 설정 )
            Pageable pageable = PageRequest.of(page, size);

//            Page 객체 정의
            Page<FileDb> fileDbPage;


                fileDbPage = fileDbService.findByFileTitle(title, pageable);


//            맵 자료구조에 넣어서 전송
            Map<String, Object> response = new HashMap<>();
            response.put("fileDb", fileDbPage.getContent());
            response.put("currentPage", fileDbPage.getNumber());
            response.put("totalItems", fileDbPage.getTotalElements());
            response.put("totalPages", fileDbPage.getTotalPages());

            if (fileDbPage.isEmpty() == false) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }




//생성
    @PostMapping("/fileDb/upload")
    public ResponseEntity<Object> uploadFileDb(String fileTitle,
                                               String fileContent,
                                               @RequestBody  MultipartFile file){
      try{
          FileDb fileDb = fileDbService.store(fileTitle,fileContent,file);
          return new ResponseEntity<>(fileDb,HttpStatus.OK);
      }catch(Exception e){
          log.debug(e.getMessage());
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }



    @GetMapping("/fileDb/{fid}")
    public ResponseEntity<Object> getById(@PathVariable int fid){
      try{
          Optional<FileDb> optionalFileDb = fileDbService.getFile(fid);
          if(optionalFileDb.isPresent()){
              return new ResponseEntity<>(optionalFileDb,HttpStatus.OK);
          }else{return new ResponseEntity<>(HttpStatus.NO_CONTENT);}
      }catch(Exception e){
          log.debug(e.getMessage());
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }




    @DeleteMapping("/fileDb/deletion/{fid}")
    public ResponseEntity<Object> deleteFileDb(@PathVariable int fid){
        try{
            fileDbService.deleteById(fid);
            if( fileDbService.deleteById(fid)){
                return new ResponseEntity<>(HttpStatus.OK);
            }else{return new ResponseEntity<>(HttpStatus.NO_CONTENT);}
        }catch(Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}

