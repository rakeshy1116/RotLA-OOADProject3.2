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

public class BoardRenderer {

    private final List<Room> boardRoomList;
    private List<Adventurer> adventurers;
    private List<Creature> creatures;
    private RoomFinder roomFinder;

    private int turnCounter = 1;

    static final int WESTMOST_ROOM = 0;
    static final int EASTMOST_ROOM = 2;
    static final int NORTHMOST_ROOM = 0;
    static final int SOUTHMOST_ROOM = 2;
    static final int TOPMOST_ROOM = 1;
    static final int BOTTOM_MOST_ROOM = 4;

    public BoardRenderer(List<Adventurer> adventurers, List<Creature> creatures) {
        roomFinder = this::findRoom;
        //initializing rooms
      boardRoomList = new ArrayList<>();
        for (int level = TOPMOST_ROOM; level <= BOTTOM_MOST_ROOM; level++) {
            for (int row = WESTMOST_ROOM; row <= EASTMOST_ROOM; row++) {
                for (int column = NORTHMOST_ROOM; column <= SOUTHMOST_ROOM; column++) {
                    boardRoomList.add(new Room(level, row, column));
                }
            }
        }
        //setting connected rooms
        boardRoomList.forEach(this::getNeighbours);

        this.adventurers=adventurers;
        this.creatures=creatures;
        //initializing spawn positions for adventurers
        Room starterRoom = new Room(0, 1, 1);
        starterRoom.setConnectedRooms(new ArrayList(List.of(findRoom(new Triplet<>(1, 1, 1)))));
        boardRoomList.add(0, starterRoom);

        adventurers.forEach(adventurer -> {
            adventurer.setRoom(starterRoom);
            starterRoom.addAdventurer(adventurer);
        });

        //initializing spawn positions for creatures
        creatures.forEach(creature -> {
            Integer level, verticalDir = null, horizontalDir = null;
            if (creature instanceof Orbiter) {
                level = GameUtility.getRandomInRange(TOPMOST_ROOM, BOTTOM_MOST_ROOM);
                // Orbiters start randomly in the outer rooms of any level
                ArrayList<Pair<Integer,Integer>> possibleStaringRooms = new ArrayList<>();
                for(int i=0;i<2;i++)
                {
                    for(int j=0;j<2;j++)
                    {
                        if(!(i==1 && j==1))
                            possibleStaringRooms.add(new Pair(i,j));
                    }
                }
                int value = GameUtility.getRandomInRange(0, possibleStaringRooms.size()-1);
                Pair<Integer,Integer> p = possibleStaringRooms.get(value);
                verticalDir = p.getValue0();
                horizontalDir = p.getValue1();
            } else if (creature instanceof Blinker) { //blinkers start in a random room on 4th level
                level = 4;
                verticalDir = GameUtility.getRandomInRange(NORTHMOST_ROOM, SOUTHMOST_ROOM);
                horizontalDir = GameUtility.getRandomInRange(WESTMOST_ROOM, EASTMOST_ROOM);
            } else { //Seeker start randomly in any of the levels
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

    private void getNeighbours(Room room) {
        ArrayList<Room> neighbors = new ArrayList<>();
        Triplet<Integer, Integer, Integer> roomCoordinates = room.getRoomCoordinates();
        int roomWest = Integer.max(WESTMOST_ROOM, roomCoordinates.getValue2() - 1);
        int roomEast = Integer.min(EASTMOST_ROOM, roomCoordinates.getValue2() + 1);
        int roomNorth = Integer.max(NORTHMOST_ROOM, roomCoordinates.getValue1() - 1);
        int roomSouth = Integer.min(SOUTHMOST_ROOM, roomCoordinates.getValue1() + 1);
        int roomLevel = roomCoordinates.getValue0();
        for (int row = roomNorth; row <= roomSouth; row++) {
            if (row != roomCoordinates.getValue1()) {
                neighbors.add(findRoom(new Triplet<>(roomLevel, row, roomCoordinates.getValue2())));
            }
        }
        for (int column = roomWest; column <= roomEast; column++) {
            if (column != roomCoordinates.getValue2()) {
                neighbors.add(findRoom(new Triplet<>(roomLevel, roomCoordinates.getValue1(), column)));
            }
        }
        if (roomCoordinates.getValue2() == 1 && roomCoordinates.getValue1() == 1) {
            int roomAbove = Integer.max(TOPMOST_ROOM, roomLevel - 1);
            int roomBelow = Integer.min(BOTTOM_MOST_ROOM, roomLevel + 1);
            for (int level = roomAbove; level <= roomBelow; level++) {
                if (level != roomLevel) {
                    neighbors.add(findRoom(new Triplet<>(level, roomCoordinates.getValue1(), roomCoordinates.getValue2())));
                }
            }
        }
        room.setConnectedRooms(neighbors);
    }

    public Room findRoom(Triplet<Integer, Integer, Integer> roomID) {
        return boardRoomList.stream()
                .filter(room -> room.getRoomCoordinates().compareTo(roomID) == 0)
                .findFirst()
                .orElse(null);
    }

    public void printGameStatus() {
        System.out.println("Turn : " + turnCounter++);
        //TODO: print rooms, adventurers, creatures
        System.out.println("Board Status : ");

        System.out.println(findRoom(new Triplet<>(0,1,1)).getRoomDetails());
        for (int level = TOPMOST_ROOM; level <= BOTTOM_MOST_ROOM; level++) {
            for (int row = WESTMOST_ROOM; row <= EASTMOST_ROOM; row++) {
                for (int column = NORTHMOST_ROOM; column <= SOUTHMOST_ROOM; column++) {
                    System.out.print(findRoom(new Triplet<>(level,row,column)).getRoomDetails()+ '\t');
                }
                System.out.println();
            }
        }


        System.out.println("Adventurers Status : ");
        for(Adventurer adventurer: adventurers) {
            System.out.println(adventurer.getAdventurerStatus());
        }

        System.out.println("Creatures Status : ");
        HashMap<String,Integer> creatureStatus = new HashMap<>();
        creatureStatus.put("Orbiter", 0);
        creatureStatus.put("Blinker", 0);
        creatureStatus.put("Seeker", 0);
        for(Creature creature: creatures) {
            String name=creature.getAbbrv();
            if(creature.isAlive()) {
                if(name.equals("O"))
                   creatureStatus.put("Orbiter", 1+creatureStatus.getOrDefault("Orbiter",0));
                else if(name.equals("B"))
                    creatureStatus.put("Blinker", 1+creatureStatus.getOrDefault("Blinker",0));
                else
                    creatureStatus.put("Seeker", 1+creatureStatus.getOrDefault("Seeker",0));
            }

        }
        creatureStatus.forEach((key,value) ->
                System.out.println(key + " - " + String.valueOf(value) + " Remaining"));

    }


}
