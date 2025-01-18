package com.sudoko.solver.controller;


import com.sudoko.solver.model.SudokoRequest;
import com.sudoko.solver.service.SudokoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/solve-sudoko")
public class SudokoController {

    @Autowired
    private SudokoService sudokoService;


    @PostMapping
    public ResponseEntity<int[][]> solveSudoko(@RequestBody SudokoRequest request){
        int[][] grid = request.getGrid();

        if(grid==null || grid.length!=9 || grid[0].length!=9) return ResponseEntity.badRequest().build();

        boolean solved = sudokoService.solveSudoko(grid);
        if(solved) return ResponseEntity.ok(grid);
        else return ResponseEntity.badRequest().body(null);

    }

}
