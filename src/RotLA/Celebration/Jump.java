package RotLA.Celebration;

import RotLA.CombatStrategy.CombatStrategy;

public class Jump extends Celebration {

    public Jump(CombatStrategy strategy) {
        this.combatStrategy = strategy;
    }

    @Override
    protected String celebrate() {
        return " jump";
    }
}
