package com.example.pj1be20231109.controller;

import com.example.pj1be20231109.domain.Board;
import com.example.pj1be20231109.domain.Member;
import com.example.pj1be20231109.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Delete;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService service;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody Board board, @SessionAttribute(value = "login", required = false) Member login){

        if(login == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if(!service.validate(board)){
            return ResponseEntity.badRequest().build();
        }

        if(service.save(board, login)){
           return ResponseEntity.ok().build();
        } else {
           return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/list")
    public List<Board> list(){
        return service.list();
    }

    @GetMapping("/id/{id}")
    public Board get(@PathVariable Integer id){
        return service.get(id);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity remove(@PathVariable Integer id, @SessionAttribute(value = "login", required = false) Member login){


        if(login == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401
        }

        if(!service.hasAccess(id, login)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403
        }

        if(service.remove(id)){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/edit")
    public ResponseEntity edit(@RequestBody Board board, @SessionAttribute(value = "login",required = false) Member login) {

        if(login == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if(!service.hasAccess(board.getId(), login)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }


        if (service.validate(board)) {

            if (service.update(board)) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.internalServerError().build();
            }

        } else {
            return ResponseEntity.badRequest().build();
        }

    }

}
