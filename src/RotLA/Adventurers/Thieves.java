package RotLA.Adventurers;

import RotLA.Dice;
import RotLA.Room;

public class Thieves extends Adventurer {

    public Thieves() {
        this.noOfDamages = 0;
    }

    @Override
    public int fightVal(Dice dice) {
        return 1 + dice.getRandoms();
    }

    @Override
    public void findTreasure(Dice dice) {
        int currentIncrease=dice.getRandoms()+1;
        setNoOfTreasure(getNoOfTreasure()+currentIncrease);
    }

}
