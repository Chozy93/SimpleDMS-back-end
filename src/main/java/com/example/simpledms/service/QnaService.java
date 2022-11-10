package com.example.simpledms.service;


import com.example.simpledms.model.Qna;
import com.example.simpledms.repository.QnaRepository;

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
public class QnaService {

    @Autowired
    QnaRepository qnaRepository; // JPA CRUD 함수가 있는 인터페이스





    public Qna save(Qna qna){
        Qna qna2 = qnaRepository.save(qna);
        return qna2;
    }

    public Optional<Qna> findById(int qno){
        Optional<Qna> optionalQna = qnaRepository.findById(qno);
        return optionalQna;
    }

    public boolean deleteByQno(int qno){
        if(qnaRepository.existsById(qno)){
            qnaRepository.deleteById(qno);
            return true;
        }else{
            return false;
        }
    }

    public Page<Qna> findByQuestion(String question, Pageable pageable){
        Page<Qna> page = qnaRepository.findAllByQuestionContaining(question,pageable);
        return page;
    }
    public Page<Qna> findByQuestioner(String questioner, Pageable pageable){
        Page<Qna> page = qnaRepository.findAllByQuestionerContaining(questioner,pageable);
        return page;
    }

}





