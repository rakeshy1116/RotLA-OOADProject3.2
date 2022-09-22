package RotLA;

import RotLA.Adventurers.Adventurer;
import RotLA.Creatures.Creature;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.List;

//A class that represents Room
public class Room {

    //CONCEPT: ENCAPSULATION - All variables are encapsulated with private modifier. They are accesible through getter and setter methods
    //A coordinate triplet (ref:https://www.javatuples.org/apidocs/org/javatuples/Triplet.html) of level (z), vertical (x) , horizontal (y) that represents rooms location in the board
    private Triplet<Integer, Integer, Integer> roomCoordinates;
    //connecting rooms of the room in board
    private List<Room> connectedRooms;
    //adventurers currently in room
    private ArrayList<Adventurer> adventurers;

    //creatures currently in room
    private ArrayList<Creature> creatures;

    public Room(int level, int verticalDir, int horizontalDir) {
        this.roomCoordinates = new Triplet<>(level, verticalDir, horizontalDir);
        adventurers = new ArrayList<>();
        creatures = new ArrayList<>();
    }

    public void setConnectedRooms(List<Room> connectedRooms) {
        this.connectedRooms = connectedRooms;
    }

    public List<Room> getConnectedRooms() {
        return connectedRooms;
    }

    public Triplet<Integer, Integer, Integer> getRoomCoordinates() {
        return roomCoordinates;
    }

    // Adds adventurer to the room
    public void addAdventurer(Adventurer adventurer) {
        adventurers.add(adventurer);
    }

    // Adds creature to the room
    public void addCreature(Creature creature) {
        creatures.add(creature);
    }

    //Removes an adventurer from the room
    public void removeAdventurer(Adventurer adventurer) {
        for (int i = 0; i < adventurers.size(); i++) {
            if (adventurers.get(i).equals(adventurer))
                adventurers.remove(i);
        }
    }

    // Removes creature from the room
    public void removeCreature(Creature creature) {
        for (int i = 0; i < creatures.size(); i++) {
            // CONCEPT: IDENTITY - Notice that creature list can have multiple instances of same tyoe in the list
            // For eg: 2 orbiters can be same room, in such case equals method still return false because each instance have their
            // own identity. Even though type and data for 2 orbiters might be same, their hash of reference is different
            if (creatures.get(i).equals(creature))
                creatures.remove(i);
        }
    }

    // returns adventurers currently in room
    public ArrayList<Adventurer> getAdventurers() {
        return adventurers;
    }

    // returns all creatures currently in room
    public ArrayList<Creature> getCreatures() {
        return creatures;
    }

    // Returns a String of roomDetails, including its location and adventurers and creatures in it
    public String getRoomDetails() {
        String roomLocationString = roomCoordinates.getValue0() + "-" + getRoomCoordinates().getValue1() + "-" + getRoomCoordinates().getValue2();
        String adventurerString = "", creatureString = "";
        for (Adventurer adventurer : adventurers) {
            adventurerString = adventurerString + adventurer.getAbbrv();
        }
        if (adventurerString.isBlank()) adventurerString = "-";
        for (Creature creature : creatures) {
            creatureString = creatureString + creature.getAbbrv();
        }
        if (creatureString.isBlank()) creatureString = "-";

        return roomLocationString + " : " + adventurerString + " : " + creatureString + " ";
    }
}
