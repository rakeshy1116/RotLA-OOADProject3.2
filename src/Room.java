import java.util.ArrayList;

public class Room {
    String roomId;
    ArrayList<Adventurer> adventurers;
    ArrayList<Creature> creatures;
    ArrayList<Room> connectedRooms;

    public Room(String roomId, ArrayList<Adventurer> adventurers, ArrayList<Creature> creatures) {
        this.roomId = roomId;
        this.adventurers = adventurers;
        this.creatures = creatures;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public ArrayList<Adventurer> getAdventurers() {
        return adventurers;
    }

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }

    public ArrayList<Room> getConnectedRooms() {
        return connectedRooms;
    }

    public void addAdventurer(Adventurer adv) {
        this.adventurers.add(adv);
    }

    public void addCreature(Creature cre) {
        this.creatures.add(cre);
    }


}
