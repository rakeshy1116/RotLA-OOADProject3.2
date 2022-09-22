package RotLA.Creatures;

import RotLA.GameUtility;
import RotLA.Room;
import org.javatuples.Triplet;

public class Blinker extends Creature {
    public Blinker() {
        this.alive = true;
        this.abbrv = "B";
        this.name = "Blinker";
    }

    public void move() {
        Room oldRoom = this.room;
        if (oldRoom.getAdventurers().isEmpty()) { // blinker moves randomly to any room only if there are no adventurers in the current room
                int level = GameUtility.getRandomInRange(1, 4);
                int row = GameUtility.getRandomInRange(0, 2);
                int column = GameUtility.getRandomInRange(0, 2);
                Room newRoom = roomFinder.findRoom(new Triplet<>(level, row, column)); //assumption: here new room can be same as old room if random values
            //give same values as old room number
                newRoom.addCreature(this);
                this.setRoom(newRoom);
                oldRoom.removeCreature(this);  //creature is removed from the old room and gets added to new room

        }

    }

}
