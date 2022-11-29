package com.example.simpledms.model;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "TB_BOOK")
@SequenceGenerator(
        name= "SQ_BOOK_GENERATOR"
        , sequenceName = "SQ_BOOK"
        , initialValue = 1
        , allocationSize = 1
)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
// null 인 컬럼 데이터는 sql 문 자동 생성시 제외시킴
@DynamicInsert
@DynamicUpdate
public class Book extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE
            , generator = "SQ_BOOK_GENERATOR"
    )
    private Integer bno;
    @Column(columnDefinition = "VARCHAR2(255)")
    private String destination;
    @Column(columnDefinition = "VARCHAR2(255)")
    private String schedule;
    @Column(columnDefinition = "NUMBER")
    private Integer price;
    @Column(columnDefinition = "VARCHAR2(255)")
    private String manager;



}