package RotLA.Adventurers;

import RotLA.CombatStrategy.CombatStrategy;
import RotLA.Dice;
import RotLA.SearchStrategy.SearchStrategy;

// CONCEPT: INHERITANCE - A type of Adventurer that inherits variables and behaviour from Adventurer
public class Brawler extends Adventurer {

    public Brawler(CombatStrategy combatStrategy, SearchStrategy searchStrategy) {
        super(combatStrategy,searchStrategy);
        this.noOfDamages = 0;
        this.abbrv = "B";
        this.adventurerName = "Brawler";

    }

    // Overrides rollDiceFight method to enforce Brawler's special power during fights, +2 to dice rolls
    @Override
    public int rollDiceFight(Dice dice) {
        return 2 + dice.getDiceRoll();
    }

}
