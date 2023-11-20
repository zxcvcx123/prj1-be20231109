package com.example.pj1be20231109.mapper;

import com.example.pj1be20231109.domain.Board;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardMapper {

    @Insert("""
                INSERT INTO board (title, content, writer)
                VALUES (#{title}, #{content}, #{writer})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Board board);

    @Select("""
            SELECT b.id,
                   b.title,
                   b.writer,
                   m.nickname  nickname,
                   b.inserted,
                   COUNT(c.id) countComment,
                   (SELECT COUNT(*) FROM boardLike bl WHERE b.id = bl.boardId ) countLike
            FROM board b
                     JOIN member m
                          ON b.writer = m.id
                     LEFT JOIN comment c
                               on b.id = c.boardId
            WHERE b.content LIKE #{keyword}
                OR b.title LIKE #{keyword}
            GROUP BY b.id
            ORDER BY id DESC
            LIMIT #{from}, 10
            """)
    List<Board> selectAll(Integer from, String keyword);


    @Select("""
                SELECT b.id, b.title, b.content, b.writer, m.nickname nickname, b.inserted
                FROM board b JOIN member m
                    ON b.writer = m.id
                WHERE b.id = #{id}
            """)
    Board selectById(Integer id);

    @Delete("""
                    DELETE FROM board
                    WHERE id = #{id}
            """)
    int deleteById(Integer id);

    @Update("""
                UPDATE board
                SET title = #{title},
                    content = #{content},
                    writer = #{writer}
                WHERE id = #{id}
            """)
    int update(Board board);

    @Delete("""
                DELETE FROM board
                WHERE writer = #{writer}
            """)
    int deleteByWriter(String writer);


    @Select("""
                SELECT id
                FROM  board
                WHERE writer = #{writer}
            """)
    List<Integer> selectIdListByMemberId(String writer);

    @Select("""
            SELECT COUNT(*) 
            FROM board
            WHERE content LIKE #{keyword}
                OR title LIKE #{keyword} 
            """)
    int countAll(String keyword);
}
