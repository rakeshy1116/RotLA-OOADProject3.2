package RotLA.CombatStrategy;

import RotLA.Adventurers.Adventurer;
import RotLA.Creatures.Creature;
import RotLA.Dice;

import java.util.List;

public class Stealth extends CombatStrategy {
    public String fight(Dice dice, Creature creature, Adventurer adv, int modifier) {
        return super.fight(dice,creature,adv,modifier);
    }
}
