package RotLA.Creatures;

import RotLA.GameUtility;
import RotLA.Room;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.ArrayList;

public class Orbiter extends Creature {

    public Orbiter() {
        this.alive = true;
    }

    public void move() {
        //TODO after room
        // orbiter also
        Room oldRoom = this.getRoom();
        if(oldRoom.getAdventurers().size()==0){
            Triplet<Integer, Integer, Integer> roomCoordinates = oldRoom.getRoomId();
            int level = roomCoordinates.getValue0();
            ArrayList<Pair<Integer,Integer>> possibleRooms = new ArrayList<>();
            for(int i=0;i<2;i++)
            {
                for(int j=0;j<2;j++)
                {
                    if(!(i==1 && j==1))
                        possibleRooms.add(new Pair(i,j));
                }
            }
            int value = GameUtility.getRandomInRange(0, possibleRooms.size()-1);
            Pair<Integer,Integer> p = possibleRooms.get(value);
            Room newRoom=getRoomFinder().findRoom(new Triplet<>(level,p.getValue0(),p.getValue1()));
            oldRoom.removeCreature(this);
            newRoom.addCreature(this);
            this.setRoom(newRoom);
        }
    }



}
