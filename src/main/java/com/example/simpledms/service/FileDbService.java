package com.example.simpledms.service;


import com.example.simpledms.model.FileDb;
import com.example.simpledms.repository.FileDbRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
public class FileDbService {

    @Autowired
    FileDbRepository fileDbRepository;

    public Page<FileDb> findAll(Pageable pageable){
        Page<FileDb> page = fileDbRepository.findAll(pageable);
        return page;
    }

    public Optional<FileDb> getFile(int id){
        Optional<FileDb> optionalFileDb = fileDbRepository.findById(id);
        return optionalFileDb;
    }

    public boolean deleteById(int id){
        if(fileDbRepository.existsById(id)){
            fileDbRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    public Page<FileDb> findByFileTitle(String title,Pageable pageable){
        Page<FileDb> fileDbPage= fileDbRepository.findAllByFileTitleContaining(title,pageable);
                return fileDbPage;
    }

    public FileDb store(String fileTitle,
                                     String fileContent,
                                     MultipartFile file) throws IOException
    {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        FileDb fileDb = new FileDb(fileTitle,
                                                        fileContent,
                                                        fileName,
                                                        file.getContentType(),
                                                        file.getBytes());

        return fileDbRepository.save(fileDb);
    }

}
