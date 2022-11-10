package com.example.simpledms.service;


import com.example.simpledms.model.Dept;
import com.example.simpledms.model.Emp;

import com.example.simpledms.repository.EmpRepository;
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
public class EmpService {

    @Autowired
    EmpRepository empRepository; // JPA CRUD 함수가 있는 인터페이스

    //    전체 조회 함수
    public List<Emp> findAll() {
        List<Emp> list = empRepository.findAll();

        return list;
    }

    public void removeAll() {
        empRepository.deleteAll(); // 전체 삭제
    }

    public Emp save(Emp emp){
        Emp emp2 = empRepository.save(emp);
        return emp2;
    }

    public Optional<Emp> findById(int eno){
        Optional<Emp> optionalEmp = empRepository.findById(eno);
        return optionalEmp;
    }

    public boolean deleteByEno(int eno){
        if(empRepository.existsById(eno)){
            empRepository.deleteById(eno);
            return true;
        }else{
            return false;
        }
    }

    public Page<Emp> findByEname(String ename, Pageable pageable){
        Page<Emp> page = empRepository.findAllByEnameContaining(ename,pageable);
        return page;
    }

}





