package com.example.pj1be20231109.mapper;

import com.example.pj1be20231109.domain.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {


    @Insert("""
            INSERT INTO comment (boardId, comment, memberId)
            VALUES (#{boardId}, #{comment}, #{memberId})
            """)
    int insert(Comment comment);

    @Select("""
            SELECT * FROM comment
            WHERE boardId = #{boardId}
            """)
    List<Comment> getCommetList(Comment comment);

    @Delete("""
            DELETE FROM comment
            WHERE id = #{id}
            """)
    void deleteById(Integer id);
}
