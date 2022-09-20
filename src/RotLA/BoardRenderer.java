package RotLA;

import RotLA.Adventurers.Adventurer;
import RotLA.Creatures.Creature;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.List;

public class BoardRenderer {

    private List<Room> boardMatrix;
    private List<Adventurer> adventurers;
    private List<Creature> creatures;
    private Room starterRoom;

    private static final int WESTMOST_ROOM = 0;
    private static final int EASTMOST_ROOM = 2;
    private static final int NORTHMOST_ROOM = 0;
    private static final int SOUTHMOST_ROOM = 2;
    private static final int TOPMOST_ROOM = 1;
    private static final int BOTTOM_MOST_ROOM = 4;

    public BoardRenderer() {
        ArrayList<Room> boardList = new ArrayList<>();
        for (int level = TOPMOST_ROOM; level <= BOTTOM_MOST_ROOM; level++) {
            for (int row = WESTMOST_ROOM; row <= EASTMOST_ROOM; row++) {
                for (int column = NORTHMOST_ROOM; column <= SOUTHMOST_ROOM; column++) {
                    boardList.add(new Room(level, row, column));
                }
            }
        }
        boardList.forEach(this::getNeighbours);

        starterRoom = new Room(0, 1, 1);
        starterRoom.setNeighbours(List.of(findRoom(new Triplet<>(1, 1, 1))));
        adventurers.forEach(adventurer -> {
            adventurer.setRoom(starterRoom);
            starterRoom.addAdventurer(adventurer);
        });
        //TODO: set initial positions for creatures
    }

    private void getNeighbours(Room room) {
        ArrayList<Room> neighbors = new ArrayList();
        Triplet<Integer, Integer, Integer> roomCoordinates = room.getRoomId();
        int roomWest = Integer.max(WESTMOST_ROOM, roomCoordinates.getValue2() - 1);
        int roomEast = Integer.min(EASTMOST_ROOM, roomCoordinates.getValue2() + 1);
        int roomNorth = Integer.max(NORTHMOST_ROOM, roomCoordinates.getValue1() - 1);
        int roomSouth = Integer.min(SOUTHMOST_ROOM, roomCoordinates.getValue1() + 1);
        int roomLevel = roomCoordinates.getValue0();
        for (int row = roomNorth; row <= roomSouth; row++) {
            for (int column = roomWest; column <= roomEast; column++) {
                if (row != roomCoordinates.getValue1() || column != roomCoordinates.getValue2()) {
                    neighbors.add(findRoom(new Triplet<>(roomLevel, row, column)));
                    if (row == column) {
                        int roomAbove = Integer.max(TOPMOST_ROOM, roomLevel - 1);
                        int roomBelow = Integer.min(BOTTOM_MOST_ROOM, roomLevel + 1);
                        for (int level = roomAbove; level <= roomBelow; level++) {
                            if (level != roomLevel) {
                                neighbors.add(findRoom(new Triplet<>(level, row, column)));
                            }
                        }
                    }
                }
            }
        }
        room.setNeighbours(neighbors);
    }

    public Room findRoom(Triplet<Integer, Integer, Integer> roomID) {
        return boardMatrix.stream()
                .filter(room -> room.getRoomId().compareTo(roomID) == 0)
                .findFirst()
                .orElse(null);
    }

}
