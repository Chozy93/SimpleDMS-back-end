package com.example.simpledms.dto.gallery;

import lombok.*;

/**
 * packageName : com.example.simpledms.dto.fileDb
 * fileName : ResponseFileDto
 * author : ds
 * date : 2022-11-10
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-10         ds          최초 생성
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponseGalleryDto {

    private Integer gid;

    private String galleryTitle;

    private String galleryFileName;

    private String galleryType;

    private Integer gallerySize; //이미지 크기

    private String galleryUrl; //이미지다운로드 url

}
