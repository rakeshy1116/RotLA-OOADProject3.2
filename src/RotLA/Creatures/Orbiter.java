package RotLA.Creatures;

import RotLA.GameUtility;
import RotLA.Room;
import org.javatuples.Triplet;

public class Orbiter extends Creature {

    Boolean isClockWise;

    public Orbiter() {
        this.alive = true;
        isClockWise = GameUtility.getRandomInRange(0, 1) == 1; //each orbiter during instantization are
        // assigned clockwise-or-anticlockwise
        abbrv = "O";
    }

    public void move() {
        Room oldRoom = this.room;
        if (oldRoom.getAdventurers().isEmpty()) { //orbiter like other creatures move only if there are no adventurers in current room
            Triplet<Integer, Integer, Integer> newRoomCoordinates;
            if (isClockWise) {
                newRoomCoordinates = getClockWiseNext(oldRoom.getRoomCoordinates()); //clockwise movement
            } else {
                newRoomCoordinates = getAntiClockWiseNext(oldRoom.getRoomCoordinates()); //anticlockwise movement
            }
            Room newRoom = roomFinder.findRoom(newRoomCoordinates);
            oldRoom.removeCreature(this);
            newRoom.addCreature(this);
            this.room = newRoom;
        }
    }

    private Triplet<Integer, Integer, Integer> getClockWiseNext(Triplet<Integer, Integer, Integer> roomCoordinate) {
        //returns new room coordinates which is one step away in clockwise direction
        int row = roomCoordinate.getValue1();
        int column = roomCoordinate.getValue2();
        if (row == 0 && column >= 0 && column < 2) {
            column++;
        } else if (row == 2 && column <= 2 && column > 0) {
            column--;
        } else if (column == 0 && row <= 2 && row > 0) {
            row--;
        } else if (column == 2 && row < 2 && row >= 0) {
            row++;
        }
        return new Triplet<>(roomCoordinate.getValue0(), row, column);
    }

    private Triplet<Integer, Integer, Integer> getAntiClockWiseNext(Triplet<Integer, Integer, Integer> roomCoordinate) {
        //returns new room coordinates which is one step away in anticlockwise direction
        int row = roomCoordinate.getValue1();
        int column = roomCoordinate.getValue2();
        if (row == 0 && column > 0 && column <= 2) {
            column--;
        } else if (row == 2 && column < 2 && column >= 0) {
            column++;
        } else if (column == 0 && row < 2 && row >= 0) {
            row++;
        } else if (column == 2 && row <= 2 && row > 0) {
            row--;
        }
        return new Triplet<>(roomCoordinate.getValue0(), row, column);
    }

}
