package RotLA.Celebration;

import RotLA.Adventurers.Adventurer;
import RotLA.BoardRenderer;
import RotLA.CombatStrategy.CombatStrategy;
import RotLA.Creatures.Creature;
import RotLA.Dice;

public class Dance extends Celebration{

    public Dance(CombatStrategy strategy) {
        this.combatStrategy=strategy;
    }

   @Override
   protected String celebrate() {
        return " dance";
    }
}
