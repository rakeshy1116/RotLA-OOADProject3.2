package RotLA.Adventurers;

import RotLA.Dice;
import RotLA.Room;

public class Brawler extends Adventurer {

    Brawler(Room room, int noOfDamages) {
        super(room, noOfDamages);
    }

    @Override
    public int fight(Dice dice) {
        return 2 + dice.getRandoms();
    }

}
