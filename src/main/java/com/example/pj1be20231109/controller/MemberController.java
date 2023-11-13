package com.example.pj1be20231109.controller;

import com.example.pj1be20231109.domain.Member;
import com.example.pj1be20231109.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("list")
    public List<Member> list(){
       return service.list();
    }

    @GetMapping
    public ResponseEntity<Member> view(String id){

        // TODO: 로그인 했는지? -> 안했으면 401
        // TODO: 본인 정보인지? -> 아니면 403

       Member member = service.getMember(id);

        return ResponseEntity.ok(member);
    }

    @DeleteMapping
    public ResponseEntity delete(String id){

        // TODO: 로그인 했는지? -> 안했으면 401
        // TODO: 본인 정보인지? -> 아니면 403

       if( service.deleteMember(id)){
           return ResponseEntity.ok().build();
       }

       return ResponseEntity.internalServerError().build();
    }
    
    @PutMapping("/edit")
    public ResponseEntity edit(@RequestBody Member member) {

        // TODO: 로그인 했는지? -> 안했으면 401
        // TODO: 본인 정보인지? -> 아니면 403

        if(service.update(member)){
            return ResponseEntity.ok().build();
        } else {

            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping(value = "check", params = "nickname")
    public ResponseEntity checkNickName(String nickname){

        System.out.println("nickname = " + nickname);

        if(service.getNickName(nickname) == null){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }
}
