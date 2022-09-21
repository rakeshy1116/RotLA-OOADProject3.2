package RotLA.Creatures;

import RotLA.GameUtility;
import RotLA.Room;

import java.util.ArrayList;

public class Seeker extends Creature {

    public Seeker() {
        this.alive = true;
        this.abbrv = "S";
    }

    public void move() {
        Room oldRoom = this.room;
        if (oldRoom.getAdventurers().isEmpty()) {
            ArrayList<Room> neighbourRooms = oldRoom.getConnectedRooms();
            ArrayList<Room> possibleRooms = new ArrayList<>();
            neighbourRooms.forEach(room -> {
                if (!room.getAdventurers().isEmpty()) {
                    possibleRooms.add(room);
                }
            });
            if (!possibleRooms.isEmpty()) {
                int value = GameUtility.getRandomInRange(0, possibleRooms.size() - 1);
                Room newRoom = possibleRooms.get(value);
                oldRoom.removeCreature(this);
                newRoom.addCreature(this);
                this.room = newRoom;
            }
        }
    }
}

