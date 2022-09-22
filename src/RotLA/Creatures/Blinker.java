package RotLA.Creatures;

import RotLA.GameUtility;
import RotLA.Room;
import org.javatuples.Triplet;

// CONCEPT: INHERITANCE - A type of Creature that inherits variables and behaviour from Creature
public class Blinker extends Creature {
    public Blinker() {
        this.alive = true;
        this.abbrv = "B";
        this.name = "Blinker";
    }

    //implementing the abstract move method for Blinkers, Can randomnly move anywhere on the board
    public void move() {
        Room oldRoom = this.room;
        int level = GameUtility.getRandomInRange(GameUtility.TOPMOST_ROOM, GameUtility.BOTTOM_MOST_ROOM);
        int row = GameUtility.getRandomInRange(GameUtility.NORTHMOST_ROOM, GameUtility.SOUTHMOST_ROOM);
        int column = GameUtility.getRandomInRange(GameUtility.WESTMOST_ROOM, GameUtility.EASTMOST_ROOM);
        Room newRoom = roomFinder.findRoom(new Triplet<>(level, row, column));
        //ASSUMPTION: here new room can be same as old room if random values give same values as old room number
        newRoom.addCreature(this);
        this.setRoom(newRoom);
        oldRoom.removeCreature(this);  //creature is removed from the old room and gets added to new room
    }
}
