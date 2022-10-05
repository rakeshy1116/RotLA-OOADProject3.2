package RotLA.Adventurers;

import RotLA.*;
import RotLA.Celebration.*;
import RotLA.CombatStrategy.CombatStrategy;
import RotLA.Creatures.Creature;
import RotLA.SearchStrategy.SearchStrategy;
import RotLA.Treasures.Portal;
import RotLA.Treasures.Treasures;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.List;

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

    public void setRoomFinder(RoomFinder roomFinder) {
        this.roomFinder = roomFinder;
    }

    public int getMaxDamages() {
        return maxDamages;
    }

    public void setMaxDamages(int maxDamages) {
        this.maxDamages = maxDamages;
    }

    int maxDamages;

    public String getAdventurerName() {
        return adventurerName;
    }

    public void setAdventurerName(String adventurerName) {
        this.adventurerName = adventurerName;
    }

    public Adventurer(CombatStrategy combatStrategy, SearchStrategy searchStrategy) {

        this.combatStrategy = combatStrategy;
        this.searchStrategy = searchStrategy;
        this.setMaxDamages(3);
        this.setTreasures(new ArrayList<>());
    }

    public Adventurer() {

    }


    //------------------------------Getter/Setter Methods--------------------------------------

    //A setter method for the room instance
    public void setRoom(Room room) {
        this.room = room;
    }

    public ArrayList<Treasures> getTreasures() {
        return treasures;
    }

    public void addTreasure(Treasures treasure) {
        this.treasures.add(treasure);
        this.noOfTreasure = this.treasures.size();
    }

    public void removeTreasure(Treasures treasure) {
        for (int i = 0; i < treasures.size(); i++) {
            if (treasures.get(i).equals(treasure))
                treasures.remove(i);
        }
    }

    public void setTreasures(ArrayList<Treasures> treasures) {
        this.treasures = treasures;
    }

    public Room getRoom() {
        return room;
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
        List<String> celebrations = new ArrayList<>();
        celebrations.add("Dance");
        celebrations.add("Shout");
        celebrations.add("Jump");
        celebrations.add("Spin");

        // ASSUMPTION: Fights creatures in the order of their room entry, i.e order of entry to the list creatures
        CombatStrategy localRefCombatStrategy = combatStrategy;
        for (int i = 0; i < 4; i++) {
            int temp = dice.getCelebrateRoll();
            while (temp-- > 0) {
                if (celebrations.get(i).equals("Dance")) {
                    localRefCombatStrategy = new Dance(localRefCombatStrategy);
                } else if (celebrations.get(i).equals("Shout")) {
                    localRefCombatStrategy = new Shout(localRefCombatStrategy);
                } else if (celebrations.get(i).equals("Jump")) {
                    localRefCombatStrategy = new Jump(localRefCombatStrategy);
                } else if (celebrations.get(i).equals("Spin")) {
                    localRefCombatStrategy = new Spin(localRefCombatStrategy);
                } else {

                }
            }
        }
        for (Creature creature : copyCreatureList) {
            String celebrateMessage = localRefCombatStrategy.fight(dice, creature, this, 0);
            System.out.println(celebrateMessage);
        }

    }

    //Performs find treasure operation, common default method for all subclasses
    protected void findTreasure(Dice dice) {
        if (this.room.getTreasures().size() > 0) {
            searchStrategy.search(this, dice, 0);
        }

//        int currentDiceVal = rollDiceTreasure(dice);
//        //increment treasures if rolled higher
//        if (currentDiceVal >= TREASURES_MIN_ROLL)
//            noOfTreasure++;
    }

    //Performs move operation, common default behaviour for all subclasses
    protected void move() {

        boolean hasPortal = false;
        for (Treasures treasures : this.getTreasures()) {
            if (treasures instanceof Portal)
                hasPortal = true;
        }
        Room newRoom;
        Room oldRoom = this.room;

        if (hasPortal) {
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
    }

    public boolean checkTreasure(Treasures currentTreasure) {

            for (int i = 0; i < this.getTreasures().size(); i++) {
                Treasures treasure = this.getTreasures().get(i);
                if (treasure.getClass() == currentTreasure.getClass()) {
                    return true;
                }
            }
        return false;

    }
}


