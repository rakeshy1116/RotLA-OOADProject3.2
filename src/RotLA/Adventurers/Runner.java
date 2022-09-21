package RotLA.Adventurers;

import RotLA.Dice;
import RotLA.Room;

public class Runner extends Adventurer {

    public Runner() {
        this.noOfDamages = 0;
        this.abbrv = "R";
        this.adventurerName = "Runner";
    }

    public void performTurn(Dice dice) {
        for (int i = 0; i < 2; i++) {
            move();
            Room currentRoom = this.room;
            if (currentRoom.getCreatures().size() > 0)
                fight(dice);
            else
                findTreasure(dice);

        }
    }

}
