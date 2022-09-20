package RotLA.Adventurers;

import RotLA.Dice;
import RotLA.Room;

public class Brawler extends Adventurer {

    public Brawler() {
        super();
    }

    @Override
    public int fight(Dice dice) {
        return 2 + dice.getRandoms();
    }

}
