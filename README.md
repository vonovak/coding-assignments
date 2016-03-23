## This repo contains solutions to some problem assignments I encountered in my university classes on algorithms.
(For now, there's only one problem.) In each folder, there is a file that contains the problem assignment. The solution to the problem is in the src subfolder. The code is always in a single java file, often with only a couple of methods and classes, since the problems are focused solely on developing effective algorithms and using suitable data structures, and never require lengthy code. 

#### Example assignment
##### Problem: Train Composition

The Institute of Railway Transport has assembled a number of cargo wagon prototypes in its depot a now wants to connect them to form a train pulled by one locomotive to perform a test drive. Due to various technical constraints it is not possible to connect the wagons in arbitrary order and consequently we may not even be allowed to compose a train from all the wagons. Wagon *v* is said to be the predecessor of wagon *w* if wagon *w* can be put immediately behind wagon *v*. If a wagon can be placed directly behind the locomotive, then the locomotive counts as a possible predecessor of the wagon. For every wagon, we are given a list of its predecessors. Individual wagons have different weight and our main priority is that the weight of the train (excluding the locomotive) is the maximum possible. 

The task is to compose a train of the maximum weight within given constraints.

##### Input

For the sake of simplicity, the wagons are identified by integers 1 to *N*, the locomotive has an id of 0.
The first line of input contains a positive number *N* representing the number of wagons in the depot. Following are *N* lines, where each line specifies an individual wagon. Lines (wagons) can be given in arbitrary order. The specification of a wagon contains the wagon id followed by an integer that represents its weight. Next comes an unsorted list of ids of all wagon predecessors. All of this information is separated by a space character.

##### Output

Output consists of two lines. The first line contains the wagon ids of the found train ordered from the beginning to the end, including the locomotive (the line starts with 0). The ids are separated by a space character. The second line gives the weight of the found train. If two or more trains have the same weight, the output only contains the train whose identification is the lowest when sorted lexicographically according to the wagon numbers. For example, from the trains `0 3 6`, `0 3 1 4 2`, `0 3 1 5` the train `0 3 1 4 2` would be picked (see example 4 below).

Example 1

input:
```
5
1 50 0 4
2 30 1 5
3 100 4 0
4 40 0 3
5 120 4
```
output:
```
0 3 4 5 2 
290
```
