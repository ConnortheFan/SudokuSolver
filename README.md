# SudokuSolver

It's 4:55am on 2/22/23 as of starting this and I'm feeling inspired to at least write up my plan to create a Sudoku Solver as a personal project.

If you didn't know about me, I'm really into Sudoku, like **really** into it. Almost unhealthily. So, if I'm going to have to make personal projects in the future for my career, why not start with a Sudoku Solver! If done correctly, it should have the same capability as me in solving classic Sudoku and I can simply test it on myself. All methods that I create for this will be from my own knowledge, without referencing other Sudoku solvers or guides. Since however, I did not create a lot of these methods and just learned them from others, I'd like to thank Cracking the Cryptic (Youtube) for entertaining me with their Sudoku solves and LogicWiz (app) for giving me a place to play Sudoku.

This repository should contain my work for this Sudoku Solver, which will be written in java since that's what I'm learning now in class.

## My Plan

Before I start coding anything, I want to write down my plan for this code. I want to see what I think I can do, then when I actually code it, I can see where I went wrong.

Right now, I plan to have 2 classes, one called `Sudoku` and another called `SudokuSolver`. `Sudoku` should just have the board and be able to take input in the form of .txt files and convert them into Arrays. Meanwhile, `SudokuSolver` will have the methods to actually solve the Sudoku. For all intents and purposes, everything will be `public` since I'm not really sure how having anything `private` or `protected` will influence anything meaningful.

## class Sudoku

### Constructor

Firstly, it should have a constructor. For now, I only plan to have one constructor that takes a .txt file as the input and converts it into java Arrays, but possibly in the future I can code it to take a string of digits as well. 

The .txt file for the input should have the format:
```
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
```
Where the `0`'s are the spots for digits. For any blank spaces, they should be filled with a `0`. The constructor should then use a java Scanner to take each line, then use the .split(" ") method to turn each line into an Array with size 9. All 9 Arrays will then be placed into 1 Array, which will be the instance variable for Sudoku. 

### boolean checkValid()

Within the `Sudoku` class should also be the method to check if the board is valid. Following classic Sudoku rules, each row, column, and 3x3 box should contain the digits 1-9 and digits should not repeat.

Checking the rows and columns should be easy simply using Array indexing. A boolean Array of size 9, where each index represents the digits 1-9, will be initialized with all `false`. Going through the cells, for a valid digit, check if the boolean corresponding to that digit is true. If it is, the Sudoku is invalid and there should be some exception or message thrown. If not, then turn that boolean to `true` and move on to the next cell. Once all cells are checked and there is no error, move onto the next row/column. 

Once all rows and columns are checked, time to check the boxes. This should actually be pretty simple using a nested for loop, like:
```
for (int i = 0; i < 9; i += 3) {
  for (int j = 0; j < 9; j += 3 {
    ...
  }
}
```
This should check the boxes by having `[i][j]` represent the cell at the top left of the box. Then there can be another set of nested for loops to go through each cell in the box and use the boolean Array to check if its valid. 

Once all rows, columns, and boxes are checked, this method should return true. For the most part, this will be called at the start, when initializing the Sudoku, and after every move.

### Setters and Getters

Following coding etiquitte that I've learned, you should never directly access the values of an object. Instead, use setters and getters. These are mostly one like functions that just retreive or set the value of a cell, given its index coordinates.

## class SudokuSolver
