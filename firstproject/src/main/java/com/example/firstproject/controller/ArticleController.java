package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Slf4j // 로깅 기능을 위한 어노테이션 추가
@Controller
public class ArticleController {
    @Autowired // 스프링부트가 미리 생성해놓은 리파지터리 객체 주입(DI)
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "/articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        //System.out.println(form.toString());
        log.info(form.toString());
        //1. DTO를 엔티티로 변환
        Article article = form.toEntity();
        //System.out.println(article.toString()); // DTO가 엔티티로 잘 변환 되는지 확인 출력
        log.info(article.toString());
        //2. 리파지터리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article); // article 엔티티를 저장해 save객체에 반환
        //System.out.println(saved.toString()); // article이 DB에 잘 저장 되는지 확인 출력
        log.info(saved.toString());
        return"redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id = "+id);
        // 1. id를 조회해 데이터 가져오기
        //Optional<Article> articleEntity = articleRepository.findById(id);
        Article articleEntity = articleRepository.findById(id).orElse(null); // id값이 없으면 null을 반환
        // 2. 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);
        // 3. 뷰 페이지 반환하기
        return "articles/show";

    }

    @GetMapping("/articles")
    public String index(Model model) {

        //1.DB에서 모든 Article 데이터 가져오기
        //List<Article>articleEntityList = (List<Article>) articleRepository.findAll(); 반환하는 데이터타입을 Iterable<> -> List<>로 다운캐스팅
        //Iterator<Article>articleEntityList = articleRepository.findAll(); 반환하는 데이터타입을 List<> -> Iterable<>로 업캐스팅
        List<Article>articleEntityList = articleRepository.findAll();

        //2.가져온 Article묶음을 모델에 등록하기
        model.addAttribute("articleList", articleEntityList);

        //3.사용자에게 보여줄 뷰 페이지 설정하기
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        //수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        //모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);
        //뷰페이지 설정하기
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        log.info(form.toString());
        //1. DTO를 엔티티로 변환하기
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());
        //2. 엔티티를 DB에 저장하기
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        if (target !=null){
            articleRepository.save(articleEntity);//엔티티를 DB에 저장(갱신)
        }
        //3. 수정 결과 페이지로 리다이렉트하기
        return "redirect:/articles/"+articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제 요청이 들어왔습니다!!");
        //1. 삭제할 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);
        //2. 대상 엔티티 삭제하기
        if(target!=null){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제됐습니다!");
        }
        //3. 결과 페이지로 리다이렉트하기
        return "redirect:/articles";
    }
}
