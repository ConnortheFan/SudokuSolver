import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Sudoku class, which will read .txt files and convert
 * them into java Arrays to modify the Sudoku board.
 * Will use a Cell class for each cell
 */
public class Sudoku {
    // Instance variables
    Cell[][] sudoku;
    ArrayList<Cell> solvedCells;

    /**
     * No-args constructor for Sudoku. 
     * Creates blank board with no given digits.
     */
    public Sudoku() {
        sudoku = new Cell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudoku[i][j] = new Cell(i, j);
            }
        }
        solvedCells = new ArrayList<>();
    }

    /**
     * Constructor for Sudoku with file in correct format.
     * 
     * @param sudokuFile file name with sudoku in correct format 
     * @throws FileNotFoundException
     */
    public Sudoku(String sudokuFile) throws FileNotFoundException {
        File file = new File(sudokuFile);
        Scanner input = new Scanner(file);
        
        sudoku = new Cell[9][9];
        solvedCells = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            String[] digits = input.next().split(" ");
            for (int j = 0; j < 9; j++) {
                int digit = Integer.parseInt(digits[j]);
                sudoku[i][j] = new Cell(digit, i, j);
                if (sudoku[i][j].solved()) {
                    solvedCells.add(sudoku[i][j]);
                }
            }
        }
                
        input.close();
    }

    /**
     * Check if the Sudoku is valid based on the digits already placed.
     * Iterate through all digits, get all associated digits with cells that 
     * are solved. Check if any are the same digit.
     * 
     * @return if Sudoku is valid
     */
    public boolean checkValid() {
        for (Cell cell : solvedCells) {
            ArrayList<Cell> relatedCells = getRelatedCells(cell.getRow(), cell.getColumn());
            for (Cell relatedCell : relatedCells) {
                if (relatedCell.solved()) {
                    if (relatedCell.getDigit() == cell.getDigit()) {
                        return false;
                    }
                }
            }
        }
        
        return true;
    }

    /**
     * Sets a digit at the cell with coordinates x and y.    
     *  
     * @param digit digit 1-9 to set
     * @param x row of cell
     * @param y column of cell
     * @return if setting was successful
     */
    public boolean setDigit(int digit, int x, int y) {
        Cell cell = getCell(x, y);
        boolean successful = cell.set(digit);
        if (successful) {
            solvedCells.add(cell);
        }
        return successful;
    }

    /**
     * Propogates the result of a cell to all unsolved cells.
     * 
     * @param x row of cell
     * @param y column of cell
     * @return if any changes were made
     */
    public boolean propogate(int x, int y) {
        Cell cell = getCell(x, y);
        int digit = cell.getDigit();
        ArrayList<Cell> relatedCells = getRelatedCells(x, y);
        boolean changed = false;

        for (Cell relatedCell : relatedCells) {
            if (relatedCell.remove(digit)) {
                changed = true;
            }
        }

        return changed;
    }

    // Helper methods
    /**
     * Get related cells of cell at coordinates.
     * Includes rows, columns, and box.
     * No overlapping cells are returned.
     * 
     * @param x row of cell
     * @param y column of cell
     * @return ArrayList of all related cells 
     */
    public ArrayList<Cell> getRelatedCells(int x, int y) {
        ArrayList<Cell> relatedCells = new ArrayList<>();

        // Get rows, columns, and box cells
        ArrayList<Cell> rowCells = getRow(x, y);
        ArrayList<Cell> columnCells = getColumn(x, y);
        ArrayList<Cell> boxCells = getBox(x, y);

        relatedCells.addAll(rowCells);
        relatedCells.addAll(columnCells);
        for (Cell cell : boxCells) {
            if (relatedCells.contains(cell)) {
                continue;
            } else {
                relatedCells.add(cell);
            }
        }

        return relatedCells;
    }

    /**
     * Get Cells of box of cell at coordinates.
     * Given cell not included.
     * 
     * @param x row of cell
     * @param y column of cell
     * @return ArrayList of box cells
     */
    public ArrayList<Cell> getBox(int x, int y) {
        ArrayList<Cell> relatedCells = new ArrayList<>();
        int boxRow = x / 3;
        int boxColumn = y / 3;
        for (int i = 3 * boxRow; i < 3 * (1+boxRow); i++) {
            for (int j = 3 * boxColumn; j < 3 * (1+boxColumn); j++) {
                if (i == x && j == y) {
                    continue;
                }
                
                relatedCells.add(getCell(i, j));

            }
        }

        return relatedCells;
    }

    /**
     * Get Cells of row of cell at coordinates.
     * Given cell not included.
     * 
     * @param x row of cell
     * @param y column of cell
     * @return ArrayList of row cells
     */
    public ArrayList<Cell> getRow(int x, int y) {
        ArrayList<Cell> relatedCells = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (i == y) {
                continue;
            }
            relatedCells.add(getCell(x, i));
        }
        return relatedCells;
    }
    
    /**
     * Get Cells of column of cell at coordinates.
     * Given cell not included.
     * 
     * @param x row of cell
     * @param y column of cell
     * @return ArrayList of column cells
     */
    public ArrayList<Cell> getColumn(int x, int y) {
        ArrayList<Cell> relatedCells = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (i == x) {
                continue;
            }
            relatedCells.add(getCell(i, y));
        }
        return relatedCells;
    }

    /**
     * Return a cell given it's coordinates
     * 
     * @param x row of cell
     * @param y colum of cell
     * @return Cell object
     */
    public Cell getCell(int x, int y) {
        return sudoku[x][y];
    }

}

