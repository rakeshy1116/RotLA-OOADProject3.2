package RotLA.Adventurers;

import RotLA.CombatStrategy.CombatStrategy;
import RotLA.Creatures.Creature;
import RotLA.Dice;
import RotLA.Events.Event;
import RotLA.GameUtility;
import RotLA.SearchStrategy.SearchStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SubmissionPublisher;

// CONCEPT INHERITANCE - Each of these subclasses inherit several instance variable and method implementations pertaining to adventurer behaviour
public class Sneaker extends Adventurer {

    public Sneaker(CombatStrategy combatStrategy, SearchStrategy searchStrategy, SubmissionPublisher<Event> publisher) {
        this.combatStrategy = combatStrategy;
        this.searchStrategy = searchStrategy;
        maxDamages = 3;
        this.publisher = publisher;
        treasures = new ArrayList<>();
        this.abbrv = "S";
        this.adventurerName = "Sneaker";
    }

    // Overrides perform turn to enforce Sneaker's special power, 50% chance to avoid fight
    //ASSUMPTION: Sneaker's power activates once per fight i.e in a turn he will either fight all creatures or avoid all creatures
    public void performTurn(Dice dice) {
        move();
        List<Creature> creaturesInRoom = this.room.getCreatures();
        if (!creaturesInRoom.isEmpty()) {
            //roll a random number between 0,1(50% chance), fight if 1 is rolled
            if (GameUtility.getRandomInRange(0, 1) == 1) {
                fight(dice, creaturesInRoom);
            }
        } else
            findTreasure(dice);
    }

}
