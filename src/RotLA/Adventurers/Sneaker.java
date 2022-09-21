package RotLA.Adventurers;

import RotLA.Dice;
import RotLA.Room;

public class Sneaker extends Adventurer {

    public Sneaker() {
        this.noOfDamages = 0;
        this.abbrv = "S";
        this.adventurerName = "Sneaker";
    }

    public void performTurn(Dice dice) {
        move();
        Room currentRoom = this.room;
        if (!currentRoom.getCreatures().isEmpty()) {
            int min = 0;
            int max = 1;
            int chance = (int) Math.floor(Math.random() * (max - min + 1) + min);
            if (chance == 1) fight(dice);
        } else
            findTreasure(dice);
    }

}
