package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor // Article()생성자 대체, 클래스 안쪽의 모든 필드를 매개변수로 하는 생성자 자동 생성
@NoArgsConstructor // 기본 생성자 추가 어노테이션
@ToString // ToString() 매서드 대체
@Entity
@Getter
public class Article {
    @Id //엔티티의 대푯값 지정
    @GeneratedValue //자동생성 기능 추가(숫자가 자동으로 매겨짐)
    private Long id;
    @Column //title 필드 선언, DB 테이블의 title 열과 연결됨
    private String title;
    @Column //content 필드 선언, DB 테이블의 content 열과 연결됨
    private String content;

}
