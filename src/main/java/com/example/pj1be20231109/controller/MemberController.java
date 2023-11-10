package com.example.pj1be20231109.controller;

import com.example.pj1be20231109.domain.Member;
import com.example.pj1be20231109.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService service;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody Member member){

        if(service.validate(member)){
           if( service.add(member)){
               return ResponseEntity.ok().build();
           } else {
               return ResponseEntity.internalServerError().build();
           }
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping(value = "check", params = "id")
    public ResponseEntity checkId(String id) {

        if(service.getId(id) == null){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping(value = "check", params = "email")
    public ResponseEntity checkEmail(String email){

        if(service.getEmail(email) == null){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

}
