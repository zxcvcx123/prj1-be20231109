package com.example.pj1be20231109.service;

import com.example.pj1be20231109.domain.Comment;
import com.example.pj1be20231109.domain.Member;
import com.example.pj1be20231109.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {


    private final CommentMapper mapper;

    public boolean add(Comment comment, Member login) {

        comment.setMemberId(login.getId());
        return mapper.insert(comment) == 1;


    }

    public boolean validate(Comment comment) {

        if(comment == null){
            return false;
        }

        if (comment.getBoardId() == null || comment.getBoardId() < 1) {
            return false;
        }

        if(comment.getComment() == null || comment.getComment().isBlank()){
            return false;
        }

        return true;
    }

    public List<Comment> getCommentList(Comment comment) {

        return mapper.getCommetList(comment);
    }

    public void remove(Integer id) {

        mapper.deleteById(id);
    }
}


