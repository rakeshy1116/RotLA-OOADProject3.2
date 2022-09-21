package RotLA;

import RotLA.Adventurers.*;
import RotLA.Creatures.Blinker;
import RotLA.Creatures.Creature;
import RotLA.Creatures.Orbiter;
import RotLA.Creatures.Seeker;

import java.util.ArrayList;

public class GameEngine {

    private BoardRenderer boardRenderer;
    private ArrayList<Adventurer> adventurers;
    private ArrayList<Creature> creatures;
    private Dice dice;

    //initialize()
    //startSimulation()
    //checkTermination()

    public void initialize() {
        this.adventurers = new ArrayList<Adventurer>();
        adventurers.add(new Brawler());
        adventurers.add(new Runner());
        adventurers.add(new Thieves());
        adventurers.add(new Sneaker());

        this.creatures = new ArrayList<Creature>();
        for(int i=0;i<4;i++)
        {
            creatures.add(new Blinker());
            creatures.add(new Orbiter());
            creatures.add(new Seeker());
        }
        this.boardRenderer = new BoardRenderer(adventurers,creatures);
        this.dice = new Dice();

    }

//    public void startSimulation() {
//        while(checkTermination()) {
//              // pass boardList as argument while calling creatures move method
                //check alive status before calling performTurn
//        }
//    }
//
    public boolean checkTermination() {
        boolean allAdvDied=false;
        boolean allCreatDied=false;
        int totalTreasure=0;
        for(int i=0;i<adventurers.size();i++)
        {
            totalTreasure += adventurers.get(i).getNoOfTreasure();
        }

        for(int i=0;i<adventurers.size();i++)
        {
            if(!adventurers.get(i).isAlive())
            {
                allAdvDied=true;
            }
            else {
                allAdvDied=false;
                break;
            }
        }

        for(int i=0;i<creatures.size();i++)
        {
            if(!creatures.get(i).isAlive())
            {
                allCreatDied=true;
            }
            else {
                allCreatDied=false;
                break;
            }
        }
        return totalTreasure>=10 || allAdvDied || allCreatDied;

    }
//    public static void main  (String args[]) {
//
//        System.out.println("Game Engine");
//    }
}
