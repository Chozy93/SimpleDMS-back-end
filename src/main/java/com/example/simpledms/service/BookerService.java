package com.example.simpledms.service;


import com.example.simpledms.model.Booker;
import com.example.simpledms.repository.BookerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
public class BookerService {

    @Autowired
    BookerRepository bookerRepository; // JPA CRUD 함수가 있는 인터페이스





    public Booker save(Booker book){
        Booker book2 = bookerRepository.save(book);
        return book2;
    }

    public Optional<Booker> findById(int qno){
        Optional<Booker> optionalBooker = bookerRepository.findById(qno);
        return optionalBooker;
    }

    public boolean deleteByBkno(int bkno){
        if(bookerRepository.existsById(bkno)){
            bookerRepository.deleteById(bkno);
            return true;
        }else{
            return false;
        }
    }

    public Page<Booker> findByName(String name, Pageable pageable){
        Page<Booker> page = bookerRepository.findAllByNameEquals(name,pageable);
        return page;
    }
    public Page<Booker> findByPhone(String phone, Pageable pageable){
        Page<Booker> page = bookerRepository.findAllByPhoneEquals(phone,pageable);
        return page;
    }

}





