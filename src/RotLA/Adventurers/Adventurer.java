package RotLA.Adventurers;

import RotLA.Creatures.Creature;
import RotLA.Dice;
import RotLA.Room;

import java.util.ArrayList;

abstract public class Adventurer {
    protected int noOfDamages;
    protected Room room;
    protected int noOfTreasure;
    protected String abbrv;
    protected String adventurerName;

    public void takeDamage() {
        noOfDamages++;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getNoOfTreasure() {
        return noOfTreasure;
    }

    public String getAbbrv() {
        return abbrv;
    }

    public boolean isAlive() {
        return noOfDamages < 3;
    }

    public void performTurn(Dice dice) {
        move();
        Room currentRoom = this.room;
        if (!currentRoom.getCreatures().isEmpty())
            fight(dice);
        else
            findTreasure(dice);
    }

    public int rollDiceFight(Dice dice) {
        return dice.getDiceRoll();
    }

    protected int rollDiceTreasure(Dice dice) {
        return dice.getDiceRoll();
    }

    protected void fight(Dice dice) {
        ArrayList<Creature> creatures = this.room.getCreatures();
        for(int i=0;i<creatures.size();i++)
        {
            Creature creature= creatures.get(i);
            int adventurerRoll = rollDiceFight(dice);
            int creatureRoll = creature.rollDice(dice);
            if (adventurerRoll > creatureRoll) {
                creature.die();
                this.room.removeCreature(creature);
            } else if (creatureRoll > adventurerRoll) {
                takeDamage();
                if (!isAlive()) {
                    this.room.removeAdventurer(this);
                }
            } else {
                //do nothing
            }
        };
    }

    protected void findTreasure(Dice dice) {
        int currentDiceVal = rollDiceTreasure(dice);
        if (currentDiceVal >= 10)
            noOfTreasure++;
    }

    protected void move() {
        Room oldRoom = this.room;
        ArrayList<Room> rooms = oldRoom.getConnectedRooms();
        int noOfConnectedRooms = rooms.size();
        int movement = getRandomInRange(0, noOfConnectedRooms - 1);
        Room newRoom = rooms.get(movement);
        oldRoom.removeAdventurer(this);
        newRoom.addAdventurer(this);
        this.room = newRoom;
    }

    public int getRandomInRange(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }

    public String getAdventurerStatus() {
        return adventurerName + " - " + noOfTreasure + " Treasure(s)" + " - " + noOfDamages + " Damage";
    }
}


