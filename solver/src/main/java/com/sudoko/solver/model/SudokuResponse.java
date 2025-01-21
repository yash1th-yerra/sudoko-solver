package com.sudoko.solver.model;


public class SudokuResponse {
    private String message;
    private int[][] solvedGrid;

    public SudokuResponse(String message) {
        this.message = message;
    }

    public SudokuResponse(String message, int[][] solvedGrid) {
        this.message = message;
        this.solvedGrid = solvedGrid;
    }

    public String getMessage() {
        return message;
    }

    public int[][] getSolvedGrid() {
        return solvedGrid;
    }
}
