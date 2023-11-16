package com.example.pj1be20231109.mapper;

import com.example.pj1be20231109.domain.Like;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

public interface LikeMapper {

    @Delete("""
            DELETE FROM boardLike
            WHERE boardId = #{boardId}
                AND memberId = #{memberId}
            """)
    int delete(Like like);

    @Insert("""
            INSERT INTO boardLike (boardId, memberId)
            VALUES (#{boardId}, #{memberId})
            """)
    int insert(Like like);
}
