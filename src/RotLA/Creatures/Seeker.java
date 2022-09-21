package RotLA.Creatures;

import RotLA.GameUtility;
import RotLA.Room;

import java.util.ArrayList;

public class Seeker extends Creature {

    public Seeker() {
        this.alive = true;
        this.abbrv = "S";
    }

    public void move() {  //seeker moves only is there are no adventures are in current room or adventurers are present in adjacent room
        Room oldRoom = this.room;
        if (oldRoom.getAdventurers().isEmpty()) {
            ArrayList<Room> neighbourRooms = oldRoom.getConnectedRooms();
            ArrayList<Room> possibleRooms = new ArrayList<>();
            neighbourRooms.forEach(room -> {   //checking if adjacent rooms have adventurers or not, if they have adventurers
                // adding them to possible rooms
                if (!room.getAdventurers().isEmpty()) {
                    possibleRooms.add(room);
                }
            });
            if (!possibleRooms.isEmpty()) { //if there are any adventurers in adjacent rooms, seekers move to one of them randomly
                int value = GameUtility.getRandomInRange(0, possibleRooms.size() - 1);
                Room newRoom = possibleRooms.get(value);
                oldRoom.removeCreature(this); //removing seeker from current room
                newRoom.addCreature(this); //adding seeker to new room
                this.room = newRoom;
            }
        }
    }
}

