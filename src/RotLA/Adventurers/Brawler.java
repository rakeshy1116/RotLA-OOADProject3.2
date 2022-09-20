package RotLA.Adventurers;

import RotLA.Dice;
import RotLA.Room;

public class Brawler extends Adventurer {

    public Brawler() {
        this.noOfDamages = 0;
    }

    @Override
    public int fightVal(Dice dice) {
        return 2 + dice.getRandoms();
    }

}
