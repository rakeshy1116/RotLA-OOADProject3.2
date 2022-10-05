package RotLA.CombatStrategy;

import RotLA.Adventurers.Adventurer;
import RotLA.Creatures.Creature;
import RotLA.Dice;

import java.util.List;

public class Trained extends CombatStrategy {
    int modifier=1;
    public String fight(Dice dice, Creature creature, Adventurer adv, int modifier) {
        return super.fight(dice,creature,adv,this.modifier);
    }
}
