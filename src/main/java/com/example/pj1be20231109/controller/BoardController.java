package com.example.pj1be20231109.controller;

import com.example.pj1be20231109.domain.Board;
import com.example.pj1be20231109.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Delete;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService service;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody Board board){

        if(!service.validate(board)){
            return ResponseEntity.badRequest().build();
        }

        if(service.save(board)){
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
    public ResponseEntity remove(@PathVariable Integer id){

        if(service.remove(id)){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/edit")
    public void edit(@RequestBody Board board){

        service.update(board);

    }

}
