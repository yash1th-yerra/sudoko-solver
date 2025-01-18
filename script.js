const table = document.getElementById('sudoku-table');
const output = document.getElementById('output');

// Generate the Sudoku grid
for (let i = 0; i < 9; i++) {
  const row = document.createElement('tr');
  for (let j = 0; j < 9; j++) {
    const cell = document.createElement('td');
    const input = document.createElement('input');
    input.type = 'number';
    input.min = '1';
    input.max = '9';
    input.placeholder = ' ';
    cell.appendChild(input);
    row.appendChild(cell);
  }
  table.appendChild(row);
}

// Collect Sudoku grid data
function getSudokuGrid() {
  const grid = [];
  const rows = table.getElementsByTagName('tr');
  for (let i = 0; i < rows.length; i++) {
    const cells = rows[i].getElementsByTagName('td');
    const row = [];
    for (let j = 0; j < cells.length; j++) {
      const value = cells[j].querySelector('input').value;
      row.push(value === '' ? 0 : parseInt(value));
    }
    grid.push(row);
  }
  return grid;
}

// Clear the Sudoku grid
document.getElementById('clear-btn').addEventListener('click', () => {
  const inputs = table.querySelectorAll('input');
  inputs.forEach(input => (input.value = ''));
  output.textContent = '';
});

// Solve the Sudoku (Send data to the backend)
document.getElementById('solve-btn').addEventListener('click', async () => {
  const grid = getSudokuGrid();
  try {
    const response = await fetch('http://localhost:8080/solve-sudoku', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ grid }),
    });

    if (response.ok) {
      const { solvedGrid } = await response.json();
      fillSudokuGrid(solvedGrid);
      output.textContent = 'Solved Successfully!';
    } else {
      output.textContent = 'Error solving Sudoku. Please check the input.';
    }
  } catch (error) {
    console.error('Error:', error);
    output.textContent = 'Error connecting to the server.';
  }
});

// Fill the Sudoku grid with the solution
function fillSudokuGrid(grid) {
  const rows = table.getElementsByTagName('tr');
  for (let i = 0; i < 9; i++) {
    const cells = rows[i].getElementsByTagName('td');
    for (let j = 0; j < 9; j++) {
      cells[j].querySelector('input').value = grid[i][j];
    }
  }
}
