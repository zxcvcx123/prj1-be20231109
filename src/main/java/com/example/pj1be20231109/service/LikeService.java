package com.example.pj1be20231109.service;

import com.example.pj1be20231109.domain.Like;
import com.example.pj1be20231109.domain.Member;
import com.example.pj1be20231109.mapper.LikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeMapper mapper;

    public Map<String, Object> update(Like like, Member login) {
        // 처음 좋아요 누를 때 : insert
        // 좋아요 취소 할 때 : delete
        // 삭제시 1개 행이 삭제된거면 있으니깐 취소
        // 삭제시 0개 행이 삭제된거면 없으니깐 좋아요 생성
        like.setMemberId(login.getId());

        int count = 0;

        if (mapper.delete(like) == 0){
            count = mapper.insert(like);
        }

        int countLike = mapper.countByBoardId(like.getBoardId());

        return Map.of("like", count == 1, "countLike", countLike);
    }
}
