package RotLA.SearchStrategy;

import RotLA.Adventurers.Adventurer;
import RotLA.Dice;
import RotLA.GameUtility;
import RotLA.Room;
import RotLA.Treasures.*;

import java.util.ArrayList;
import java.util.List;

public abstract class SearchStrategy {
    public void search(Adventurer adv, Dice dice,int minScore) {
        int diceRoll = dice.getDiceRoll();
        if (diceRoll >= minScore) {
            Room currentRoom = adv.getRoom();
            ArrayList<Treasures> currentTreasures = currentRoom.getTreasures();
            int treasureChoice = GameUtility.getRandomInRange(0, currentTreasures.size() - 1);
            Treasures currentTreasure = currentTreasures.get(treasureChoice);
            boolean hasSameTreasure = false;
            if(!adv.checkTreasure(currentTreasure))
                common(adv,currentRoom,currentTreasure);
            }



    }
    public void common(Adventurer adv, Room room, Treasures currentTreasure) {
        ArrayList<Treasures> currentTreasures = room.getTreasures();

        for(int j=0;j<currentTreasures.size();j++)
        {
            if(currentTreasures.get(j).equals(currentTreasure))
            {
                currentTreasures.remove(j);
                break;
            }
        }
        if(currentTreasure instanceof Trap) {
            if (this instanceof Careful) {
                int chance = GameUtility.getRandomInRange(0, 1);
                if (chance == 1) {
                    adv.takeDamage();
                }
            } else {
                adv.takeDamage();
            }
        }
        else
        {
            adv.addTreasure(currentTreasure);
            if(currentTreasure instanceof Potion)
            {
                adv.setMaxDamages(adv.getMaxDamages()+1);
            }
        }
    }


}
