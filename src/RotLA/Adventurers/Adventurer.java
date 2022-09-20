package RotLA.Adventurers;

import RotLA.Dice;
import RotLA.Room;

import java.util.ArrayList;

abstract public class Adventurer {
    private int noOfDamages;
    private Room room;

    Adventurer(Room room, int noOfDamages) {
        this.room = room;
        this.noOfDamages = noOfDamages;
    }

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

    public int fight(Dice dice) {
        return dice.getRandoms();
    }

    public int findTreasure(Dice dice) {
        return dice.getRandoms();
    }

    public boolean isAlive() {
        return noOfDamages < 3;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void move() {
        // TODO will add the method after room is added

        Room oldRoom = this.room;
       ArrayList<Room> rooms= room.getConnectedRooms();
       int noOfConnectedRooms = rooms.size();
       int movement = getRandomInRange(0,noOfConnectedRooms-1);
       Room newRoom = rooms.get(movement);
       oldRoom.removeAdventurer(this);
       newRoom.addAdventurer(this);
     //  newRoom.addAdventurer();
    }

    public int getRandomInRange(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }



}


