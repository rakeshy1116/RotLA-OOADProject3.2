package RotLA.Creatures;

import RotLA.GameUtility;
import RotLA.Room;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.ArrayList;

public class Seeker extends Creature {

    public Seeker() {
        this.alive = true;
    }

    public void move() {

        //TODO after room
        Room oldRoom = this.getRoom();
        if (oldRoom.getAdventurers().size() == 0) {
            ArrayList<Room> neighbourRooms = oldRoom.getConnectedRooms();
            ArrayList<Room> possibleRooms = new ArrayList<>();
            for (int i = 0; i < neighbourRooms.size(); i++) {
                if (neighbourRooms.get(i).getAdventurers().size() > 0) {
                    possibleRooms.add(neighbourRooms.get(i));
                }
            }
            if(!possibleRooms.isEmpty()) {
                int value = GameUtility.getRandomInRange(0, possibleRooms.size() - 1);
                Room newRoom = possibleRooms.get(value);
                oldRoom.removeCreature(this);
                newRoom.addCreature(this);
                this.setRoom(newRoom);
            }
        }
    }
}

