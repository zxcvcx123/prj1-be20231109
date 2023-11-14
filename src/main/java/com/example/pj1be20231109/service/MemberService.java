package com.example.pj1be20231109.service;

import com.example.pj1be20231109.domain.Member;
import com.example.pj1be20231109.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper mapper;

    public boolean add(Member member){
        return mapper.insert(member) == 1;
    }

    public String getId(String id) {
       return mapper.selectId(id);
    }

    public String getEmail(String email) {
        return mapper.selectEmail(email);
    }

    public boolean validate(Member member) {

        if(member == null){
            return false;
        }

        if(member.getEmail().isBlank()){
            return false;
        }

        if(member.getPassword().isBlank()){
            return false;
        }

        if(member.getId().isBlank()){
            return false;
        }

        if(member.getNickname().isBlank()){
            return false;
        }

        return true;
    }

    public List<Member> list() {
        return mapper.selectAll();
    }

    public Member getMember(String id) {
        return mapper.selectById(id);
    }

    public boolean deleteMember(String id) {

        return mapper.deleteById(id) == 1;
    }

    public boolean update(Member member) {

//        Member oldMember = mapper.selectById(member.getId());
//        if (member.getPassword().equals("")) {
//            member.setPassword(oldMember.getPassword());
//        }


        return mapper.update(member) == 1;
    }


    public String getNickName(String nickname) {
        return mapper.getNickName(nickname);
    }

    public boolean login(Member member, WebRequest request) {
       Member dbMember = mapper.selectById(member.getId());

       if(dbMember != null){
           if(dbMember.getPassword().equals(member.getPassword())){
               dbMember.setPassword("");
               request.setAttribute("login", dbMember, RequestAttributes.SCOPE_SESSION);
               return true;
           }
       }

       return false;

    }
}
