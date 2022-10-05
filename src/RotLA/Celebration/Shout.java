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

//    public String fight(Dice dice, Creature creature, Adventurer adv, int modifier) {
//        String fightResult = combatStrategy.fight(dice,creature,adv,modifier);
//        if(fightResult.length()>4)
//        {
//            return celebrate(adv);
//        }
//        return fightResult;
//    }

//    public String celebrate(Adventurer adv) {
//
//        return combatStrategy.celebrate(adv)+" shout,";
//    }

    public String fight(Dice dice, Creature creature, Adventurer adv, int modifier) {
        String result = combatStrategy.fight(dice,creature,adv,modifier);
        if(result.length()>4)
            return result+" shout,";
        else
            return "";
    }
}
