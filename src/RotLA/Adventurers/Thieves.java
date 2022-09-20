package RotLA.Adventurers;

import RotLA.Dice;
import RotLA.Room;

class Thieves extends Adventurer {

    Thieves(Room room, int noOfDamages) {
        super(room, noOfDamages);
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
