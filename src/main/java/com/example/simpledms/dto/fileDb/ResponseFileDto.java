package com.example.simpledms.dto.fileDb;

import lombok.*;

import javax.persistence.*;

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
public class ResponseFileDto {

    private Integer fid;

    private String fileTitle;

    private String fileContent;

    private String fileName;

    private String fileType;

    private Integer fileSize; //이미지 크기

    private String fileUrl; //이미지다운로드 url

}
