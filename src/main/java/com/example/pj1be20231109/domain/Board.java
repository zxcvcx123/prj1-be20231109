package com.example.pj1be20231109.domain;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Board {

    private Integer id;
    private String title;
    private String content;
    private String writer;
    private String nickname;
    private LocalDateTime inserted;
}
