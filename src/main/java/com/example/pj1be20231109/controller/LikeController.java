package com.example.pj1be20231109.controller;

import com.example.pj1be20231109.domain.Like;
import com.example.pj1be20231109.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class LikeController {

    private final LikeService service;

    @PostMapping
    public void like(@RequestBody Like like){
        service.update(like);
    }

}
