package com.sudoko.solver.model;

public class SudokuRequest {
    private int[][] grid;
    public int[][] getGrid(){
        return grid;

    }
    public void setGrid(int[][] grid){
        this.grid = grid;
    }
}
