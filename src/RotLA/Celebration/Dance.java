package RotLA.Celebration;

import RotLA.CombatStrategy.CombatStrategy;

public class Dance extends Celebration {

    public Dance(CombatStrategy strategy) {
        this.combatStrategy = strategy;
    }

    @Override
    protected String celebrate() {
        return " dance";
    }
}
