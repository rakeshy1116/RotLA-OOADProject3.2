package RotLA.SearchStrategy;

import RotLA.Adventurers.Adventurer;
import RotLA.Dice;
import RotLA.GameUtility;

public class Quick extends SearchStrategy {
    int minScore = 9;

    public void search(Adventurer adv, Dice dice, int minScore) {
        int choice = GameUtility.getRandomInRange(1, 3);
        if (choice != 3) {
            super.search(adv, dice, this.minScore);
        }
    }
}
