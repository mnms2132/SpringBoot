package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class MemberController {

    @Autowired // 스프링부트가 미리 생성해놓은 리파지터리 객체 주입(DI)
    private MemberRepository memberRepository;

    @GetMapping("/signup")
    public String signup(){
        return "/members/new";
    }

    @PostMapping("/join")
    public String join(MemberForm memberForm){
        log.info(memberForm.toString());

        //1. DTO를 엔티티로 변환
        Member member = memberForm.toEntity();
        //2. 리파지터리로 엔티티를 DB에 저장
        //Member saved = memberRepository.save(member); // article 엔티티를 저장해 save객체에 반환
        Member saved = memberRepository.save(member);
        log.info(saved.toString());
        return "";
    }
}
