package RotLA.Adventurers;

import RotLA.CombatStrategy.CombatStrategy;
import RotLA.Dice;
import RotLA.SearchStrategy.SearchStrategy;

import java.util.ArrayList;

// CONCEPT: INHERITANCE - A type of Adventurer that inherits variables and behaviour from Adventurer
public class Runner extends Adventurer {

    public Runner(CombatStrategy combatStrategy, SearchStrategy searchStrategy) {
        this.combatStrategy = combatStrategy;
        this.searchStrategy = searchStrategy;
        maxDamages = 3;
        treasures = new ArrayList<>();
        this.noOfDamages = 0;
        this.abbrv = "R";
        this.adventurerName = "Runner";
    }

    // Overrides performTurn method to enforce Runner's special ability to perform 2 turns in a single game turn
    public void performTurn(Dice dice) {
        for (int i = 0; i < 2; i++) {
            if (this.isAlive()) {
                super.performTurn(dice);
            }
        }
    }

}
