package test;

import RotLA.Adventurers.Adventurer;
import RotLA.Adventurers.Brawler;
import RotLA.CombatStrategy.Expert;
import RotLA.Creatures.Creature;
import RotLA.Creatures.Orbiter;
import RotLA.Events.Event;
import RotLA.Room;
import RotLA.SearchStrategy.Careless;
import org.junit.jupiter.api.Test;

import java.util.concurrent.SubmissionPublisher;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    Room room = new Room(1, 1, 1);

    @Test
    void addAdventurer() {
        //testing if addAdventurer methods adds the adventure to rooms list of adventures
        room.addAdventurer(new Brawler(new Expert(), new Careless(), new SubmissionPublisher<Event>()));
        assertEquals(1, room.getAdventurers().size());
    }

    @Test
    void addCreature() {
        //testing if addCreature methods adds the creature to rooms list of creatures
        room.addCreature(new Orbiter(new SubmissionPublisher<Event>()));
        assertNotEquals(0, room.getCreatures().size());
    }

    @Test
    void removeAdventurer() {
        //testing if removeAdventurer methods removes the adventure from rooms list of adventures;
        room.addAdventurer(new Brawler(new Expert(), new Careless(), new SubmissionPublisher<Event>()));
        Adventurer adv = room.getAdventurers().get(0);
        room.removeAdventurer(adv);
        assertFalse(room.getAdventurers().contains(adv));
    }

    @Test
    void removeCreatureTest() {
        //testing if removeCreature method removes creature from rooms creature list
        room.addCreature(new Orbiter(new SubmissionPublisher<>()));
        Creature crea = room.getCreatures().get(0);
        room.removeCreature(crea);
        assertFalse(room.getCreatures().contains(crea));
    }


}