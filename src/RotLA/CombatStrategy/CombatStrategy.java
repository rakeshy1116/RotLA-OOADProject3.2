package RotLA.CombatStrategy;

import RotLA.Adventurers.Adventurer;
import RotLA.Creatures.Creature;
import RotLA.Dice;
import RotLA.Events.Event;
import RotLA.Treasures.Armor;
import RotLA.Treasures.Gem;
import RotLA.Treasures.Sword;
import RotLA.Treasures.Treasures;

import java.util.concurrent.SubmissionPublisher;

//CONCEPT: Strategy pattern
//Combat strategy is the abstract strategy class that implements fight behaviour for the adventurers
//Combat strategy has concrete algorithm classes - Expert, Stealth, Trained, Untrained
//Combat Strategy is used with delegation in the Adventurer class
public abstract class CombatStrategy {

    int modifier;

    public String fight(Dice dice, Creature creature, Adventurer adv, SubmissionPublisher<Event> publisher) {
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
            publisher.submit(new Event.GameObjectCombat(adv.getAdventurerName(), "wins"));
            publisher.submit(new Event.GameObjectCombat(creature.getInstanceName(), "loses"));
            creature.die();
            adv.getRoom().removeCreature(creature);
            publisher.submit(new Event.GameObjectDefeated(creature.getInstanceName()));
            return adv.getAdventurerName();
        } else if (creatureRoll > adventurerRoll) {
            // if creature wins, make adventurer take damage
            publisher.submit(new Event.GameObjectCombat(creature.getInstanceName(), "wins"));
            publisher.submit(new Event.GameObjectCombat(adv.getAdventurerName(), "loses"));
            adv.takeDamage();
            if (!adv.isAlive()) {
                // if adventurer is dead remove from room and end the fights
                adv.getRoom().removeAdventurer(adv);
                publisher.submit(new Event.GameObjectDefeated(adv.getAdventurerName()));
            }
            return creature.getName();
        } else {
            // if both roll the same, then nothing happens
            return null;
        }
    }
}
