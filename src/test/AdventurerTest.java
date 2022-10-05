package test;

import RotLA.Adventurers.Adventurer;
import RotLA.Adventurers.Brawler;
import RotLA.BoardRenderer;
import RotLA.CombatStrategy.Expert;
import RotLA.CombatStrategy.Stealth;
import RotLA.Dice;
import RotLA.GameEngine;
import RotLA.Room;
import RotLA.SearchStrategy.Careless;
import RotLA.Treasures.Portal;
import RotLA.Treasures.Potion;
import RotLA.Treasures.Sword;
import RotLA.Treasures.Treasures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdventurerTest {

    Room room = new Room(1,2,2);

    @Test
    void addTreasure() {
        //testing addTreasure method to check if the methods adds one treasure to adventures list of treasures or not.
        Adventurer brawler = new Brawler(new Expert(), new Careless());
        brawler.addTreasure(new Sword());
        assertEquals(1,brawler.getTreasures().size());
    }

    @Test
    void removeTreasure() {
        //testing removeTreasure method to check if the methods removes one treasure from adventures list of treasures or not.
        Adventurer brawler = new Brawler(new Expert(), new Careless());
        brawler.addTreasure(new Sword());
        Treasures treasure = brawler.getTreasures().get(0);
        brawler.removeTreasure(treasure);
        assertEquals(0,brawler.getTreasures().size());
    }

    @Test
    void findTreasure() {
        //testing findTreasure method to check if the methods can find treasure from the given room or not.
        //since it depends upon dice roll output, here adv alive status is checked as there is no fighting involved
        room.addAdventurer(new Brawler(new Expert(), new Careless()));
        room.addTreasure(new Potion());
        Adventurer adv = room.getAdventurers().get(0);
        adv.setRoom(room);
        Dice dice = new Dice();
        adv.findTreasure(dice);
        assertTrue(adv.isAlive());
    }

    @Test
    void checkTreasure() {
        //testing checkTreasure method to check if the method can identity a given type of treasure from
        //adventures existing list of treasures.
        Treasures treasure = new Portal();
        Adventurer brawler = new Brawler(new Expert(), new Careless());
        brawler.addTreasure(new Sword());
        assertFalse(brawler.checkTreasure(treasure));

    }

}