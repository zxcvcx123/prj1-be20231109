package com.example.pj1be20231109.controller;

import com.example.pj1be20231109.domain.Like;
import com.example.pj1be20231109.domain.Member;
import com.example.pj1be20231109.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class LikeController {

    private final LikeService service;

    @PostMapping
    public ResponseEntity like(@RequestBody Like like,
                     @SessionAttribute(value = "login", required = false)Member login){

        if(login == null){
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        service.update(like, login);

        return ResponseEntity.ok().build();
    }

}
