package RotLA.Celebration;

import RotLA.Adventurers.Adventurer;
import RotLA.CombatStrategy.CombatStrategy;
import RotLA.Creatures.Creature;
import RotLA.Dice;

public class Shout extends Celebration {

    public Shout(CombatStrategy strategy) {
        this.combatStrategy=strategy;
    }

    protected String celebrate() {
        return " shout";
    }
}
