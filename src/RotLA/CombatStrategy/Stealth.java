package RotLA.CombatStrategy;

import RotLA.Adventurers.Adventurer;
import RotLA.Creatures.Creature;
import RotLA.Dice;
import RotLA.GameUtility;

public class Stealth extends CombatStrategy {

    public Stealth() {
        modifier = 0;
    }

    @Override
    public String fight(Dice dice, Creature creature, Adventurer adv) {
        if (GameUtility.getRandomInRange(0, 1) == 1) {
            return super.fight(dice, creature, adv);
        } else {
            return null;
        }
    }
}
