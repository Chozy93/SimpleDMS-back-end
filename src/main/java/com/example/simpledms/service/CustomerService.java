package com.example.simpledms.service;


import com.example.simpledms.model.Customer;
import com.example.simpledms.model.Faq;
import com.example.simpledms.repository.CustomerRepository;
import com.example.simpledms.repository.FaqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * packageName : com.example.jpaexam.service.exam01
 * fileName : DeptService
 * author : ds
 * date : 2022-10-20
 * description : 부서 업무 서비스 클래스
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-10-20         ds          최초 생성
 */
@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository; // JPA CRUD 함수가 있는 인터페이스





    public Customer save(Customer customer){
        Customer customer2 = customerRepository.save(customer);
        return customer2;
    }

    public Optional<Customer> findById(int cid){
        Optional<Customer> optionalCustomer = customerRepository.findById(cid);
        return optionalCustomer;
    }

    public boolean deleteByCid(int cid){
        if(customerRepository.existsById(cid)){
            customerRepository.deleteById(cid);
            return true;
        }else{
            return false;
        }
    }

    public Page<Customer> findByEmail(String email, Pageable pageable){
        Page<Customer> page = customerRepository.findAllByEmailContaining(email,pageable);
        return page;
    }

}





