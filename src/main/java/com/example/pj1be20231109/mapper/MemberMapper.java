package com.example.pj1be20231109.mapper;

import com.example.pj1be20231109.domain.Member;
import org.apache.ibatis.annotations.*;

import java.util.List;

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

    @Select("""
            SELECT *
            FROM member
            ORDER BY inserted DESC
            """)
    List<Member> selectAll();

    @Select("""
            SELECT *
            FROM member
            WHERE id = #{id}
            """)
    Member selectById(String id);

    @Delete("""
            DELETE FROM member
            WHERE id = #{id}
            """)
    int deleteById(String id);

    @Update("""
            UPDATE member
            SET password = #{member.password},
                email = #{member.email}
            WHERE id = #{id}
            """)
    int updateByMember(String id, Member member);
}
