# Assignment 4

## Problems

![Cap 1](https://github.com/jasminecronin/data-structures-algorithms/blob/master/Assignments/Assignment%204/assignment4-1.png)

![Cap 2](https://github.com/jasminecronin/data-structures-algorithms/blob/master/Assignments/Assignment%204/assignment4-2.png)

![Cap 3](https://github.com/jasminecronin/data-structures-algorithms/blob/master/Assignments/Assignment%204/assignment4-3.png)

![Cap 4](https://github.com/jasminecronin/data-structures-algorithms/blob/master/Assignments/Assignment%204/assignment4-4.png)

![Cap 5](https://github.com/jasminecronin/data-structures-algorithms/blob/master/Assignments/Assignment%204/assignment4-5.png)

## Solutions

![Cap 6](https://github.com/jasminecronin/data-structures-algorithms/blob/master/Assignments/Assignment%204/solution1.png)

![Cap 7](https://github.com/jasminecronin/data-structures-algorithms/blob/master/Assignments/Assignment%204/solution2.png)

![Cap 8](https://github.com/jasminecronin/data-structures-algorithms/blob/master/Assignments/Assignment%204/solution3.png)

![Cap 9](https://github.com/jasminecronin/data-structures-algorithms/blob/master/Assignments/Assignment%204/solution4.png)

![Cap 10](https://github.com/jasminecronin/data-structures-algorithms/blob/master/Assignments/Assignment%204/solution5.png)

### Code Solution

MazeVisualizer.java is unchanged from the original file.

Pair.java is unchanged from the original file.

PairComparator.java is a comparator class to instruct a priority queue on how to order 
Pair objects: x is the vertex number, y is the current shortest distance, and the 
priority queue will prioritize based on y, followed by x.

HW4.java is the driver program for the assignment that makes use of the other three files.

To run this from the command prompt, I had the 4 provided text files placed in the /bin
folder for the project. In the command prompt, I navigated to that folder and then ran
the program with the following 4 lines:

> java HW4 --unweighted RandomMaze.txt RandomQuery.txt

> java HW4 --weighted RandomMaze.txt RandomQuery.txt

> java HW4 --unweighted HilbertMaze.txt HilbertQuery.txt

> java HW4 --weighted HilbertMaze.txt HilbertQuery.txt

