package RotLA;

import RotLA.Adventurers.*;
import RotLA.Creatures.Creature;

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
        this.boardRenderer = new BoardRenderer(adventurers,creatures);


        this.dice = new Dice();

    }

//    public static void main  (String args[]) {
//
//        System.out.println("Game Engine");
//    }
}
