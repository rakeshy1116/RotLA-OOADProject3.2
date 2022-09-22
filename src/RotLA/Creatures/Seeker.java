package RotLA.Creatures;

import RotLA.GameUtility;
import RotLA.Room;

import java.util.ArrayList;
import java.util.List;

// CONCEPT: INHERITANCE - A type of Creature that inherits variables and behaviour from Creature
public class Seeker extends Creature {

    public Seeker() {
        this.alive = true;
        this.abbrv = "S";
        this.name = "Seeker";
    }

    // implementing the abstract move method for Seekers, Always move to a room where adventurers are prsent
    public void move() {
        //seeker moves only if adventurers are present in adjacent room
        Room oldRoom = this.room;
        List<Room> neighbourRooms = oldRoom.getConnectedRooms();
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

