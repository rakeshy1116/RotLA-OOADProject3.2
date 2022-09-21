package RotLA.Creatures;

import RotLA.Adventurers.Adventurer;
import RotLA.Dice;
import RotLA.Room;
import RotLA.RoomFinder;

import java.util.ArrayList;

public abstract class Creature {

    protected Room room;
    protected boolean alive;
    protected RoomFinder roomFinder;
    protected String abbrv;

    public void setRoomFinder(RoomFinder roomFinder) {
        this.roomFinder = roomFinder;
    }

    abstract void move();

    public void performTurn(Dice dice) {
        move();
        Room currentRoom = this.room;
        if (!currentRoom.getAdventurers().isEmpty())
            fight(dice);
    }

    public int rollDice(Dice dice) {
        return dice.getDiceRoll();
    }

    public void fight(Dice dice) {
        ArrayList<Adventurer> adventurers = room.getAdventurers();
        adventurers.forEach(adventurer -> {
            int adventureRolls = adventurer.rollDiceFight(dice);
            int creatureRolls = this.rollDice(dice);
            if (adventureRolls > creatureRolls) {
                this.alive = false;
            } else if (creatureRolls > adventureRolls) {
                adventurer.takeDamage();
                if (!adventurer.isAlive()) {
                    room.removeAdventurer(adventurer);
                }
            } else {
                //do nothing
            }
        });
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public boolean isAlive() {
        return alive;
    }

    public void die() {
        this.alive = false;
    }

    public String getAbbrv() {
        return abbrv;
    }
}


