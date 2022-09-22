package RotLA.Adventurers;

import RotLA.Creatures.Creature;
import RotLA.Dice;
import RotLA.GameUtility;
import RotLA.Room;

import java.util.ArrayList;
import java.util.List;

import static RotLA.GameUtility.TREASURES_MIN_ROLL;

//------------CONCEPTS EXPLAINED IN THIS CLASS ----------------
// Inheritance
// Encapsulation
// Polymorphism
// Abstraction
// Identity
// Cohesion
//------------------------------------------------------------

//Adventurer is an abstract class, it is extended by different subclass types - Brawler, Runner, Sneaker, Thief
// CONCEPT INHERITANCE - Each of these subclasses inherit several instance variable and method implementations pertaining to adventurer behaviour
abstract public class Adventurer {
    // CONCEPT POLYMORPHISM - The following instance variables and some methods are marked protected, which means they are encapsulated
// from other classes. They are only visible in the inheritance heirarchy. Exposed to other classes through getter/setter methods
    protected int noOfDamages;  //represents number of damage instances taken by an Adventurer
    protected Room room; //the room instance where the object is currently located
    protected int noOfTreasure; //total number of treasures found by an Adventurer
    protected String abbrv;  // Abbreviation of Adventurer type, for eg: B for Brawler
    protected String adventurerName; // Type of the Adventurer

    //------------------------------Getter/Setter Methods--------------------------------------

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
        // ASSUMPTION: Fights creatures in the order of their room entry, i.e order of entry to the list creatures
        for (Creature creature : copyCreatureList) {
            //get adventurer's roll
            int adventurerRoll = rollDiceFight(dice);
            //get creature's roll
            int creatureRoll = creature.rollDice(dice);
            if (adventurerRoll > creatureRoll) {
                // if adventurer wins, kill creature, remove from room
                creature.die();
                this.room.removeCreature(creature);
            } else if (creatureRoll > adventurerRoll) {
                // if creature wins, make adventurer take damage
                takeDamage();
                if (!isAlive()) {
                    // if adventurer is dead remove from room and end the fights
                    this.room.removeAdventurer(this);
                    break;
                }
            } else {
                // if both roll the same, then nothing happens
            }
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


