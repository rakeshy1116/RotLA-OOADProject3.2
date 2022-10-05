package RotLA.SearchStrategy;

import RotLA.Adventurers.Adventurer;
import RotLA.Dice;
import RotLA.GameUtility;
import RotLA.Room;
import RotLA.Treasures.*;

import java.util.List;

public abstract class SearchStrategy {
    public void search(Adventurer adv, Dice dice,int minScore) {
        int diceRoll =dice.getDiceRoll();
        if(diceRoll>=minScore) {
            Room currentRoom = adv.getRoom();
            List<Treasures> currentTreasures= currentRoom.getTreasures();
            int treasureChoice = GameUtility.getRandomInRange(0,currentTreasures.size()-1);
            Treasures currentTreasure = currentTreasures.get(treasureChoice);

            //check if adv already has that type of treasure
            for(Treasures treasure: adv.getTreasures())
            {
                if(treasure instanceof Sword && currentTreasure instanceof Sword)
                {
                    break;
                }
                else if(treasure instanceof Armor && currentTreasure instanceof Armor)
                {
                    break;
                }
                else if(treasure instanceof Gem && currentTreasure instanceof Gem)
                {
                    break;
                }
                else if(treasure instanceof Portal && currentTreasure instanceof Portal)
                {
                    break;
                }
                else
                {
                    adv.addTreasure(currentTreasure);
                    for(int i=0;i<currentTreasures.size();i++)
                    {
                        if(currentTreasures.get(i).equals(currentTreasure))
                        {
                            currentTreasures.remove(i);
                        }
                    }
                    if(currentTreasure instanceof Potion)
                    {
                        adv.setMaxDamages(adv.getMaxDamages()+1);
                    }
                    if(currentTreasure instanceof Trap) {
                        if(this instanceof Careful)
                        {
                            int chance = GameUtility.getRandomInRange(0,1);
                            if(chance==1)
                            {
                                adv.takeDamage();
                            }
                        }
                        else
                        {
                            adv.takeDamage();
                        }

                    }
                }
            }




        }

    }
}
