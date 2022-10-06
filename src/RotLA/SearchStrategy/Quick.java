package RotLA.SearchStrategy;

import RotLA.Adventurers.Adventurer;
import RotLA.Dice;
import RotLA.Events.Event;
import RotLA.GameUtility;

import java.util.concurrent.SubmissionPublisher;

public class Quick extends SearchStrategy {

    public Quick() {
        minScore = 9;
    }

    @Override
    public void search(Adventurer adv, Dice dice, SubmissionPublisher<Event> publisher) {
        int choice = GameUtility.getRandomInRange(1, 3);
        if (choice != 3) {
            super.search(adv, dice, publisher);
        }
    }
}
