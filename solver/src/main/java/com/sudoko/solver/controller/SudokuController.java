package com.sudoko.solver.controller;


import com.sudoko.solver.model.SudokuRequest;
import com.sudoko.solver.model.SudokuResponse;
import com.sudoko.solver.service.SudokuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/solve-sudoku")
public class SudokuController {

    @Autowired
    private SudokuService sudokuService;

    @PostMapping
    public ResponseEntity<SudokuResponse> solveSudoku(@RequestBody SudokuRequest request) {
        int[][] grid = request.getGrid();

        if (grid == null || grid.length != 9 || grid[0].length != 9) {
            return ResponseEntity.badRequest().body(new SudokuResponse("Invalid grid size"));
        }

        boolean solved = sudokuService.solveSudoku(grid);

        if (solved) {
            return ResponseEntity.ok(new SudokuResponse("Success", grid));
        } else {
            return ResponseEntity.badRequest().body(new SudokuResponse("Failed to solve Sudoku"));
        }
    }
}
