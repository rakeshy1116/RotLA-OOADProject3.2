package RotLA.CombatStrategy;

import RotLA.Adventurers.Adventurer;
import RotLA.Creatures.Creature;
import RotLA.Dice;

import java.util.List;

public class Untrained extends CombatStrategy {
    int modifier=0;
    public String fight(Dice dice, Creature creature, Adventurer adv, int modifier) {
        return super.fight(dice,creature,adv,this.modifier);
    }
}
