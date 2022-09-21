package RotLA;

import RotLA.Adventurers.Adventurer;
import RotLA.Creatures.Creature;
import org.javatuples.Triplet;

import java.util.ArrayList;

public class Room {
    private Triplet<Integer, Integer, Integer> roomCoordinates;
    private ArrayList<Room> connectedRooms;

    private ArrayList<Adventurer> adventurers;

    private ArrayList<Creature> creatures;

    public Room(int level, int verticalDir, int horizontalDir) {
        this.roomCoordinates = new Triplet<>(level, verticalDir, horizontalDir);
        adventurers = new ArrayList<>();
        creatures = new ArrayList<>();
    }

    public void setConnectedRooms(ArrayList<Room> connectedRooms) {
        this.connectedRooms = connectedRooms;
    }

    public ArrayList<Room> getConnectedRooms() {
        return connectedRooms;
    }

    public Triplet<Integer, Integer, Integer> getRoomCoordinates() {
        return roomCoordinates;
    }

    public void addAdventurer(Adventurer adventurer) {
        adventurers.add(adventurer);
    }

    public void addCreature(Creature creature) {
        creatures.add(creature);
    }

    public void removeAdventurer(Adventurer adventurer) {
        for (int i = 0; i < adventurers.size(); i++) {
            if (adventurers.get(i).equals(adventurer))
                adventurers.remove(i);
        }
    }

    public void removeCreature(Creature creature) {
        for (int i = 0; i < creatures.size(); i++) {
            if (creatures.get(i).equals(creature))
                creatures.remove(i);
        }
    }

    public ArrayList<Adventurer> getAdventurers() {
        return adventurers;
    }

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }

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

        return roomLocationString + " : " + adventurerString + " : " + creatureString;
    }
}
