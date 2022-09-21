package RotLA.Creatures;

import RotLA.Adventurers.Adventurer;
import RotLA.Dice;
import RotLA.Room;
import RotLA.RoomFinder;

import java.util.ArrayList;

public abstract class Creature {

    private Room room;
    protected boolean alive;
    private RoomFinder roomFinder;

    public RoomFinder getRoomFinder() {
        return roomFinder;
    }

    public void setRoomFinder(RoomFinder roomFinder) {
        this.roomFinder = roomFinder;
    }

    abstract void move();

    public void performTurn(Dice dice) {
        move();
        Room currentRoom = this.getRoom();
        if(currentRoom.getAdventurers().size()>0)
            fight(dice);
    }

    public void fight(Dice dice) {

        ArrayList<Adventurer> adventurers = getRoom().getAdventurers();
        for(int i=0;i<adventurers.size();i++)
        {
            int advVal = adventurers.get(i).fightVal(dice);
            int creatVal = dice.getRandoms();
            if(advVal>creatVal) {
                this.setAlive(false);
                getRoom().removeCreature(this);
            }
            else {
                adventurers.get(i).setNoOfDamages(adventurers.get(i).getNoOfDamages()+1);
                if(adventurers.get(i).getNoOfDamages()==3)
                {
                    getRoom().removeAdventurer(adventurers.get(i));
                }
            }
        }

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


