package RotLA.Adventurers;

import RotLA.CombatStrategy.CombatStrategy;
import RotLA.Dice;
import RotLA.Events.Event;
import RotLA.SearchStrategy.SearchStrategy;

import java.util.ArrayList;
import java.util.concurrent.SubmissionPublisher;

// CONCEPT: INHERITANCE - A type of Adventurer that inherits variables and behaviour from Adventurer
public class Brawler extends Adventurer {

    public Brawler(CombatStrategy combatStrategy, SearchStrategy searchStrategy, SubmissionPublisher<Event> publisher) {
        this.combatStrategy = combatStrategy;
        this.searchStrategy = searchStrategy;
        maxDamages = 3;
        this.publisher = publisher;
        treasures = new ArrayList<>();
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