/**
 * The Cell class, which will be used for each cell
 * in a Sudoku
 */
class Cell {
    // Instance variables
    boolean solved;
    ArrayList<Integer> possibleDigits;
    int digit;
    int row;
    int column;

    /**
     * No digit constructor for Cell.
     * Sets solved to false and gives all possible digits.
     * 
     * @param row row of cell
     * @param column column of cell
     */
    public Cell(int row, int column) {
        solved = false;
        possibleDigits = new ArrayList<Integer>();
        for (int i = 1; i <= 9; i++) {
            possibleDigits.add(i);
        }
        this.row = row;
        this.column = column;
    }

    /**
     * Constructor for a cell. If digit is 0, means there
     * is no digit, so set possibleDigits to 1-9. If digit
     * is non-zero, then set that digit to there. 
     * Assign solved accordingly.
     * 
     * @param digit digit read for Sudoku
     * @param row row of cell
     * @param column column of cell
     */
    public Cell(int digit, int row, int column) {
        if (digit == 0) {
            solved = false;
            possibleDigits = new ArrayList<Integer>();
            for (int i = 1; i <= 9; i++) {
                possibleDigits.add(i);
            }
        } else {
            solved = true;
            this.digit = digit;
        }
        this.row = row;
        this.column = column;
    }

    /**
     * Set the value of a cell to a certain digit.
     * Make solved to true.
     * 
     * @param digit solved digit
     */
    protected boolean set(int digit) {
        if (solved) {
            System.out.println("Already solved cell. Error");
            return false;
        }        

        solved = true; 
        this.digit = digit;
        return true;
    }

    /**
     * Check if the cell is solved.
     * 
     * @return if the cell is solved
     */
    protected boolean solved() {
        return solved;
    }

    /**
     * Return the possible digits of cell.
     * 
     * @return possibleDigits
     */
    protected ArrayList<Integer> getPossibleDigits() {
        return possibleDigits;
    }

    /**
     * Return the digit of a solved cell.
     * 
     * @return digit
     */
    protected int getDigit() {
        return digit;
    }

    /**
     * Return the row of a cell
     * 
     * @return row
     */
    protected int getRow() {
        return row;
    }

    /**
     * Return the column of a cell
     * 
     * @return column
     */
    protected int getColumn() {
        return column;
    }

    /**
     * Remove a digit from possibleDigits.
     * 
     * @param digit digit to remove
     * @return if removal was successful
     */
    protected boolean remove(int digit) {
        if(solved) {
            return false;
        }
        if (possibleDigits.contains(digit)) {
            int index = possibleDigits.indexOf(digit);
            possibleDigits.remove(index);
            return true;
        }

        return false;
    }
}