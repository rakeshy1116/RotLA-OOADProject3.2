package test;

import RotLA.BoardRenderer;
import RotLA.Room;
import org.javatuples.Triplet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardRendererTest {

    BoardRenderer boardRenderer = new BoardRenderer();
    @Test
    void getNeighbours() {
        //testing the no of neighbours of 0-1-1, which is 1 (1-1-1) room
        Room room = new Room(0,1,1);
        boardRenderer.getNeighbours(room);
        assertEquals(1,room.getConnectedRooms().size());
    }

    @Test
    void findRoom() {
        //testing if findRoom can find 0-0-0 room which is not a valid room
        assertNull(boardRenderer.findRoom(new Triplet<>(0,0,0)));

    }
}