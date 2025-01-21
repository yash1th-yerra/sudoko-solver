import React, { useState } from "react";
import "./SudokuSolver.css";

const SudokuSolver = () => {
  const [sudoku, setSudoku] = useState(
    Array.from({ length: 9 }, () => Array(9).fill(0))
  ); // 9x9 grid initialized with zeros
  const [output, setOutput] = useState("");

  const handleChange = (row, col, value) => {
    const newSudoku = [...sudoku];
    newSudoku[row] = [...newSudoku[row]]; // Ensure deep copy of the row

    if (value === "" || value === "0") {
      newSudoku[row][col] = 0; // Treat empty cells as 0
    } else if (!isNaN(value) && value >= 1 && value <= 9) {
      newSudoku[row][col] = parseInt(value); // Convert to number
    }

    setSudoku(newSudoku);
  };

  const clearGrid = () => {
    setSudoku(Array.from({ length: 9 }, () => Array(9).fill(0)));
    setOutput("");
  };

  const solveSudoku = async () => {
    setOutput("Solving...");
    try {
      const response = await fetch("http://localhost:8080/solve-sudoku", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ grid: sudoku }),
      });

      if (response.ok) {
        const { solvedGrid } = await response.json();
        setSudoku(solvedGrid);
        setOutput("Solved Successfully!");
      } else {
        const errorDetails = await response.text();
        setOutput(`Failed to solve Sudoku: ${errorDetails}`);
      }
    } catch (error) {
      console.error("Error:", error);
      setOutput("Error connecting to the server.");
    }
  };

  return (
    <div className="container">
      <h1>Sudoku Solver</h1>
      <div className="sudoku-grid">
        {sudoku.map((row, rowIndex) => (
          <div className="sudoku-row" key={rowIndex}>
            {row.map((cell, colIndex) => (
              <input
                key={`${rowIndex}-${colIndex}`}
                type="number"
                min="1"
                max="9"
                value={cell === 0 ? "" : cell}
                onChange={(e) =>
                  handleChange(rowIndex, colIndex, e.target.value)
                }
              />
            ))}
          </div>
        ))}
      </div>
      <div className="buttons">
        <button onClick={solveSudoku}>Solve</button>
        <button onClick={clearGrid}>Clear</button>
      </div>
      <div className="output">{output}</div>
    </div>
  );
};

export default SudokuSolver;
