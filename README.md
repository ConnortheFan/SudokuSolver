# SudokuSolver

It's 4:55am on 2/22/23 as of starting this and I'm feeling inspired to at least write up my plan to create a Sudoku Solver as a personal project.

If you didn't know about me, I'm really into Sudoku, like **really** into it. Almost unhealthily. So, if I'm going to have to make personal projects in the future for my career, why not start with a Sudoku Solver! If done correctly, it should have the same capability as me in solving classic Sudoku and I can simply test it on myself. All methods that I create for this will be from my own knowledge, without referencing other Sudoku solvers or guides. Since however, I did not create a lot of these methods and just learned them from others, I'd like to thank Cracking the Cryptic (Youtube) for entertaining me with their Sudoku solves and LogicWiz (app) for giving me a place to play Sudoku.

This repository should contain my work for this Sudoku Solver, which will be written in java since that's what I'm learning now in class.

## My Plan

Before I start coding anything, I want to write down my plan for this code. I want to see what I think I can do, then when I actually code it, I can see where I went wrong.

Right now, I plan to have 2 classes, one called `Sudoku` and another called `SudokuSolver`. `Sudoku` should just have the board and be able to take input in the form of .txt files and convert them into Arrays. Meanwhile, `SudokuSolver` will have the methods to actually solve the Sudoku. For all intents and purposes, everything will be `public` since I'm not really sure how having anything `private` or `protected` will influence anything meaningful.

## `class Sudoku`

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
Where the `0`'s are the spots for digits. 
