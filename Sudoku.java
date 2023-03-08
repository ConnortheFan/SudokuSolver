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

    /**
     * No-args constructor for Sudoku. 
     * Creates blank board with no given digits.
     */
    public Sudoku() {
        sudoku = new Cell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudoku[i][j] = new Cell(0);
            }
        }
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
        for (int i = 0; i < 9; i++) {
            String[] digits = input.next().split(" ");
            for (int j = 0; j < 9; j++) {
                int digit = Integer.parseInt(digits[j]);
                sudoku[i][j] = new Cell(digit);
            }
        }
                
        input.close();
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

    /**
     * No-args constructor for Cell.
     * Sets solved to false and gives all possible digits.
     */
    public Cell() {
        solved = false;
        possibleDigits = new ArrayList<Integer>();
        for (int i = 1; i <= 9; i++) {
            possibleDigits.add(i);
        }
    }

    /**
     * Constructor for a cell. If digit is 0, means there
     * is no digit, so set possibleDigits to 1-9. If digit
     * is non-zero, then set that digit to there. 
     * Assign solved accordingly.
     * 
     * @param digit digit read for Sudoku
     */
    public Cell(int digit) {
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
    }

    /**
     * Set the value of a cell to a certain digit.
     * Make solved to true.
     * 
     * @param digit solved digit
     */
    protected void set(int digit) {
        if (solved) {
            System.out.println("Already solved cell. Error");
            return;
        }        

        solved = true; 
        this.digit = digit;
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