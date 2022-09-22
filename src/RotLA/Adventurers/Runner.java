package RotLA.Adventurers;

import RotLA.Creatures.Creature;
import RotLA.Dice;
import RotLA.Room;

import java.util.List;

// CONCEPT: INHERITANCE - A type of Adventurer that inherits variables and behaviour from Adventurer
public class Runner extends Adventurer {

    public Runner() {
        this.noOfDamages = 0;
        this.abbrv = "R";
        this.adventurerName = "Runner";
    }

    // Overrides performTurn method to enforce Runner's special ability to perform 2 turns in a single game turn
    public void performTurn(Dice dice) {
        for (int i = 0; i < 2; i++) {
            if (this.isAlive()) {
                super.performTurn(dice);
//                move();
//                List<Creature> creaturesInRoom = room.getCreatures();
//                //After moving if the room has any creatures, fight them other wise find treasure
//                if (!creaturesInRoom.isEmpty())
//                    fight(dice, creaturesInRoom);
//                else
//                    findTreasure(dice);
            }
        }
    }

}
