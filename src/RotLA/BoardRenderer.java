package RotLA;

import RotLA.Adventurers.Adventurer;
import RotLA.Creatures.Blinker;
import RotLA.Creatures.Creature;
import RotLA.Creatures.Orbiter;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static RotLA.GameUtility.*;

// BoardRenderer class represents the Board of the RotLA game. BoardRenderer holds rooms, adventurers, creatures
// and also a RoomFinder functional interface that acts as a messenger to other components which need to find rooms
public class BoardRenderer {

    //CONCEPT: ENCAPSULATION - All variables are encapsulated with private modifier.
    private final List<Room> boardRoomList;
    private List<Adventurer> adventurers;
    private List<Creature> creatures;
    private RoomFinder roomFinder;

    private int turnCounter = 0;

    public BoardRenderer() {
        roomFinder = this::findRoom;
        //initializing rooms in each of the coordinates
        boardRoomList = new ArrayList<>();
        // add starter room
        Room starterRoom = new Room(0, 1, 1);
        boardRoomList.add(starterRoom);
        for (int level = TOPMOST_ROOM; level <= BOTTOM_MOST_ROOM; level++) {
            for (int row = WESTMOST_ROOM; row <= EASTMOST_ROOM; row++) {
                for (int column = NORTHMOST_ROOM; column <= SOUTHMOST_ROOM; column++) {
                    boardRoomList.add(new Room(level, row, column));
                }
            }
        }
        //setting connected rooms
        boardRoomList.forEach(this::getNeighbours);
    }


    //-----------------------Publicly exposed methods--------------------------------
    // This method adds adventurers and creatures to board and sets their initial spawn positions
    public void initialiseBoardForGame(List<Adventurer> adventurers, List<Creature> creatures) {
        this.adventurers = adventurers;
        this.creatures = creatures;
        //CONCEPT: POLYMORPHISM - Each type of Adventurer or Creature can be its type as well as its parent class type
        //i.e An Orbiter instance is an instance of both Orbiter and Creature
        //initializing spawn positions for adventurers, always start at 0-1-1
        Room starterRoom = boardRoomList.get(0);
        adventurers.forEach(adventurer -> {
            adventurer.setRoom(starterRoom);
            starterRoom.addAdventurer(adventurer);
        });

        //initializing spawn positions for creatures
        creatures.forEach(creature -> {
            // coordinates of room where creature is to be added, updating along the way
            int level, verticalDir, horizontalDir;
            if (creature instanceof Orbiter) {
                level = GameUtility.getRandomInRange(TOPMOST_ROOM, BOTTOM_MOST_ROOM);
                // Orbiters start randomly in the outer rooms of any level
                ArrayList<Pair<Integer, Integer>> possibleStaringRooms = new ArrayList<>();
                //Find all possible outer rooms where orbiters can spawn
                for (int i = NORTHMOST_ROOM; i < SOUTHMOST_ROOM; i++) {
                    for (int j = WESTMOST_ROOM; j < EASTMOST_ROOM; j++) {
                        if (!(i == 1 && j == 1))
                            possibleStaringRooms.add(new Pair<>(i, j));
                    }
                }
                // Choosing a random room out of the possible rooms
                int value = GameUtility.getRandomInRange(0, possibleStaringRooms.size() - 1);
                Pair<Integer, Integer> p = possibleStaringRooms.get(value);
                verticalDir = p.getValue0();
                horizontalDir = p.getValue1();
            } else if (creature instanceof Blinker) {
                //blinkers start in a random room on 4th level
                level = BOTTOM_MOST_ROOM;
                verticalDir = GameUtility.getRandomInRange(NORTHMOST_ROOM, SOUTHMOST_ROOM);
                horizontalDir = GameUtility.getRandomInRange(WESTMOST_ROOM, EASTMOST_ROOM);
            } else {
                //Seeker start randomly in any of the levels
                level = GameUtility.getRandomInRange(TOPMOST_ROOM, BOTTOM_MOST_ROOM);
                verticalDir = GameUtility.getRandomInRange(NORTHMOST_ROOM, SOUTHMOST_ROOM);
                horizontalDir = GameUtility.getRandomInRange(WESTMOST_ROOM, EASTMOST_ROOM);
            }
            Room room = findRoom(new Triplet<>(level, verticalDir, horizontalDir));
            room.addCreature(creature);
            creature.setRoom(room);
            creature.setRoomFinder(roomFinder);
        });
    }

