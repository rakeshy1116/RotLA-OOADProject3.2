# RotLA-OOADProject2.2

## Team Members

- Rakshith Jayanna
- Rakesh Chowdary Yarlagadda

### Java version : 18

### Jdk : Oracle OpenJDK-18.02

### IDE used : IntelliJ

## Assumptions

- We are using our code from Project 2.2 to add more patterns in this project - https://github.com/rakeshy1116/RotLA-OOADProject2.2
- Portal item: On every move, Adventurers who possess it have a 25% chance to use it
- To add more details to the creatures while printing the status, we have added Creature's identity hash with the name
  in status. This will help track the correct creature instances.
- Trap: We do not consider trap as a treasure for adventurers to hold and do not count traps for termination conditions
- Celebration: Adventurers celebrate their victories during their turn and not in the creature turn
- Potion: 1 Potion instance is only used once per adventurer per game as soon as it is found

## Changes in UML file

- Changed strategy classes(Combat and Search) to abstract classes
- Added additional method collectTreasure to Search Strategy
- Not all combat strategies override fight method, they override combat modifier value
- Added celebrate method to abstract Celebrate decorator class
- Changed Observers to Java Flows Subsribers and Gameobjects to possess publisher instance to push events
- Added Event interface and event data classes
- Updated Tracker to hold structure to print game status

## How to run

- GameEngine class in RotLA package has the main function to run the simulation
- For single run, directly run the main function
- For multiple run, uncomment lines in main as directed and run main

## Unit test

- Junit5 unit test cases can be found and run in test package
- We have implemented 10 test cases and a screenshot of the test run can be found as RotLA-testcases.png

## Dependencies

- Junit 5 library for unit tests, https://junit.org/junit5/
- Java Tuples for tuples, https://www.javatuples.org/
- Implemented Observer pattern using Java Flow, https://docs.oracle.com/javase/9/docs/api/java/util/concurrent/Flow.html