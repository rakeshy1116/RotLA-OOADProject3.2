package RotLA.Adventurers;

import RotLA.Dice;

public class Thief extends Adventurer {

    public Thief() {
        this.noOfDamages = 0;
        this.abbrv = "T";
        this.adventurerName = "Thief";
    }

    @Override
    public int rollDiceFight(Dice dice) {
        return 1 + dice.getDiceRoll();
    }

    @Override
    public int rollDiceTreasure(Dice dice) {
        return 1 + dice.getDiceRoll();
    }

}
