package RotLA.Creatures;

import RotLA.GameUtility;
import RotLA.Room;
import org.javatuples.Triplet;

public class Blinker extends Creature {
    public Blinker() {
        this.alive = true;
        this.abbrv = "B";
    }

    public void move() {
        Room oldRoom = this.room;
        if (oldRoom.getAdventurers().isEmpty()) {
            int level = GameUtility.getRandomInRange(1, 4);
            int row = GameUtility.getRandomInRange(0, 2);
            int column = GameUtility.getRandomInRange(0, 2);
            Room newRoom = roomFinder.findRoom(new Triplet<>(level, row, column));
            oldRoom.removeCreature(this);
            newRoom.addCreature(this);
            this.setRoom(newRoom);
        }

    }

}
