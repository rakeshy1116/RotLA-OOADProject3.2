package RotLA;

import org.javatuples.Triplet;

// A functional interface that serves as a messenger/callback for classes that needs to find a room based on coordinates
public interface RoomFinder {
    public Room findRoom(Triplet<Integer, Integer, Integer> roomID);
}
