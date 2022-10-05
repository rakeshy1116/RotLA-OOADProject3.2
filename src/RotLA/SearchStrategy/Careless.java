package RotLA.SearchStrategy;

import RotLA.Adventurers.Adventurer;
import RotLA.Dice;

public class Careless extends SearchStrategy {
    int minScore = 10;

    public void search(Adventurer adv, Dice dice, int minScore) {
        super.search(adv, dice, this.minScore);
    }
}
