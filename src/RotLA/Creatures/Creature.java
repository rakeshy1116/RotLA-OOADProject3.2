package RotLA.Creatures;

import RotLA.Adventurers.Adventurer;
import RotLA.Dice;
import RotLA.Events.Event;
import RotLA.Room;
import RotLA.RoomFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SubmissionPublisher;

//Adventurer is an abstract class, it is extended by different subclass types - Orbiter, Seeker, Blinker
// CONCEPT INHERITANCE, COHESION - Each of these subclasses inherit several instance variable and method implementations pertaining to creature behaviour
public abstract class Creature {
    // CONCEPT ENCAPSULATION - The following instance variables and some methods are marked protected, which means they are encapsulated
    // from other classes. They are only visible in the inheritance heirarchy. Exposed to other classes through getter/setter methods
    protected Room room; //instance of room where creature is currently present
    protected boolean alive; // flag that says if creature is alive or dead
    protected RoomFinder roomFinder; //roomFinder is used to find a particular room from boardList
    protected String abbrv; // name abbrivation of a creature type
    protected String name; // name of a creature type
    protected SubmissionPublisher<Event> publisher;

    // ----------------------- Public methods -----------------------------------------

    // Performs operations of a Creature during a turn including move() and fight() operations
    public void performTurn(Dice dice) {
        List<Adventurer> currentAdventuresInRoom = this.room.getAdventurers();
        if (currentAdventuresInRoom.isEmpty())
            //creatures move only if current room has no adventurers
            move();
        //room gets updated after creatures move
        currentAdventuresInRoom = this.room.getAdventurers();
        if (!currentAdventuresInRoom.isEmpty())
            //if any adventurer in room fight
            fight(dice, currentAdventuresInRoom);
    }

    // rolls the Dice object
    public int rollDice(Dice dice) {
        return dice.getDiceRoll();
    }

    //CONCEPT ABSTRACTION - abstract method move() provides a contract for extending classes to implement the behaviour
    abstract void move();  //each creature has different moves so all of them will override this abstract method

    // Dies, sets alive to false
    public void die() {
        this.alive = false;
    }

    // returns if the creature is alive
    public boolean isAlive() {
        return alive;
    }

    // -------------------------------------getter/setter methods------------------------------------------
    //returns name of the creature type
    public String getName() {
        return name;
    }

    public String getInstanceName() {
        return name + System.identityHashCode(this);
    }

    // sets roomfinder message callback to find rooms
    public void setRoomFinder(RoomFinder roomFinder) {
        this.roomFinder = roomFinder;
    }

    //sets room where creature has stays
    public void setRoom(Room room) {
        this.room = room;
    }

    //returns name abbreviation of creature type
    public String getAbbrv() {
        return abbrv;
    }

    // implementation of default fight behaviour of creatures
    private void fight(Dice dice, List<Adventurer> adventurers) {
        // Fight all adventurers in the room
        // Creating copy to avoid concurrent updates in room and skipping fight concerns
        ArrayList<Adventurer> adventurersCopy = new ArrayList<>(adventurers);
        for (Adventurer adventurer : adventurersCopy) {
            //adventurer roll
            int adventureRolls = adventurer.rollDiceFight(dice);
            //creature roll
            int creatureRolls = this.rollDice(dice);
            if (adventureRolls > creatureRolls) {
                //if adventurer wins die and remove from room
                publisher.submit(new Event.GameObjectCombat(adventurer.getAdventurerName(), "wins"));
                publisher.submit(new Event.GameObjectCombat(getInstanceName(), "loses"));
                die();
                room.removeCreature(this);
                publisher.submit(new Event.GameObjectDefeated(getInstanceName()));
                break;
            } else if (creatureRolls > adventureRolls) {
                //if creature wins, adventurers take damage and get removed from room if they die
                publisher.submit(new Event.GameObjectCombat(getInstanceName(), "wins"));
                publisher.submit(new Event.GameObjectCombat(adventurer.getAdventurerName(), "loses"));
                adventurer.takeDamage();
                if (!adventurer.isAlive()) {
                    room.removeAdventurer(adventurer); // loser gets removed from the room
                    publisher.submit(new Event.GameObjectDefeated(adventurer.getAdventurerName()));
                }
            } else {
                //in case of tie, do nothing
            }
        }
    }
}


