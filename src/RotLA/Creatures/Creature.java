package RotLA.Creatures;

import RotLA.Dice;
import RotLA.Room;

public abstract class Creature {

    private Room room;
    protected boolean alive;

    abstract void move();

    public void performTurn(Dice dice) {
        move();
        Room currentRoom = this.getRoom();
        if(currentRoom.getAdventurers().size()>0)
            fight(dice);
    }

    public int fight(Dice dice) {
        return dice.getRandoms();
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}


