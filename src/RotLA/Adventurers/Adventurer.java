package RotLA.Adventurers;

import RotLA.Celebration.Dance;
import RotLA.Celebration.Jump;
import RotLA.Celebration.Shout;
import RotLA.Celebration.Spin;
import RotLA.CombatStrategy.CombatStrategy;
import RotLA.Creatures.Creature;
import RotLA.Dice;
import RotLA.Events.Event;
import RotLA.GameUtility;
import RotLA.Room;
import RotLA.RoomFinder;
import RotLA.SearchStrategy.SearchStrategy;
import RotLA.Treasures.Portal;
import RotLA.Treasures.Treasures;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SubmissionPublisher;

import static RotLA.GameUtility.*;

//Adventurer is an abstract class, it is extended by different subclass types - Brawler, Runner, Sneaker, Thief
// CONCEPT INHERITANCE, COHESION - Each of these subclasses inherit several instance variable and method implementations pertaining to adventurer behaviour
abstract public class Adventurer {
    // CONCEPT ENCAPSULATION - The following instance variables and some methods are marked protected, which means they are encapsulated
    // from other classes. They are only visible in the inheritance heirarchy. Exposed to other classes through getter/setter methods
    protected int noOfDamages;  //represents number of damage instances taken by an Adventurer
    protected Room room; //the room instance where the object is currently located
    protected int noOfTreasure; //total number of treasures found by an Adventurer
    protected String abbrv;  // Abbreviation of Adventurer type, for eg: B for Brawler
    protected String adventurerName; // Type of the Adventurer

    protected RoomFinder roomFinder;
    protected CombatStrategy combatStrategy;
    protected SearchStrategy searchStrategy;
    protected ArrayList<Treasures> treasures;
    protected int maxDamages;
    protected SubmissionPublisher<Event> publisher;

    public void setRoomFinder(RoomFinder roomFinder) {
        this.roomFinder = roomFinder;
    }

    public int getMaxDamages() {
        return maxDamages;
    }

    public void setMaxDamages(int maxDamages) {
        this.maxDamages = maxDamages;
    }

    public String getAdventurerName() {
        return adventurerName;
    }

    //------------------------------Getter/Setter Methods--------------------------------------

    public ArrayList<Treasures> getTreasures() {
        return treasures;
    }

    public void setTreasures(ArrayList<Treasures> treasures) {
        this.treasures = treasures;
    }

    public void addTreasure(Treasures treasure) {
        this.treasures.add(treasure);
        this.noOfTreasure = this.treasures.size();
    }

    public Room getRoom() {
        return room;
    }

    //A setter method for the room instance
    public void setRoom(Room room) {
        this.room = room;
    }

    // a getter method for the no of treasures found by the Adventurer
    public int getNoOfTreasure() {
        return noOfTreasure;
    }

    // A getter method to get name abbreviation of the Adventurer
    public String getAbbrv() {
        return abbrv;
    }

//    ----------------------Publicly exposed methods/behaviour---------------------

    // Adds damage to an adventurer, whenever adventurer loses a fight
    // a default behaviour for all adventurers, hence not abstract method
    public void takeDamage() {
        noOfDamages++;
        publisher.submit(new Event.AdventurerDamage(getAdventurerName(), String.valueOf(noOfDamages)));
    }

    // Checks if the Adventurer is alive, alive if adventurer has suffered a max damage of 2
    public boolean isAlive() {
        return noOfDamages < maxDamages;
    }

    // Performs all the operations of an Adventurer in a turn, like move, fight or find treasure
    public void performTurn(Dice dice) {
        move();
        List<Creature> creaturesInRoom = room.getCreatures();
        //After moving if the room has any creatures, fight them other wise find treasure
        if (!creaturesInRoom.isEmpty())
            fight(dice, creaturesInRoom);
        else
            findTreasure(dice);
    }

    // Returns a dice roll of an Adventurer for a fight, overriden by each subclasse for special powers
    public int rollDiceFight(Dice dice) {
        return dice.getDiceRoll();
    }

    // Returns a dice roll of an Adventurer for finding a treasure, overriden by each subclasse for special powers
    protected int rollDiceTreasure(Dice dice) {
        return dice.getDiceRoll();
    }

