package RotLA.SearchStrategy;

import RotLA.Adventurers.Adventurer;
import RotLA.Dice;
import RotLA.Events.Event;
import RotLA.GameUtility;
import RotLA.Room;
import RotLA.Treasures.Portal;
import RotLA.Treasures.Potion;
import RotLA.Treasures.Trap;
import RotLA.Treasures.Treasures;

import java.util.ArrayList;
import java.util.concurrent.SubmissionPublisher;

//CONCEPT: Strategy pattern
//Search strategy is the abstract strategy class that implements search behaviour for the adventurers
//Search strategy has concrete algorithm classes - Careful, Careless, Quick
//Search Strategy is used with delegation in the Adventurer class
public abstract class SearchStrategy {

    protected int minScore;

    public void search(Adventurer adv, Dice dice, SubmissionPublisher<Event> publisher) {
        int diceRoll = dice.getDiceRoll();
        if (diceRoll >= minScore) {
            Room currentRoom = adv.getRoom();
            ArrayList<Treasures> currentTreasures = currentRoom.getTreasures();
            int treasureChoice = GameUtility.getRandomInRange(0, currentTreasures.size() - 1);
            Treasures currentTreasure = currentTreasures.get(treasureChoice);
            if (!adv.hasTreasure(currentTreasure))
                collectTreasure(adv, currentRoom, currentTreasure, publisher);
        }
    }

    public void collectTreasure(Adventurer adv, Room room, Treasures currentTreasure, SubmissionPublisher<Event> publisher) {
        room.removeTreasure(currentTreasure);
        if (currentTreasure instanceof Trap) {
            if (this instanceof Careful) {
                int chance = GameUtility.getRandomInRange(0, 1);
                if (chance == 1) {
                    adv.takeDamage();
                }
            } else {
                adv.takeDamage();
            }
        } else {
            adv.addTreasure(currentTreasure);
            publisher.submit(new Event.AdventurerFindsTreasure(adv.getAdventurerName(), currentTreasure.getName()));
            if (currentTreasure instanceof Potion) {
                adv.setMaxDamages(adv.getMaxDamages() + 1);
            }
        }
    }
}
