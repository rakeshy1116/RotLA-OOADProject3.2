package RotLA.CombatStrategy;

import RotLA.Adventurers.Adventurer;
import RotLA.Creatures.Creature;
import RotLA.Dice;
import RotLA.Treasures.Armor;
import RotLA.Treasures.Gem;
import RotLA.Treasures.Sword;
import RotLA.Treasures.Treasures;

//Pattern - STRATEGY
public abstract class CombatStrategy {

    int modifier;

    public String fight(Dice dice, Creature creature, Adventurer adv) {
        // ASSUMPTION: Fights creatures in the order of their room entry, i.e order of entry to the list creatures
        //get adventurer's roll
        int adventurerRoll = dice.getDiceRoll() + modifier;
        //get creature's roll
        int creatureRoll = creature.rollDice(dice);
        //check treasures
        for (Treasures treasure : adv.getTreasures()) {
            if (treasure instanceof Sword) {
                adventurerRoll = adventurerRoll + 1;
            } else if (treasure instanceof Gem) {
                creatureRoll = creatureRoll + 1;
            } else if (treasure instanceof Armor) {
                creatureRoll = creatureRoll - 1;
            }
        }
        if (adventurerRoll > creatureRoll) {
            // if adventurer wins, kill creature, remove from room
            creature.die();
            adv.getRoom().removeCreature(creature);
            //System.out.println("Adventurer wins the battle");
            return adv.getAdventurerName();
        } else if (creatureRoll > adventurerRoll) {
            // if creature wins, make adventurer take damage
            adv.takeDamage();
            if (!adv.isAlive()) {
                // if adventurer is dead remove from room and end the fights
                adv.getRoom().removeAdventurer(adv);
            }
            return creature.getName();
        } else {
            // if both roll the same, then nothing happens
            return null;
        }
    }
}
