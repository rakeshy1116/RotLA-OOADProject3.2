package RotLA.Celebration;

import RotLA.CombatStrategy.CombatStrategy;

public class Shout extends Celebration {

    public Shout(CombatStrategy strategy) {
        this.combatStrategy = strategy;
    }

    protected String celebrate() {
        return " shout";
    }
}
