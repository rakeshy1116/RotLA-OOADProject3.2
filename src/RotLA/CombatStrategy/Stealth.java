package RotLA.CombatStrategy;

import RotLA.Adventurers.Adventurer;
import RotLA.Creatures.Creature;
import RotLA.Dice;
import RotLA.Events.Event;
import RotLA.GameUtility;

import java.util.concurrent.SubmissionPublisher;

public class Stealth extends CombatStrategy {

    public Stealth() {
        modifier = 0;
    }

    @Override
    public String fight(Dice dice, Creature creature, Adventurer adv, SubmissionPublisher<Event> publisher) {
        if (GameUtility.getRandomInRange(0, 1) == 1) {
            return super.fight(dice, creature, adv, publisher);
        } else {
            return null;
        }
    }
}
