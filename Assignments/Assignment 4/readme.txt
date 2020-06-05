README

MazeVisualizer.java is unchanged from the original file.

Pair.java is unchanged from the original file.

PairComparator.java is a comparator class to instruct a priority queue on how to order 
Pair objects: x is the vertex number, y is the current shortest distance, and the 
priority queue will prioritize based on y, followed by x.

HW4.java is the driver program for the assignment that makes use of the other three files.

To run this from the command prompt, I had the 4 provided text files placed in the /bin
folder for the project. In the command prompt, I navigated to that folder and then ran
the program with the following 4 lines:

java HW4 --unweighted RandomMaze.txt RandomQuery.txt
java HW4 --weighted RandomMaze.txt RandomQuery.txt
java HW4 --unweighted HilbertMaze.txt HilbertQuery.txt
java HW4 --weighted HilbertMaze.txt HilbertQuery.txt

