package com.example.simpledms.service;


import com.example.simpledms.model.Dept;
import com.example.simpledms.repository.DeptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * packageName : com.example.jpaexam.service.exam01
 * fileName : DeptSerivce
 * author : ds
 * date : 2022-10-20
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-10-20         ds          최초 생성
 */
@Service
public class DeptService {

    @Autowired
    DeptRepository deptRepository;

    public Page<Dept> findAll(Pageable pageable){
        Page<Dept> page = deptRepository.findAll(pageable);
        return page;
    }

    public void removeAll(){
        deptRepository.deleteAll();
    }

    public Dept save(Dept dept){
        Dept dept2 = deptRepository.save(dept);
        return dept2;
    }
    public Optional<Dept> findById(int dno){
        Optional<Dept> optionalDept = deptRepository.findById(dno);

        //        데이터 가공
        Dept dept = optionalDept.get();

        dept.setDname(dept.getDname() +"_해킹");

//        가공데이터를 다시 옵셔널에 넣기
        Optional<Dept> optionalDeptnew = Optional.ofNullable(dept);

        return optionalDeptnew;

    }

    public boolean deleteByDno(int dno){
         if(deptRepository.existsById(dno)){
             deptRepository.deleteById(dno);
             return true;
        }else{
             return false;
         }
    }

    public Page<Dept> findAllByDnameContaining(String dname,Pageable pageable) {
        Page<Dept> page = deptRepository.findAllByDnameContaining(dname,pageable);

        return page;
    }



}