    // Returns a String giving an Adventurer's current status like current treasures and damage taken
    public String getAdventurerStatus() {
        return adventurerName + " - " + noOfTreasure + " Treasure(s)" + " - " + noOfDamages + " Damage";
    }

//    --------------------Private/Protected Methods (Unexposed to other types) ------------

    // Performs fight operation of the Adventurer, common default behaviour of all subclasses
    protected void fight(Dice dice, List<Creature> creatures) {
        // Fight all creatures in the room
        // Creating copy to avoid concurrent updates in room and skipping fight concerns
        List<Creature> copyCreatureList = new ArrayList<>(creatures);
        // ASSUMPTION: Fights creatures in the order of their room entry, i.e order of entry to the list creatures
        for (Creature creature : copyCreatureList) {
            CombatStrategy modifiedCombat = prepareCelebrations();
            String celebrateMessage = modifiedCombat.fight(dice, creature, this, publisher);
            if (celebrateMessage != null && celebrateMessage.contains(adventurerName))
                publisher.submit(new Event.AdventurerCelebrates(adventurerName, celebrateMessage));
        }
    }

    private CombatStrategy prepareCelebrations() {
        CombatStrategy modifiedCombat = combatStrategy;
        // get a random count of celebrations for every celebration and wrap strategy
        for (String celebration : getAllCelebrations()) {
            int repeat = getRandomInRange(0, 2);
            while (repeat-- > 0) {
                if (celebration.equalsIgnoreCase("Dance")) {
                    modifiedCombat = new Dance(modifiedCombat);
                } else if (celebration.equalsIgnoreCase("Spin")) {
                    modifiedCombat = new Spin(modifiedCombat);
                } else if (celebration.equalsIgnoreCase("Jump")) {
                    modifiedCombat = new Jump(modifiedCombat);
                } else if (celebration.equalsIgnoreCase("Shout")) {
                    modifiedCombat = new Shout(modifiedCombat);
                }
            }
        }
        return modifiedCombat;
    }

    //Performs find treasure operation, common default method for all subclasses
    public void findTreasure(Dice dice) {
        if (this.room.getTreasures().size() > 0) {
            searchStrategy.search(this, dice, publisher);
        }
    }

    //Performs move operation, common default behaviour for all subclasses
    protected void move() {
        boolean hasPortal = false;
        boolean usePortal = false;
        for (Treasures treasures : this.getTreasures()) {
            if (treasures instanceof Portal) {
                hasPortal = true;
                break;
            }
        }
        Room newRoom;
        Room oldRoom = this.room;

        //ASSUMPTION: If Adventures posseses a Portal, then he has a 25% chance to use it in every move
        if (hasPortal) {
            int chance = GameUtility.getRandomInRange(1, 4);
            if (chance == 1) usePortal = true;
        }

        if (hasPortal && usePortal) {
            int level, verticalDir, horizontalDir;
            level = GameUtility.getRandomInRange(TOPMOST_ROOM, BOTTOM_MOST_ROOM);
            verticalDir = GameUtility.getRandomInRange(NORTHMOST_ROOM, SOUTHMOST_ROOM);
            horizontalDir = GameUtility.getRandomInRange(WESTMOST_ROOM, EASTMOST_ROOM);
            newRoom = roomFinder.findRoom(new Triplet<>(level, verticalDir, horizontalDir));
        } else {
            List<Room> rooms = oldRoom.getConnectedRooms();
            int noOfConnectedRooms = rooms.size();
            // Get a random room from the connected rooms
            int movement = GameUtility.getRandomInRange(0, noOfConnectedRooms - 1);
            newRoom = rooms.get(movement);
        }
        // remove adventurer from old room
        oldRoom.removeAdventurer(this);
        // add adventurer to new room
        newRoom.addAdventurer(this);
        // update new room in Adventurer
        this.room = newRoom;
        publisher.submit(new Event.GameObjectEnters(adventurerName, room.getRoomName()));
    }

    public boolean hasTreasure(Treasures currentTreasure) {
        for (int i = 0; i < this.getTreasures().size(); i++) {
            Treasures treasure = this.getTreasures().get(i);
            if (treasure.getClass() == currentTreasure.getClass()) {
                return true;
            }
        }
        return false;
    }
}


