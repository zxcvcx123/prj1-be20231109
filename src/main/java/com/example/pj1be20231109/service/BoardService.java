package com.example.pj1be20231109.service;

import com.example.pj1be20231109.domain.Board;
import com.example.pj1be20231109.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {


    private final BoardMapper mapper;

    public void save(Board board) {
        mapper.insert(board);
    }
}
