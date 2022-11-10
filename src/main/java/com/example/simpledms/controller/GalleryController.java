package com.example.simpledms.controller;



import com.example.simpledms.dto.gallery.ResponseGalleryDto;
import com.example.simpledms.dto.gallery.ResponseMessageDto;
import com.example.simpledms.model.Gallery;
import com.example.simpledms.service.GalleryService;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * packageName : com.example.jpacustomexam.controller.exam01
 * fileName : GalleryController
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
public class GalleryController {
//    스프링부트 : DI(@Autowired)의존성주입
    @Autowired
GalleryService galleryService; //@Autowired : 스프링부트가 가동될때 생성된 객체를 하나 받아오기 어노테이션

    ModelMapper modelMapper = new ModelMapper();

//    클라이언트 : Get 방식(url) -> 서버 @GetMapping("url") -> DB : select 요청
//    쿼리스트링방식 : ? @RequestParam
//    파라메터방식 : /{} @PathVariable
    @GetMapping("/gallery")//select 요청(DB 입장),get 방식(web 입장)
    public ResponseEntity<Object> getListFiles(@RequestParam(required = false) String title,
                                             @RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "3") int size) {
        try {
//            Pageable 객체 정의 ( page, size 값 설정 )
            Pageable pageable = PageRequest.of(page, size);

//            Page 객체 정의
            Page<ResponseGalleryDto> galleryPage;

                galleryPage = galleryService.findByFileTitle(title, pageable)
                                        .map(gallery ->{
                                            String DownloadUri = ServletUriComponentsBuilder
                                                                                 .fromCurrentContextPath().path("api/gallery/").path(gallery.getGid().toString()).toUriString();

//                                         modelMapper.map(소스모델,타겟Dto.class)
                                            ResponseGalleryDto galleryDto = modelMapper.map(gallery,ResponseGalleryDto.class);

                                            galleryDto.setGallerySize(gallery.getGalleryData().length);
                                            galleryDto.setGalleryUrl(DownloadUri);

                                            return galleryDto;
                                        });

//            맵 자료구조에 넣어서 전송
            Map<String, Object> response = new HashMap<>();
            response.put("gallery", galleryPage.getContent());
            response.put("currentPage", galleryPage.getNumber());
            response.put("totalItems", galleryPage.getTotalElements());
            response.put("totalPages", galleryPage.getTotalPages());

            if (!galleryPage.isEmpty()) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }




//생성(업로드)
    @PostMapping("/gallery/upload")
    public ResponseEntity<Object> GalleryUploadFile(@RequestParam("galleryTitle") String galleryTitle,
                                                   @RequestParam("gallery")  MultipartFile gallery){
        String message= "";
        log.debug("galleryTitle: " + galleryTitle);

        log.debug("gallery: " + gallery);
      try{

          galleryService.store(galleryTitle,gallery);
          message = "Upload the file successfully: " + gallery.getOriginalFilename();
          return new ResponseEntity<>(new ResponseMessageDto(message),HttpStatus.OK);
      }catch(Exception e){
          log.debug(e.getMessage());
          message = "Could not upload the file : " + gallery.getOriginalFilename();
          return new ResponseEntity<>(new ResponseMessageDto(message),HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }


//파일 다운로드
    @GetMapping("/gallery/{gid}")
    public ResponseEntity<Object> getById(@PathVariable int gid){
      try{
          Gallery gallery = galleryService.getGallery(gid).get();
//          첨부파일 다운로드 : url Content-Type 규칙
          return ResponseEntity.ok()
                  .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + gallery.getGalleryFileName() + "\"")
                  .body(gallery.getGalleryData());
      }catch(Exception e){
          log.debug(e.getMessage());
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }


    @DeleteMapping("/gallery/deletion/{gid}")
    public ResponseEntity<Object> deleteGallery(@PathVariable int gid){
        try{
            galleryService.deleteById(gid);
            if( galleryService.deleteById(gid)){
                return new ResponseEntity<>(HttpStatus.OK);
            }else{return new ResponseEntity<>(HttpStatus.NO_CONTENT);}
        }catch(Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}

