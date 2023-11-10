package com.example.pj1be20231109.domain;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Member {

    private String id;
    private String password;
    private String email;
    private LocalDateTime inserted;

}
