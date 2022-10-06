package RotLA.Adventurers;

import RotLA.CombatStrategy.CombatStrategy;
import RotLA.Dice;
import RotLA.Events.Event;
import RotLA.SearchStrategy.SearchStrategy;

import java.util.ArrayList;
import java.util.concurrent.SubmissionPublisher;

// CONCEPT: INHERITANCE - A type of Adventurer that inherits variables and behaviour from Adventurer
public class Thief extends Adventurer {

    public Thief(CombatStrategy combatStrategy, SearchStrategy searchStrategy, SubmissionPublisher<Event> publisher) {
        this.combatStrategy = combatStrategy;
        this.searchStrategy = searchStrategy;
        maxDamages = 3;
        this.publisher = publisher;
        treasures = new ArrayList<>();
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
