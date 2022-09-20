package RotLA.Adventurers;

import RotLA.Dice;
import RotLA.Room;

public class Runner extends Adventurer {

    public Runner( ) {
        this.noOfDamages = 0;
    }

    public void performTurn(Dice dice) {
        for(int i=0;i<2;i++)
        {
            move();
            Room currentRoom = getRoom();
            if(currentRoom.getCreatures().size()>0)
                fight(dice);
            else
                findTreasure(dice);

        }
    }

}
