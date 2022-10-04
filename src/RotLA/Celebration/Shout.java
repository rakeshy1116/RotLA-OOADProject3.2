package RotLA.Celebration;

import RotLA.Adventurers.Adventurer;
import RotLA.CombatStrategy.CombatStrategy;
import RotLA.Creatures.Creature;
import RotLA.Dice;

public class Shout extends Celebration {

    CombatStrategy combatStrategy;

    public Shout(CombatStrategy strategy) {
        this.combatStrategy=strategy;
    }

    public boolean fight(Dice dice, Creature creature, Adventurer adv, int modifier) {
        boolean fightResult = combatStrategy.fight(dice,creature,adv,modifier);
        if(fightResult)
        {
            celebrate(adv);
        }
        return fightResult;
    }

    public String celebrate(Adventurer adv) {

        return combatStrategy.celebrate(adv)+" shout,";
    }
}
