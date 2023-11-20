package com.example.pj1be20231109.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FileMapper {


    @Select("""
            INSERT INTO boardFile (boardId, fileName)
            VALUES (#{boardId}, #{fileName})
            """)
    void insert(Integer boardId, String fileName);
}
