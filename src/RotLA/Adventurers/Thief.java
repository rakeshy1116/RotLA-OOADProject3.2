package RotLA.Adventurers;

import RotLA.CombatStrategy.CombatStrategy;
import RotLA.Dice;
import RotLA.SearchStrategy.SearchStrategy;

// CONCEPT: INHERITANCE - A type of Adventurer that inherits variables and behaviour from Adventurer
public class Thief extends Adventurer {

    public Thief(CombatStrategy combatStrategy, SearchStrategy searchStrategy) {
        super(combatStrategy,searchStrategy);
        this.noOfDamages = 0;
        this.abbrv = "T";
        this.adventurerName = "Thief";
    }

    // Overrides rollDiceFight to enforce Thief's ability during fights, +1 to rolls
    @Override
    public int rollDiceFight(Dice dice) {
        return 1 + dice.getDiceRoll();
    }

    // Overrides rollDiceFight to enforce Thief's ability during finding treasures, +1 to rolls
    @Override
    public int rollDiceTreasure(Dice dice) {
        return 1 + dice.getDiceRoll();
    }

}
