package com.example.simpledms.service;


import com.example.simpledms.model.FileDb;
import com.example.simpledms.model.Gallery;
import com.example.simpledms.repository.FileDbRepository;
import com.example.simpledms.repository.GalleryRepository;
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
public class GalleryService {

    @Autowired
    GalleryRepository galleryRepository;


    public Page<Gallery> findAll(Pageable pageable){
        Page<Gallery> page = galleryRepository.findAll(pageable);
        return page;
    }

    public Optional<Gallery> getGallery(int id){
        Optional<Gallery> optionalFileDb = galleryRepository.findById(id);
        return optionalFileDb;
    }

    public boolean deleteById(int id){
        if(galleryRepository.existsById(id)){
            galleryRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    public Page<Gallery> findByFileTitle(String title,Pageable pageable){
        Page<Gallery> galleryPage= galleryRepository.findAllByGalleryTitleContaining(title,pageable);
                return galleryPage;
    }

    public Gallery store(String galleryTitle,
                                     MultipartFile file) throws IOException
    {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        Gallery gallery = new Gallery(galleryTitle,
                                                        fileName,
                                                        file.getContentType(),
                                                        file.getBytes());

        return galleryRepository.save(gallery);
    }

}
