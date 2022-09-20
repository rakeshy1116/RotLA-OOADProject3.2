package RotLA.Adventurers;

import RotLA.Dice;
import RotLA.Room;

import java.util.ArrayList;

abstract public class Adventurer {
    protected int noOfDamages;
    private Room room;


    public void performTurn(Dice dice) {
        move();
        Room currentRoom = this.getRoom();
        if(currentRoom.getCreatures().size()>0)
            fight(dice);
        else
            findTreasure(dice);
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


