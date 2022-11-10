package com.example.simpledms.service;


import com.example.simpledms.model.Emp;
import com.example.simpledms.model.Faq;
import com.example.simpledms.repository.EmpRepository;
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
public class FaqService {

    @Autowired
    FaqRepository faqRepository; // JPA CRUD 함수가 있는 인터페이스

    //    전체 조회 함수
    public List<Faq> findAll() {
        List<Faq> list = faqRepository.findAll();

        return list;
    }

    public void removeAll() {
        faqRepository.deleteAll(); // 전체 삭제
    }

    public Faq save(Faq faq){
        Faq faq2 = faqRepository.save(faq);
        return faq2;
    }

    public Optional<Faq> getById(int no){
        Optional<Faq> optionalFaq = faqRepository.findById(no);
        return optionalFaq;
    }

    public boolean deleteByNo(int no){
        if(faqRepository.existsById(no)){
            faqRepository.deleteById(no);
            return true;
        }else{
            return false;
        }
    }

    public Page<Faq> findByTitle(String title, Pageable pageable){
        Page<Faq> page = faqRepository.findAllByTitleContaining(title,pageable);
        return page;
    }

}





