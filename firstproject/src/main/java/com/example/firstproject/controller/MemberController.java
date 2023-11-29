package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class MemberController {

    @Autowired // 스프링부트가 미리 생성해놓은 리파지터리 객체 주입(DI)
    MemberRepository memberRepository;

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
        return "redirect:/members/" + saved.getId();
    }

    @GetMapping("/members/{id}")
    public String show(@PathVariable Long id, Model model){

        Member memberEntity = memberRepository.findById(id).orElse(null);

        model.addAttribute("member", memberEntity);
        return "members/show";
    }

    @GetMapping("/members")
    public String index(Model model){
        List<Member> MemberEntityList = memberRepository.findAll();
        model.addAttribute("memberList",MemberEntityList);
        return "members/index";
    }

    @GetMapping("/members/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        //1. 수정할 데이터 가져오기
        Member memberEntity = memberRepository.findById(id).orElse(null);
        //2. 모델에 데이터 등록하기
        model.addAttribute("member", memberEntity);
        //3. 뷰페이지 설정하기
        return "members/edit";
    }

    @PostMapping("/members/update")
    public String update (MemberForm form){
        log.info(form.toString());
        //1. DTO를 엔티티로 변환하기
        Member memberEntity = form.toEntity();
        log.info(memberEntity.toString());
        //2. 엔티티를 DB에 저장하기
        Member target = memberRepository.findById(memberEntity.getId()).orElse(null);
        if (target != null) {
            memberRepository.save(memberEntity); //DB저장 갱신
        }
        //3. 수정 결과 페이지로 리다이렉트하기
        return "redirect:/members/"+memberEntity.getId();
    }

    @GetMapping("/members/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info(id.toString());
        log.info("삭제요청이들어왔습니다!");
        //1. 데이터 가져오기
        Member target = memberRepository.findById(id).orElse(null);
        //2. 데이터 삭제하기
        if (target != null) {
            memberRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제됐습니다!");
        }
        //3. 결과페이지 반환하기
        return "redirect:/members";
    }

}
