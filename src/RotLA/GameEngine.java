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

    public void initialize() {
        this.adventurers = new ArrayList<Adventurer>();
        adventurers.add(new Brawler());
        adventurers.add(new Runner());
        adventurers.add(new Thief());
        adventurers.add(new Sneaker());

        this.creatures = new ArrayList<Creature>();
        for (int i = 0; i < 4; i++) {
            creatures.add(new Blinker());
            creatures.add(new Orbiter());
            creatures.add(new Seeker());
        }
        this.boardRenderer = new BoardRenderer(adventurers, creatures);
        this.dice = new Dice();

    }

    public void startSimulation() {
        while (true) {

            // check alive status before calling performTurn
            adventurers.forEach(adventurer -> {
                if (adventurer.isAlive()) {
                    adventurer.performTurn(dice);
                }
            });
            //if (checkTermination()) break;
            creatures.forEach(creature -> {
                if (creature.isAlive()) {
                    creature.performTurn(dice);
                }
            });

            boardRenderer.printGameStatus();
            if (checkTermination()) break;
        }
    }

    public boolean checkTermination() {
        boolean isAdventurerAlive = false;
        boolean isCreatureAlive = false;
        int totalTreasure = 0;
        for (Adventurer adventurer : adventurers) {
            totalTreasure += adventurer.getNoOfTreasure();
            isAdventurerAlive = isAdventurerAlive || adventurer.isAlive();
        }
        for (Creature creature : creatures) {
            isCreatureAlive = isCreatureAlive || creature.isAlive();
        }

        if (totalTreasure >= 10) {
            System.out.println("Adventurers win by finding " + String.valueOf(totalTreasure) + " treasures");
            return true;
        } else if (isAdventurerAlive && !isCreatureAlive) {
            System.out.println("Adventurers win by eliminating all creatures");
            return true;
        } else if (isCreatureAlive && !isAdventurerAlive) {
            System.out.println("Creatures win by eliminating all adventurers");
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        GameEngine gm = new GameEngine();
        gm.initialize();
        gm.startSimulation();
//        for(int i=0;i<30;i++) {
//            System.out.print("Run " + String.valueOf(i+1) + ": ");
//            gm.initialize();
//            gm.startSimulation();
//        }

    }
}
