package RotLA.Adventurers;

import RotLA.Dice;
import RotLA.Room;

abstract public class Adventurer {
    private int noOfDamages;
    private Room room;

    public void performTurn(Dice dice) {
        move();
        //TODO override this in sneaker
        if (getClass() == Sneaker.class) {
            int min = 0;
            int max = 1;
            int chance = (int) Math.floor(Math.random() * (max - min + 1) + min);
            if (chance == 1) fight(dice);
        } else {
            fight(dice);
        }
        findTreasure(dice);
        //TODO creature check to be added after room class created
    }

    public Room getRoom() {
        return room;
    }

    public boolean isAlive() {
        return noOfDamages < 3;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    Adventurer(Room room, int noOfDamages) {
        this.room = room;
        this.noOfDamages = noOfDamages;
    }

    public void move() {
        // TODO will add the method after room is added
    }

    public int fight(Dice dice) {
        return dice.getRandoms();
    }

    public int findTreasure(Dice dice) {
        return dice.getRandoms();
    }
}


