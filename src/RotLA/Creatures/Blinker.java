package RotLA.Creatures;

import RotLA.GameUtility;
import RotLA.Room;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.ArrayList;

public class Blinker extends Creature {

    public Blinker() {
        this.alive = true;
    }

    public void move() {
        Room oldRoom = this.getRoom();
        if (oldRoom.getAdventurers().size() == 0) {
            int level = GameUtility.getRandomInRange(1, 4);
            int row = GameUtility.getRandomInRange(0, 2);
            int column = GameUtility.getRandomInRange(0, 2);
            Room newRoom = getRoomFinder().findRoom(new Triplet<>(level, row, column));
            oldRoom.removeCreature(this);
            newRoom.addCreature(this);
            this.setRoom(newRoom);
        }

    }

}
