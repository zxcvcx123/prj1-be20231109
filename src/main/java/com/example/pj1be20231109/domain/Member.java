package com.example.pj1be20231109.domain;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Member {

    private String id;
    private String password;
    private String email;

    private String nickname;
    private LocalDateTime inserted;
    private List<Auth> auth;

}