    // Prints the status of the game after each turn
    public void printGameStatus() {
        //print game turn
        System.out.println("Turn : " + turnCounter++);
        //print all room status of the board, starter room followed by all rooms
        System.out.println("Board Status : ");
        System.out.println(findRoom(new Triplet<>(0, 1, 1)).getRoomDetails());
        for (int level = TOPMOST_ROOM; level <= BOTTOM_MOST_ROOM; level++) {
            for (int row = WESTMOST_ROOM; row <= EASTMOST_ROOM; row++) {
                for (int column = NORTHMOST_ROOM; column <= SOUTHMOST_ROOM; column++) {
                    System.out.print(findRoom(new Triplet<>(level, row, column)).getRoomDetails() + '\t');
                }
                System.out.println();
            }
        }
        // Printing status of all adventurers
        System.out.println("Adventurers Status : ");
        for (Adventurer adventurer : adventurers) {
            System.out.println(adventurer.getAdventurerStatus());
        }
        // Printing status of all creatures, creating a hashmap to track no of instances
        System.out.println("Creatures Status : ");
        HashMap<String, Integer> creatureStatus = new HashMap<>();
        creatures.forEach(creature -> {
            creatureStatus.putIfAbsent(creature.getName(), 0);
            if (creature.isAlive()) {
                creatureStatus.computeIfPresent(creature.getName(), (key, value) -> value + 1);
            }
        });
        creatureStatus.forEach((key, value) -> System.out.println(key + " - " + String.valueOf(value) + " Remaining"));
        System.out.println();
    }

    // ---------------------------------Private methods------------------------------------------

    // Finds and sets the neighbors for a particular room
    private void getNeighbours(Room room) {
        ArrayList<Room> neighbors = new ArrayList<>();
        // for a room listing all possible coordinates of its neighbours
        Triplet<Integer, Integer, Integer> roomCoordinates = room.getRoomCoordinates();
        int roomWest = Integer.max(WESTMOST_ROOM, roomCoordinates.getValue2() - 1);
        int roomEast = Integer.min(EASTMOST_ROOM, roomCoordinates.getValue2() + 1);
        int roomNorth = Integer.max(NORTHMOST_ROOM, roomCoordinates.getValue1() - 1);
        int roomSouth = Integer.min(SOUTHMOST_ROOM, roomCoordinates.getValue1() + 1);
        int roomLevel = roomCoordinates.getValue0();
        // adding the north south neighbours with level and horizontal coordinates as constant
        for (int row = roomNorth; row <= roomSouth; row++) {
            if (row != roomCoordinates.getValue1()) {
                Room neighborRoom = findRoom(new Triplet<>(roomLevel, row, roomCoordinates.getValue2()));
                if (neighborRoom != null)
                    neighbors.add(neighborRoom);
            }
        }
        // adding east-west neighbours keeping level and vertical coordinates constant
        for (int column = roomWest; column <= roomEast; column++) {
            if (column != roomCoordinates.getValue2()) {
                Room neighborRoom = findRoom(new Triplet<>(roomLevel, roomCoordinates.getValue1(), column));
                if (neighborRoom != null)
                    neighbors.add(neighborRoom);
            }
        }
        // if a room is central room that horizontal and vertical coordinates 1, add top-down constants
        if (roomCoordinates.getValue2() == 1 && roomCoordinates.getValue1() == 1) {
            int roomAbove = Integer.max(TOPMOST_ROOM, roomLevel - 1);
            int roomBelow = Integer.min(BOTTOM_MOST_ROOM, roomLevel + 1);
            for (int level = roomAbove; level <= roomBelow; level++) {
                if (level != roomLevel) {
                    Room neighborRoom = findRoom(new Triplet<>(level, roomCoordinates.getValue1(), roomCoordinates.getValue2()));
                    if (neighborRoom != null)
                        neighbors.add(neighborRoom);
                }
            }
        }
        room.setConnectedRooms(neighbors);
    }

    //returns a room given its coordinates
    private Room findRoom(Triplet<Integer, Integer, Integer> roomID) {
        return boardRoomList.stream()
                .filter(room -> room.getRoomCoordinates().compareTo(roomID) == 0)
                .findFirst()
                .orElse(null);
    }
}
