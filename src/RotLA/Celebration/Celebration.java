package RotLA.Celebration;

import RotLA.Adventurers.Adventurer;
import RotLA.CombatStrategy.CombatStrategy;
import RotLA.Creatures.Creature;
import RotLA.Dice;

//Pattern - Decorator
public abstract class Celebration extends CombatStrategy {
    CombatStrategy combatStrategy;

    public String fight(Dice dice, Creature creature, Adventurer adv) {
        String fightResult = combatStrategy.fight(dice, creature, adv);
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
