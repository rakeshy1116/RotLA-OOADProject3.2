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
        int currentDiceVal=dice.getRandoms()+1;
        if(currentDiceVal>=10)
            setNoOfTreasure(getNoOfTreasure()+1);
    }

}
