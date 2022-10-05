package test;

import RotLA.Adventurers.Adventurer;
import RotLA.Adventurers.Brawler;
import RotLA.CombatStrategy.Expert;
import RotLA.Creatures.Orbiter;
import RotLA.Room;
import RotLA.SearchStrategy.Careless;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class RoomTest {

    Room room = new Room(1, 1, 1);


    @Test
    void addAdventurer() {
        //testing if addAdventurer methods adds the adventure to rooms list of adventures
        room.addAdventurer(new Brawler(new Expert(), new Careless()));
        assertEquals(1, room.getAdventurers().size());
    }

    @Test
    void addCreature() {
        //testing if addCreature methods adds the creature to rooms list of creatures
        room.addCreature(new Orbiter());
        assertNotEquals(0, room.getCreatures().size());
    }

    @Test
    void removeAdventurer() {
        //testing if removeAdventurer methods removes the adventure from rooms list of adventures;
        room.addAdventurer(new Brawler(new Expert(), new Careless()));
        Adventurer adv = room.getAdventurers().get(0);
        room.removeAdventurer(adv);
    }


}