package RotLA;

import org.javatuples.Triplet;

public interface RoomFinder {

    public Room findRoom(Triplet<Integer, Integer, Integer> roomID);
}
