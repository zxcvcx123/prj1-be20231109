package com.example.pj1be20231109.service;

import com.example.pj1be20231109.domain.Board;
import com.example.pj1be20231109.domain.Like;
import com.example.pj1be20231109.domain.Member;
import com.example.pj1be20231109.mapper.BoardMapper;
import com.example.pj1be20231109.mapper.CommentMapper;
import com.example.pj1be20231109.mapper.FileMapper;
import com.example.pj1be20231109.mapper.LikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class) // 모든 Exception 발생하면 RollBack
public class BoardService {


    private final BoardMapper mapper;
    private final CommentMapper commentMapper;
    private final LikeMapper likeMapper;
    private final FileMapper fileMapper;

    private final S3Client s3;

    @Value("${aw3.s3.bucket.name}")
    private String bucket;


    public boolean save(Board board, MultipartFile[] files, Member login) throws Exception {

        // 게시판 글 제목, 내용 저장
        board.setWriter(login.getId());
        int cnt = mapper.insert(board);

        // 게시판 파일 정보 DB에 저장 boardFile 테이블에 files 정보 저장
        if(files != null) {

            // 반복문으로 배열 형태를 하나씩 뺀다
            for(int i = 0; i < files.length; i++) {

                // 테이블에 정보 저장
                fileMapper.insert(board.getId(), files[i].getOriginalFilename());

                // 게시판 파일 실제 디렉토리에 저장 (local PC에 저장)
                upload(files[i], board.getId());
            }
        }




        // 게시판 파일 실제 디렉토리에 저장 (AWS S3 bucket에 upload)


        return cnt == 1;
    }

    private void upload(MultipartFile file, Integer boardId) throws Exception {
//        // 로컬에 저장
//        // 파일 저장 경로
//        // C:\Temp\prj1\게시물번호\파일명
//
//
//        // 경로에 파일있는지 체크하고 없으면 생성
//        File folder = new File("C:\\Temp\\prj1\\" + boardId);
//        if (!folder.exists()) {
//            folder.mkdirs();
//        }
//
//        // 파일 실제 저장
//        String path = folder.getAbsolutePath() + "\\" + file.getOriginalFilename();
//        File des = new File(path);
//        file.transferTo(des);

        // aws s3에 저장

        String key = "prj1/" + boardId + "/" + file.getOriginalFilename();

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucket)   // 버킷이름
                .key(key)         // key(파일경로)
                .acl(ObjectCannedACL.PUBLIC_READ) // 권한
                .build();

        s3.putObject(objectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

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


        int countAll = mapper.countAll("%"+keyword+"%");
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
