package RotLA.Creatures;

import RotLA.Room;

public class Orbiter extends Creature {

    public Orbiter() {
        this.alive = true;
    }

    public void move() {
        //TODO after room
        // orbiter also
        Room oldRoom = this.getRoom();
        if(oldRoom.getAdventurers().size()==0){

        }
    }

}
