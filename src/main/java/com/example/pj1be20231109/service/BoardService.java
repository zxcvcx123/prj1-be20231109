package com.example.pj1be20231109.service;

import com.example.pj1be20231109.domain.Board;
import com.example.pj1be20231109.domain.Like;
import com.example.pj1be20231109.domain.Member;
import com.example.pj1be20231109.mapper.BoardMapper;
import com.example.pj1be20231109.mapper.CommentMapper;
import com.example.pj1be20231109.mapper.LikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardService {


    private final BoardMapper mapper;
    private final CommentMapper commentMapper;
    private final LikeMapper likeMapper;

    public boolean save(Board board, Member login) {

        board.setWriter(login.getId());

        return mapper.insert(board) == 1;
    }

    public boolean validate(Board board) {

        if (board == null) {
            return false;
        }

        if (board.getContent() == null || board.getContent().isBlank()) {
            return false;
        }

        if (board.getTitle() == null || board.getTitle().isBlank()) {
            return false;
        }


        return true;

    }

    public Map<String, Object> list(Integer page, String keyword) {

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> pageInfo = new HashMap<>();

        int from = (page - 1) * 10;

        int countAll = mapper.countAll();
        int lastPageNumber = (countAll - 1) / 10 +1;
        int startPageNumber = (page - 1) / 10 * 10 + 1;
        int endPageNumber = startPageNumber + (10-1);
        endPageNumber = Math.min(endPageNumber, lastPageNumber);

        int prevPageNumber = startPageNumber - 10;
        int nextPageNumber = endPageNumber + 1;

        pageInfo.put("startPageNumber", startPageNumber);
        pageInfo.put("endPageNumber", endPageNumber);
        pageInfo.put("lastPageNumber", lastPageNumber);
        pageInfo.put("currentPageNumber", page);
        if(prevPageNumber > 0){
            pageInfo.put("prevPageNumber", prevPageNumber);
        }

        if(lastPageNumber >= nextPageNumber) {
            pageInfo.put("nextPageNumber", nextPageNumber);
        }


        map.put("boardList", mapper.selectAll(from, "%" + keyword + "%"));
        map.put("pageInfo", pageInfo);

        return map;
    }

    public Board get(Integer id) {
        return mapper.selectById(id);
    }

    public boolean remove(Integer id) {

        // 1. 게시물에 달린 댓글 삭제
        commentMapper.deleteByBoardId(id);

        // 게시물에 달린 좋아요 삭제
        likeMapper.deleteByBoardId(id);
        
        return mapper.deleteById(id) == 1;
    }

    public boolean update(Board board) {
        return  mapper.update(board) == 1;
    }

    public boolean hasAccess(Integer id, Member login) {

        if(login == null){
            return false;
        }

        if(login.isAdmin()){
            return true;
        }

        Board board = mapper.selectById(id);

        return board.getWriter().equals(login.getId());

    }


}
