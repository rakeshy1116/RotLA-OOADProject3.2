package RotLA.Adventurers;

import RotLA.Dice;

public class Brawler extends Adventurer {

    public Brawler() {
        this.noOfDamages = 0;
        this.abbrv = "B";
        this.adventurerName = "Brawler";
    }

    @Override
    public int rollDiceFight(Dice dice) {
        return 2 + dice.getDiceRoll();
    }

}
