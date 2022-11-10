package com.example.simpledms.controller;


import com.example.simpledms.model.Customer;
import com.example.simpledms.service.CustomerService;
import com.example.simpledms.service.CustomerService;
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
 * fileName : Customer07Controller
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
public class CustomerController {

    @Autowired
    CustomerService customerService; // @Autowired : 스프링부트가 가동될때 생성된 객체를 하나 받아오기

    @GetMapping("/customer")
    public ResponseEntity<Object> getCustomerAll(@RequestParam(required = false) String title,
                                            @RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "3")int size) {


        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Customer> pageCustomer= customerService.findByEmail(title, pageable);
            Map<String, Object> response = new HashMap<>();
            response.put("customer", pageCustomer.getContent());
            response.put("currentPage", pageCustomer.getNumber());
            response.put("totalItems", pageCustomer.getTotalElements());
            response.put("totalPages", pageCustomer.getTotalPages());

            if (!pageCustomer.isEmpty()) {
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



    @PostMapping("/customer")
    public ResponseEntity<Object> createCustomer(@RequestBody Customer customer){
        try{
            Customer customer2 = customerService.save(customer);
            return new ResponseEntity<>(customer2,HttpStatus.OK);
        }catch(Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/customer/{cid}")
    public ResponseEntity<Object> getById(@PathVariable int cid){
        try{
            Optional<Customer> optionalCustomer = customerService.findById(cid);
            if(optionalCustomer.isPresent()){
                return new ResponseEntity<>(optionalCustomer,HttpStatus.OK);
            }else{return new ResponseEntity<>(HttpStatus.NO_CONTENT);}
        }catch(Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/customer/{no}")
    public ResponseEntity<Object> updateDept(@PathVariable int no,@RequestBody Customer customer){

        try{
            Customer customer2 = customerService.save(customer);
            return new ResponseEntity<>(customer2,HttpStatus.OK);
        }catch(Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/customer/deletion/{cid}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable int cid){
        try{
            customerService.deleteByCid(cid);
            if( customerService.deleteByCid(cid)){
                return new ResponseEntity<>(HttpStatus.OK);
            }else{return new ResponseEntity<>(HttpStatus.NO_CONTENT);}
        }catch(Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}










