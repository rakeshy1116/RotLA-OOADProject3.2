package RotLA.Celebration;

import RotLA.CombatStrategy.CombatStrategy;

public class Spin extends Celebration {

    public Spin(CombatStrategy strategy) {
        this.combatStrategy = strategy;
    }

    @Override
    protected String celebrate() {
        return " spin";
    }
}
