package com.example.simpledms.service;


import com.example.simpledms.model.Book;
import com.example.simpledms.repository.BookRepository;
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
public class BookService {

    @Autowired
    BookRepository bookRepository; // JPA CRUD 함수가 있는 인터페이스





    public Book save(Book book){
        Book book2 = bookRepository.save(book);
        return book2;
    }

    public Optional<Book> findById(int qno){
        Optional<Book> optionalBook = bookRepository.findById(qno);
        return optionalBook;
    }

    public boolean deleteByQno(int qno){
        if(bookRepository.existsById(qno)){
            bookRepository.deleteById(qno);
            return true;
        }else{
            return false;
        }
    }

    public Page<Book> findByDestination(String question, Pageable pageable){
        Page<Book> page = bookRepository.findAllByDestinationContaining(question,pageable);
        return page;
    }
    public Page<Book> findBySchedule(String questioner, Pageable pageable){
        Page<Book> page = bookRepository.findAllByScheduleContaining(questioner,pageable);
        return page;
    }

}





