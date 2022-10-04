package RotLA.CombatStrategy;

import RotLA.Adventurers.Adventurer;
import RotLA.Creatures.Creature;
import RotLA.Dice;

import java.util.ArrayList;
import java.util.List;

public class Expert extends CombatStrategy {
    int modifier=2;
    public boolean fight(Dice dice,Creature creature, Adventurer adv,int modifier) {
        return super.fight(dice,creature,adv,modifier);
    }
}
