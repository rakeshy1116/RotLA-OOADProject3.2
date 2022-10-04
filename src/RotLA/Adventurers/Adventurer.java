package RotLA.Adventurers;

import RotLA.Celebration.*;
import RotLA.CombatStrategy.CombatStrategy;
import RotLA.Creatures.Creature;
import RotLA.Dice;
import RotLA.GameUtility;
import RotLA.Room;
import RotLA.SearchStrategy.SearchStrategy;
import RotLA.Treasures.Treasures;

import java.util.ArrayList;
import java.util.List;

import static RotLA.GameUtility.TREASURES_MIN_ROLL;

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


    public String getAdventurerName() {
        return adventurerName;
    }

    public void setAdventurerName(String adventurerName) {
        this.adventurerName = adventurerName;
    }

    public Adventurer(CombatStrategy combatStrategy) {
        this.combatStrategy = combatStrategy;
    }
    public Adventurer() {

    }

    protected CombatStrategy combatStrategy;
    protected SearchStrategy searchStrategy;
    protected List<Treasures> treasures;

    //------------------------------Getter/Setter Methods--------------------------------------

    //A setter method for the room instance
    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Treasures> getTreasures() {
        return treasures;
    }

    public void setTreasures(List<Treasures> treasures) {
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
        return noOfDamages < 3;
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
        for (Creature creature : copyCreatureList) {
            for(int i=0;i<4;i++)
            {
                int temp= dice.getCelebrateRoll();
                while(temp-->0) {
                    if(celebrations.get(i).equals("Dance")){
                        combatStrategy=new Dance(combatStrategy);
                    }
                    else if(celebrations.get(i).equals("Shout")){
                        combatStrategy=new Shout(combatStrategy);
                    }
                    else if(celebrations.get(i).equals("Jump")){
                        combatStrategy=new Jump(combatStrategy);
                    }
                    else if(celebrations.get(i).equals("Spin")){
                        combatStrategy=new Spin(combatStrategy);
                    }
                    else {

                    }
                }
            }
            combatStrategy.fight(dice, creature, this,0);
        }
    }

    //Performs find treasure operation, common default method for all subclasses
    protected void findTreasure(Dice dice) {
        int currentDiceVal = rollDiceTreasure(dice);
        //increment treasures if rolled higher
        if (currentDiceVal >= TREASURES_MIN_ROLL)
            noOfTreasure++;
    }

    //Performs move operation, common default behaviour for all subclasses
    protected void move() {
        Room oldRoom = this.room;
        List<Room> rooms = oldRoom.getConnectedRooms();
        int noOfConnectedRooms = rooms.size();
        // Get a random room from the connected rooms
        int movement = GameUtility.getRandomInRange(0, noOfConnectedRooms - 1);
        Room newRoom = rooms.get(movement);
        // remove adventurer from old room
        oldRoom.removeAdventurer(this);
        // add adventurer to new room
        newRoom.addAdventurer(this);
        // update new room in Adventurer
        this.room = newRoom;
    }
}


