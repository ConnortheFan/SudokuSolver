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
  for (int j = 0; j < 9; j += 3) {
    ...
  }
}
```
This should check the boxes by having `[i][j]` represent the cell at the top left of the box. Then there can be another set of nested for loops to go through each cell in the box and use the boolean Array to check if its valid. 

Once all rows, columns, and boxes are checked, this method should return true. For the most part, this will be called at the start, when initializing the Sudoku, and after every move.

### Setters and Getters

Following coding etiquitte that I've learned, you should never directly access the values of an object. Instead, use setters and getters. These are mostly one like functions that just retreive or set the value of a cell, given its index coordinates.

## class SudokuSolver

Now time for the meaty part of the code. From how I solve classic Sudoku, I usually go through a couple methods in order. They are:

1. Naked Singles
2. Box x Row/Column exclusivity
3. Pairs
4. Triples/etc
5. X-Wing/Swordfish

Apart from these, I also know of XY-chains, but I'm not at all proficient with them. Each of these methods will be of type boolean and will return true if there is some change made. If no change was made using a method, move onto the next method, and so forth. Once a change is made, go back to method #1 Naked Singles and run through all methods again. Once the board is filled or all methods have been exhausted with no change, the program should stop and return whatever results it has in a new .txt file, likely called `results.txt` or `{Sudoku File name} + _results.txt`.

### Create Possible Digits

Sudoku technically collapses once digits are filled in. Every cell has the possibility of being any digit, but once the cell is filled in, the possible digits of surrounding cells decreases and the entire Sudoku collapses towards one final result. In this way, we can solve our Sudoku by filling in all blank spaces with their possible digits. Then, by turning those possible digits into real digits using our methods, we collapse the other possibilities making this Sudoku closer to completion. 

Right now, there's two possible ways I could implement possible digits. 

1. For every space with a 0, I could replace it with an Integer Array. Then, using a helper method called findRelatedCells(), I can get all cells that would affect this blank cell, take the real digits, and either add digits to the possible digits that aren't included, or remove digits from an initialized Array with 1-9 that already exist.
  - Pros: When I want to collapse a cell into a real number, I can just check if the possibilities size is 1.
  - Cons: Since I'm using java Arrays, I'd need to change their size or reorder the numbers
2. For every space with a 0, replace it with a boolean Array. This Array should be size 9 with each boolean corresponding to a digit 1-9. The boolean is true if that number is possible and false if it isn't. 
  - Pros: 
    - I won't need to adjust the Arrays after first creating them
    - I can change possible values easily by setting them to false, without needing to check their initial value
  - Cons: Checking if there's just one possible value left means going through all 9 values in the Array

Judging by these pros and cons, I will likely use method 2, because I'm not too worried about runtime since this program should have runtime O(1) given that a Sudoku has constant size.

### Object[] findRelatedCells()

This helper method should return a list of cells that are related to a given cell. Crudely, it should just take the cell coordinates and take each cell in the row, column, and box, and throw them into one list. The same cell can be listed twice since each cell will be checked through later. However, I could also clean this up and write code to avoid duplicate cells, like taking the coordinates of the box cells and only adding them if their x and y are different from the given cell.

Possible variations of this helper method could exist where a boolean could be given as a parameter for whether only real cells are given or blank cells.

### boolean NakedSingles()

Naked Singles should be the most basic Sudoku solving method possible. This will have 2 iterations.

The first iteration will be through all blank cells. If the cell only has 1 possible digit, then replace that cell with the real digit and extrapolate the effects to other blank cells. This will likely be written using the findRelatedCells() helper method to take blank cells and remove the real digit from their possibilities. After going through all cells, start the second iteration.

The second iteration will be going through rows, columns, and boxes for each digit 1-9. If in that RCB for a digit there is one cell that has the possibility, then that cell has to be it and will turn real and extrapolate.

NakedSingles() should be the only method that actually places digits. All other more advanced methods only remove possibilities from other cells, until they only have 1 option left.

From how I'm describing this, I will likely add more to the `Sudoku` class that includes the digits that already exist for each RCB, so I won't need to check them. 

### boolean RCBExclusive()

For this method, each row and column will be checked for each digit 1-9. The cells that have that possible digit are then marked. If those cells are all in the same box, then the rest of that box cannot have that number as a possible digit.

Example:
```
1 2 3 4 5 6 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
```
Using this portion of a Sudoku, it can be seen that in box 3, all cells have the possibility of all numbers given how we've assigned them aside from the top row where those cells only have the possibilities of 7, 8 and 9. When we check that row for 7, 8, and 9, we can see that because those possible cells are all in box 3 for each digit, the rest of box 3 (rows 2 and 3) cannot have 7, 8, or 9 as their possible digits, thus they will be removed from the possible digits. 

This can be done using a simple iterator for the rows and columns separately, then calculating which box each cell is in based on its index and continuing from there.

### boolean Pairs()

Pairs apply when 2 cells have the possibilites of only the same 2 numbers. These cells have to be related and because they have the same 2 possible digits, all other cells in their RCB cannot be those 2 digits. This is actually the same concept as the Triples and so on, where there are *n* cells that have the same *n* possible digits, therefore other related cells cannot have those digits. 

Coding the Pairs and Triples should be simple, but for *n* cells, this feels much more difficult. I could simply brute force this to go through every combination of blank cells to see when *n* blank cells have only *n* possible digits. For now, I'd code it like this and change it in the future as I see fit. 

From how I'm thinking, there are 2 variations of this.

Firstly, when there are 2 cells, each with only the 2 possible digits. This should be easy to spot and check.

For example:
```
[1-9] [1-9] [2,4] [1-9] [1-9] [1-9] [2, 4] [1-9] [1-9]
```
Here, this row has blank cells that can be 1-9, but cells 3 and 7 only have the possibilities of 2 and 4. Therefore, these 2 cells are a pair and the rest of the blank cells on this row can no longer be a 2 or a 4.

Secondly, when in a RCB, there are the same 2 cells have have the possibility of having 2 digits. 

For example:
```
[1-2, 5-9] [1-2, 5-9] [1-9] [1-2, 5-9] [1-2, 5-9] [1-2, 5-9] [1-9] [1-2, 5-9] [1-2, 5-9]  
```
Here, in this example, all cells have the possibilities of the digits 1-2 and 5-9, but only cells 3 and 7 have the possibilites of 3 and 4. Therefore, these 2 cells are a pair and their only options should be 3 and 4. However, thinking about this in hindsight, it's actually just a set of 7 of the cells 1-2, 4-6, and 8-9 with 7 possibilites instead. Therefore, this second variation can be ignored and the code for *n* cells just needs to be coded. Perhaps, the method should be renamed Sets(), for sets of all sizes.

### boolean Swordfish()

The X-Wing is a simplified Swordfish, like how Pairs is a simplified Sets, so the code for Swordfish should be enough. A Swordfish is when you consider all possible cells for a certain digit. You then see if those possible cells in a row match up with the possible cells in a different row via their column. If you have *n* columns of possible cells in *n* rows, then you can eliminate the possibility of that digit in the rest of those columns. This also applies vice versa, with columns and rows instead. The X-Wing is just the name if n=2.

Applying this should be simple. Iterate through this for each digit still missing. Then, we're going to run this up to as many digits are in 1 row/column, starting with 2. For each iteration of *n* digits, find the rows/columns that have *n* or less of our digit. Then match them up to see if any combination of *n* rows/columns gives us *n* columns/rows. If so, remove the possibilities of all other cells in the columns/rows. If not, move onto the next size of n+1. Once through, move onto the next digit until you go through all digits. 

### void SolveSudoku()

SolveSudoku() will be the main method that puts all other solving methods together. This will run with a while loop that checks a boolean called `solving`. Once all methods are run through, the while loop should stop and print the results into a new .txt file. There should be a helper method to see if the Sudoku is solved in the loop to terminate early, but I might not include one because it should terminate once all methods are finished anyways. Including one may just make the code take longer to run.

## Compiling and Running

Now that the java code itself is finished, I need to be able to run this code from the terminal. Using what I've learned from CSE 15L, I can write a bash script `SolveSudoku.sh` that compiles and runs the code using whatever Sudoku file I want. I may even be able to specify what the result file will be or use output redirection to have my methods print out the result instead and redirect it into a .txt file. 

And that's all I have planned for now. Now whether I actually code all of this, we'll have to see. It's 6:39am now and my surge of inspiration hopefully won't run out anytime soon. 

# Modification log

## 3/7/2023

It's been a while, but I'm back and have the time to work on this. 

### class Cell

I've made the Sudoku class and decided to have a Cell class as well, to represent each cell. I'll use these Cells to make it easier to implement the solving methods because each Cell has a `boolean solved` instance variable, which is simply if the Cell has a set digit yet or not. Based on this, I can make my methods more efficient since it can do one check for a cell before moving on if its already solved. 

Each cell also has `ArrayList<Integer> possibleDigits` and `int digit` instance variables, which are assigned respectively whether the cell is created with a given digit or not.

The Cell class also has a few protected helper methods like `remove()` and `set()` and whatnot to make my life easier in the future. `remove()` returns a boolean whether any changed were made, in anticipation of the future, where I have to check if any changes were made. So far, the helper methods are: 

* `void set(int digit)` 
* `boolean solved()`
* `ArrayList<Intger> getPossibleDigits()`
* `int getDigit()`
* `boolean remove(int digit)`

## class Sudoku

All I've made are the constructors for Sudoku. There's a no-args constructor, which just makes a blank board, which is mostly useless, but just in case I want to expand what this program is for (*hint hint wink wink*).

The `Sudoku(String sudokuFile)` constructor is more of the meat of this class. It takes a file, preferably a .txt file, with valid formatting as explained above, and uses a `Scanner` to read through the file. It uses the `next()` method to take whole lines, then the `split(" ")` method to break up the lines into its digits. These digits are then parsed to an int, and used to construct new Cell objects.

The Sudoku uses a 2D Cell array as it's backend data structure, since the size shouldn't change at all and it makes it super easy to call certain cells based on their coordinates (CSE 12 Data Structures taught me this). 

For the rest of the Sudoku class, I plan to create some more basic methods like `set()` or `note()` or `checkValid()` since these are mostly basic Sudoku methods and not exclusive to the SudokuSolver. I'm also planning to have the SudokuSolver extend Sudoku, so these basic methods can be called there too. This way, if I were to decide to expand this into a webApp, where you can play Sudoku, the basic Sudoku class can be used instead of just SudokuSolver. 