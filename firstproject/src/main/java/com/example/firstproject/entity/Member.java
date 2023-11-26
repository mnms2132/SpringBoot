package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor // Article()생성자 대체, 클래스 안쪽의 모든 필드를 매개변수로 하는 생성자 자동 생성
@ToString // ToString() 매서드 대체
@Entity
public class Member {
    @Id //엔티티의 대푯값 지정
    @GeneratedValue //자동생성 기능 추가(숫자가 자동으로 매겨짐)
    Long id;
    @Column //email 필드 선언, DB 테이블의 email 열과 연결됨
    String email;
    @Column //password 필드 선언, DB 테이블의 password 열과 연결됨
    String password;

}
