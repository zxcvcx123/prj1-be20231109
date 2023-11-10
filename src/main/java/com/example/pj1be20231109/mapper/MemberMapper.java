package com.example.pj1be20231109.mapper;

import com.example.pj1be20231109.domain.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {

    @Insert("""
            INSERT INTO member (id, password, email)
            VALUES (#{id}, #{password}, #{email})
            """)
    int insert(Member member);


    @Select("""
            SELECT id FROM member
            WHERE id = #{id}
            """)
    String selectId(String id);

    @Select("""
            SELECT email FROM member
            WHERE email = #{email}
            """)
    String selectEmail(String email);
}
