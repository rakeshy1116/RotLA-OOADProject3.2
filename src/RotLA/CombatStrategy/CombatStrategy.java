package RotLA.CombatStrategy;

import RotLA.Adventurers.Adventurer;
import RotLA.Creatures.Creature;
import RotLA.Dice;
import RotLA.Treasures.Armor;
import RotLA.Treasures.Gem;
import RotLA.Treasures.Sword;
import RotLA.Treasures.Treasures;

import java.util.List;

public abstract class CombatStrategy {
    int modifier;
    public String fight(Dice dice,Creature creature, Adventurer adv,int modifier) {
        // ASSUMPTION: Fights creatures in the order of their room entry, i.e order of entry to the list creatures
        //get adventurer's roll
        int changeAdvRoll=0;
        int changeCreRoll=0;
//        List<Treasures> treasures=adv.getTreasures();
//        for(Treasures treasure : treasures) {
//            if(treasure instanceof Sword) { //Sword provides the Adventurer holding it with a +1 bonus to all combat rolls against Creatures
//                changeAdvRoll+=1;
//            }
//            else if(treasure instanceof Gem) {
//                changeCreRoll+=1;
//            }
//            else if(treasure instanceof Armor) {
//                changeCreRoll-=1;
//            }
//            else {
//
//            }
//        }
        int adventurerRoll = dice.getDiceRoll()+modifier + changeAdvRoll;
        //get creature's roll
        int creatureRoll = creature.rollDice(dice) + changeCreRoll;
        if (adventurerRoll > creatureRoll) {
            // if adventurer wins, kill creature, remove from room
            creature.die();
            adv.getRoom().removeCreature(creature);
            System.out.println("Adventurer wins the battle");
            return celebrate(adv);
        } else if (creatureRoll > adventurerRoll) {
            // if creature wins, make adventurer take damage
            adv.takeDamage();
            if (!adv.isAlive()) {
                // if adventurer is dead remove from room and end the fights
                adv.getRoom().removeAdventurer(adv);
            }
            return "lost";
        } else {
            // if both roll the same, then nothing happens

        }
        return celebrate(adv);

    }

    public String celebrate(Adventurer adv) {

        return adv.getAdventurerName() + " celebrate: ";
    }
}
