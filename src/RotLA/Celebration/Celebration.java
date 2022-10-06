package RotLA.Celebration;

import RotLA.Adventurers.Adventurer;
import RotLA.CombatStrategy.CombatStrategy;
import RotLA.Creatures.Creature;
import RotLA.Dice;
import RotLA.Events.Event;

import java.util.concurrent.SubmissionPublisher;

// CONCEPT: Decorator pattern
// Celebration is a Decorator class for CombatStrategy, It modifies CombatStrategy's fight method to add celebrate with any change in Combar Strategy
public abstract class Celebration extends CombatStrategy {
    CombatStrategy combatStrategy;

    public String fight(Dice dice, Creature creature, Adventurer adv, SubmissionPublisher<Event> publisher) {
        //call combatStrategy's parent
        String fightResult = combatStrategy.fight(dice, creature, adv, publisher);
        //check who won the fight
        if (fightResult != null && fightResult.contains(adv.getAdventurerName())) {
            if (fightResult.equals(adv.getAdventurerName())) {
                fightResult = fightResult + ":" + celebrate();
            } else {
                fightResult = fightResult + celebrate();
            }
        }
        return fightResult;
    }

    protected abstract String celebrate();
}
