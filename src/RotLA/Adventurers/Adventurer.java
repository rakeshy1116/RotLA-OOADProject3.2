package RotLA.Adventurers;

import RotLA.Creatures.Creature;
import RotLA.Dice;
import RotLA.Room;

import java.util.ArrayList;

abstract public class Adventurer {
    protected int noOfDamages;
    private Room room;

    public int getNoOfDamages() {
        return noOfDamages;
    }

    public void setNoOfDamages(int noOfDamages) {
        this.noOfDamages = noOfDamages;
    }

    private int noOfTreasure;

    public int getNoOfTreasure() {
        return noOfTreasure;
    }

    public void setNoOfTreasure(int noOfTreasure) {
        this.noOfTreasure = noOfTreasure;
    }

    public void performTurn(Dice dice) {
        move();
        Room currentRoom = this.getRoom();
        if(currentRoom.getCreatures().size()>0)
            fight(dice);
        else
            findTreasure(dice);
    }

    public int fightVal(Dice dice) {
        return dice.getRandoms();
    }
    public void fight(Dice dice) {
        ArrayList<Creature> creatures = getRoom().getCreatures();
        for(int i=0;i<creatures.size();i++)
        {
           int advVal = fightVal(dice);
           int creatVal = dice.getRandoms();
           if(advVal>creatVal) {
               creatures.get(i).setAlive(false);
               getRoom().removeCreature(creatures.get(i));
           }
           else {
               this.noOfDamages+=1;
               if(this.noOfDamages==3)
               {
                   getRoom().removeAdventurer(this);
               }
           }
        }
    }

    public void findTreasure(Dice dice) {
        int currentDiceVal=dice.getRandoms();
        if(currentDiceVal>=10)
        setNoOfTreasure(getNoOfTreasure()+1);
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
       this.setRoom(newRoom);
     //  newRoom.addAdventurer();
    }

    public int getRandomInRange(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }



}


