package com.example.simpledms.model;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * packageName : com.example.simpledms.model
 * fileName : Dept
 * author : ds
 * date : 2022-10-19
 * description : 부서 모델(== JPA : 엔티티(Entity)) 클래스
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-10-19         ds          최초 생성
 */
// JPA 매커니즘 : 클래스를 대상으로 테이블 자동 생성,
//          기본 제공하는 함수들은 자동으로 CRUD sql문을 만들어줌
//    sql문 작성에 필요한 노력을 절약함,
//    개발자는 제공하는 함수만 호출 -> sql 문 JPA 라이브러리가 알아서 생성해줌
//    비교) Mybatis : 개발자가 직접 자바소스에 sql 문을 작성
// @Entity : 대상 클래스을 참고하여 DB에 물리 테이블을 생성함
// @Table(name = "테이블명") : 자동 생성 시 물리 테이블명으로 생성됨
// @SequenceGenerator(각종 속성) : Oracle DB 시퀀스 생성시 사용할 속성들
// @DynamicInsert : insert 시 null 인 컬럼 제외해서 sql 문 자동 생성함
// @DynamicUpdate : update 시 null 인 컬럼 제외해서 sql 문 자동 생성함
// @Id : 기본키가 지정될 속성 -> DB에 기본키를 자동으로 만들어줌
// @Column(columnDefinition = "컬럼타입(개수)") : DB에 자동 생성될 테이블의 컬럼정보 직접 지정
@Entity
@Table(name = "TB_FILE")
@SequenceGenerator(
        name= "SQ_FILE_GENERATOR"
        , sequenceName = "SQ_FILE"
        , initialValue = 1
        , allocationSize = 1
)
@Getter
@Setter
@Builder
@NoArgsConstructor
// null 인 컬럼 데이터는 sql 문 자동 생성시 제외시킴
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class FileDb extends BaseTimeEntity {
    //    부서 번호 : dno
//    @Id : 기본키
//    시퀀스 사용 : ORACLE / POSTGRE 등
//    MYSQL /MARIA DB -> increment 이용
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE
                   , generator = "SQ_FILE_GENERATOR"
    )
    private Integer fid;

    @Column
    private String fileTitle;

    @Column
    private String fileContent;

    @Column
    private String fileName;
    @Column
    private String fileType;

//    CLOB 4000byte 보다 큰 문자열일때 사용
//    BLOB 이미지, 동영상 등을 저장하는 컬럼(binary file)
//    CLOB/BLOB 일 경우 @Lob 붙히기
//    CLOB/BLOB 는 보통 byte[] 자료형 사용
    @Lob
    @Column
    private byte[] fileData;

    public FileDb(String fileTitle, String fileContent, String fileName, String fileType, byte[] fileData) {
        this.fileTitle = fileTitle;
        this.fileContent = fileContent;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileData = fileData;
    }
//    FID          NUMBER NOT NULL PRIMARY KEY,
//    FILE_TITLE   VARCHAR2(1000),
//    FILE_CONTENT VARCHAR2(1000),
//    FILE_NAME    VARCHAR2(1000),
//    FILE_TYPE    VARCHAR2(1000),
//    FILE_DATA    BLOB,
//    DELETE_YN    VARCHAR2(1) DEFAULT 'N',
//    INSERT_TIME  VARCHAR2(255),
//    UPDATE_TIME  VARCHAR2(255),
//    DELETE_TIME  VARCHAR2(255)
}










