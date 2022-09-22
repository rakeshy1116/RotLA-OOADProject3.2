package RotLA.Creatures;

import RotLA.GameUtility;
import RotLA.Room;
import org.javatuples.Triplet;

import static RotLA.GameUtility.*;

// CONCEPT: INHERITANCE - A type of Creature that inherits variables and behaviour from Creature
public class Orbiter extends Creature {

    Boolean isClockWise;

    public Orbiter() {
        this.alive = true;
        isClockWise = GameUtility.getRandomInRange(0, 1) == 1; //each orbiter during instantization are
        // assigned clockwise-or-anticlockwise initially
        abbrv = "O";
        this.name = "Orbiter";
    }

    //implementing the abstract move method for Orbiters, Always move in a direction on the outer rooms
    public void move() {
        Room oldRoom = this.room;
        Triplet<Integer, Integer, Integer> newRoomCoordinates;
        if (isClockWise) {
            newRoomCoordinates = getClockWiseNext(oldRoom.getRoomCoordinates()); //if clockwise, find next room in clockwise directiom
        } else {
            newRoomCoordinates = getAntiClockWiseNext(oldRoom.getRoomCoordinates()); //if anticlockwise, find next room in anticlockwise directiom
        }
        Room newRoom = roomFinder.findRoom(newRoomCoordinates);
        oldRoom.removeCreature(this);
        newRoom.addCreature(this);
        this.room = newRoom;
    }

    // returns coordinates of next room in clockwise direction, given rooms coordinates
    private Triplet<Integer, Integer, Integer> getClockWiseNext(Triplet<Integer, Integer, Integer> roomCoordinate) {
        int row = roomCoordinate.getValue1();
        int column = roomCoordinate.getValue2();
        if (row == NORTHMOST_ROOM && column >= WESTMOST_ROOM && column < EASTMOST_ROOM) {
            //if first row, move east
            column++;
        } else if (row == SOUTHMOST_ROOM && column <= EASTMOST_ROOM && column > WESTMOST_ROOM) {
            //if last row, move west
            column--;
        } else if (column == WESTMOST_ROOM && row <= SOUTHMOST_ROOM && row > NORTHMOST_ROOM) {
            //if first column, move noeth
            row--;
        } else if (column == EASTMOST_ROOM && row < SOUTHMOST_ROOM && row >= NORTHMOST_ROOM) {
            //if last column, move south
            row++;
        }
        return new Triplet<>(roomCoordinate.getValue0(), row, column);
    }

    // returns coordinates of next room in anti-clockwise direction, given rooms coordinates
    private Triplet<Integer, Integer, Integer> getAntiClockWiseNext(Triplet<Integer, Integer, Integer> roomCoordinate) {
        int row = roomCoordinate.getValue1();
        int column = roomCoordinate.getValue2();
        if (row == NORTHMOST_ROOM && column > WESTMOST_ROOM && column <= EASTMOST_ROOM) {
            // if first row, move west
            column--;
        } else if (row == SOUTHMOST_ROOM && column < EASTMOST_ROOM && column >= WESTMOST_ROOM) {
            // if last row, move east
            column++;
        } else if (column == WESTMOST_ROOM && row < SOUTHMOST_ROOM && row >= NORTHMOST_ROOM) {
            // if first column, move south
            row++;
        } else if (column == EASTMOST_ROOM && row <= SOUTHMOST_ROOM && row > NORTHMOST_ROOM) {
            // last column, move north
            row--;
        }
        return new Triplet<>(roomCoordinate.getValue0(), row, column);
    }

}
