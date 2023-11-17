package com.example.pj1be20231109.domain;


import com.example.pj1be20231109.util.AppUtil;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

@Data
public class Board {

    private Integer id;
    private String title;
    private String content;
    private String writer;
    private String nickname;
    private Integer countComment;
    private Integer countLike;
    private LocalDateTime inserted;

    public String getAgo(){
        return AppUtil.getAgo(inserted, LocalDateTime.now());
    }
}

