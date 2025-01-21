package com.sudoko.solver.service;


import org.springframework.stereotype.Service;

@Service
public class SudokuService {
    public boolean solveSudoko(int[][] grid){
        boolean[][] row = new boolean[9][9];
        boolean[][] col = new boolean[9][9];
        boolean[][] gridTracker = new boolean[9][9];
        initializeTrackers(grid, row, col, gridTracker);
        return solve(0,0,grid,row,col,gridTracker);
    }

    private void initializeTrackers(int[][] grid, boolean[][] row, boolean[][] col, boolean[][] gridTracker) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (grid[r][c] != 0) {
                    int num = grid[r][c] - 1; // Convert to 0-based index
                    row[r][num] = true;
                    col[c][num] = true;
                    gridTracker[getGridIndex(r, c)][num] = true;
                }
            }
        }
    }

    private boolean isValid(int r, int c, int num, boolean[][] row, boolean[][] col, boolean[][] gridTracker) {
        return !row[r][num] && !col[c][num] && !gridTracker[getGridIndex(r, c)][num];
    }

    private int getGridIndex(int r, int c) {
        return (r / 3) * 3 + (c / 3);
    }

    private boolean solve(int r, int c, int[][] grid, boolean[][] row, boolean[][] col, boolean[][] gridTracker) {
        // Move to the next row if we reach the end of the current row
        if (c == 9) {
            c = 0;
            r++;
        }

        // If we've completed all rows, return true
        if (r == 9) {
            return true;
        }

        // Skip pre-filled cells
        if (grid[r][c] != 0) {
            return solve(r, c + 1, grid, row, col, gridTracker);
        }

        // Try filling the cell with numbers 1 to 9
        for (int num = 0; num < 9; num++) {
            if (isValid(r, c, num, row, col, gridTracker)) {
                // Place the number
                grid[r][c] = num + 1;
                row[r][num] = true;
                col[c][num] = true;
                gridTracker[getGridIndex(r, c)][num] = true;

                // Recursively try to solve the rest of the grid
                if (solve(r, c + 1, grid, row, col, gridTracker)) {
                    return true;
                }

                // Backtrack: Remove the number
                grid[r][c] = 0;
                row[r][num] = false;
                col[c][num] = false;
                gridTracker[getGridIndex(r, c)][num] = false;
            }
        }

        return false; // No valid number could be placed
    }


}
