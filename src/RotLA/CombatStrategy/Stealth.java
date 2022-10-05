package RotLA.CombatStrategy;

import RotLA.Adventurers.Adventurer;
import RotLA.Creatures.Creature;
import RotLA.Dice;
import RotLA.GameUtility;

import java.util.List;

public class Stealth extends CombatStrategy {
    public String fight(Dice dice, Creature creature, Adventurer adv, int modifier) {
        int chance= GameUtility.getRandomInRange(0,1);
        if(chance==1)
        return super.fight(dice,creature,adv,modifier);
        else
            return "";
    }
}
