# RotLA-OOADProject2.2

## Team Members
- Rakshith Jayanna
- Rakesh Chowdary Yarlagadda

### Java version : 18
### Jdk : Oracle OpenJDK-18.02
### IDE used : IntelliJ

## Assumptions
- Sneaker special ability: We have considered that Sneakers ability to evade fights activates once in a turn. This means irrespective of number of creatures in a room, sneaker's ability decides whether he fights all creatures in the room
- Order of fights: Whenever a creature encounters multiple adventurers in a room or vice versa, the order in which they fight is the order of the arrival of adventurers or creatures
- Multiple termination condition scenario: If treasures found >=10, all adventurers are dead, one of creatures alive, we are considering Adventures win by finding treasures. 
- Orbiter movement: We consider each orbiter instance move in one particular direction clockwise/anticlockwise one room a turn

## Changes in UML file

- Adventurer class: added rollDiceFight and rollDiceTreasure methods that are overidden by subclasses
- Creature class: added delegate roomfinder interface to find rooms to move for creatures, added getter and setters with appropriate access modifiers
- BoardRenderer class: added roomfinder functional interface lambda, added initialise board method, added findroom method
- GameEngine class: No change in properties, updated relationship to composition since all instance existence depends on GameEngine
- GameUtility class : Added a utility class that hold static methods like get random values and constants 
- RoomFinder interface: Added a functional interface that acts as a messger or callback between 2 classes
- Room class: Added roomcoordinate triplet, added appropriate getter and setter methods



## How to run

- GameEngine class in RotLA package has the main function to run the simulation
