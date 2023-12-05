package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j //log를 찍을수 있는 어노테이션
@RestController
public class ArticleApiController {
@Autowired //게시글 리파지터리 주입
private ArticleRepository articleRepository;
    
//GET
@GetMapping("/api/articles")
public List<Article> index(){ //index() 메서드 정의
return articleRepository.findAll();
}

@GetMapping("/api/articles/{id}")
public Article show(@PathVariable Long id){ //url의 id를 매개변수로 받아오기
    return articleRepository.findById(id).orElse(null);
}
//POST
@PostMapping("/api/articles")
public Article create(@RequestBody ArticleForm dto){
    Article article = dto.toEntity(); //dto는 DB에서 활용할 수 있도록 엔티티로 변환해 ardticle변수에 넣어줌
    return articleRepository.save(article); //articleRepository를 통해 DB에 저장 후 반환
}

//PATCH
@PatchMapping("/api/articles/{id}")
public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){
    //1. DTO -> 엔티티 변환하기
    Article article = dto.toEntity();
    log.info("id: {}, article: {}", id, article.toString());
    //2. 타깃 조회하기
    Article target = articleRepository.findById(id).orElse(null);
    //3. 잘못된 요청 처리하기
    if (target == null || id != article.getId()){
        //400, 잘못된 요청 응답
        log.info("잘못된 요청! id:{}, article{}", id, article.toString()); //로그 찍기
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); //ResponseEntity 반환
    }
    //4. 업데이트 및 정상 응답(200)하기
    Article updated = articleRepository.save(article);
    return ResponseEntity.status(HttpStatus.OK).body(updated);
}
//DELETE
}
