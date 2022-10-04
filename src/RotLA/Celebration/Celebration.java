package RotLA.Celebration;

import RotLA.CombatStrategy.CombatStrategy;

public class Celebration extends CombatStrategy {
    CombatStrategy combatWrapper;

    public void setCombatWrapper(CombatStrategy combatWrapper) {
        this.combatWrapper = combatWrapper;
    }
}
