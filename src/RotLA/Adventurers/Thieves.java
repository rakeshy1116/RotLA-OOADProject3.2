package RotLA.Adventurers;

import RotLA.Dice;
import RotLA.Room;

public class Thieves extends Adventurer {

    public Thieves() {

    }

    @Override
    public int fight(Dice dice) {
        return 1 + dice.getRandoms();
    }

    @Override
    public int findTreasure(Dice dice) {
        return 1 + dice.getRandoms();
    }

}
