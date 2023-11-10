package com.example.pj1be20231109.service;

import com.example.pj1be20231109.domain.Member;
import com.example.pj1be20231109.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper mapper;

    public void add(Member member){
        mapper.insert(member);
    }

    public String getId(String id) {
       return mapper.selectId(id);
    }
}
