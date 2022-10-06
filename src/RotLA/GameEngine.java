package RotLA;

import RotLA.Adventurers.*;
import RotLA.CombatStrategy.Expert;
import RotLA.CombatStrategy.Stealth;
import RotLA.CombatStrategy.Trained;
import RotLA.CombatStrategy.Untrained;
import RotLA.Creatures.Blinker;
import RotLA.Creatures.Creature;
import RotLA.Creatures.Orbiter;
import RotLA.Creatures.Seeker;
import RotLA.Events.Event;
import RotLA.Events.Logger;
import RotLA.Events.Tracker;
import RotLA.SearchStrategy.Careful;
import RotLA.SearchStrategy.Careless;
import RotLA.SearchStrategy.Quick;
import RotLA.Treasures.*;
import org.javatuples.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.SubmissionPublisher;

import static RotLA.GameUtility.TREASURES_WINNING_NUMBER;

// The main Game simulation performer class, initializes all components, performs turns and checks for termination
public class GameEngine {

    private BoardRenderer boardRenderer;
    private ArrayList<Adventurer> adventurers;
    private ArrayList<Creature> creatures;
    private ArrayList<Treasures> treasures;

    private SubmissionPublisher<Event> publisher;
    private Tracker tracker;

    private Dice dice;

    // Main function to start game simulation
    public static void main(String[] args) {
        GameEngine gm = new GameEngine();
//        int advWin=0;
//        int creWin=0;
//        int advWinTreasure=0;
        gm.initialize();
        gm.startSimulation();
//        Uncomment for multiple game run
//        for(int i=0;i<30;i++) {
//            System.out.print("Run " + String.valueOf(i+1) + ": ");
//            gm.initialize();
//            String result=gm.startSimulation();
//            if(result.contains("10")) {
//                advWinTreasure+=1;
//            }
//            else if(result.substring(0,3).equals("Adv"))
//            {
//                advWin+=1;
//            }
//            else {
//                creWin+=1;
//            }
//        }
//
//        System.out.println("Summary of Runs:");
//        System.out.println("Adventurers win by finding 10 treasures: " + advWinTreasure);
//        System.out.println("Adventurers win by eliminating all creatures: " + advWin);
//        System.out.println("Creatures win by eliminating all adventurers: " + creWin);

    }

    //Creates instances of all players, creatures, Board, Dice and initializes them
    public void initialize() {
        // remove logger files form previous runs
        resetLoggerFiles();
        // initialize publisher
        publisher = new SubmissionPublisher<Event>();
        //initialize adventurer
        this.adventurers = new ArrayList<Adventurer>();
        adventurers.add(new Brawler(new Expert(), new Careless(), publisher));
        adventurers.add(new Runner(new Untrained(), new Quick(), publisher));
        adventurers.add(new Thief(new Trained(), new Careful(), publisher));
        adventurers.add(new Sneaker(new Stealth(), new Quick(), publisher));
        // initialize creatures
        this.creatures = new ArrayList<Creature>();
        for (int i = 0; i < 4; i++) {
            creatures.add(new Blinker(publisher));
            creatures.add(new Orbiter(publisher));
            creatures.add(new Seeker(publisher));
        }
        //initialize Tracker and subscribe to publisher
        ArrayList<String> adventurerNames = new ArrayList<>();
        adventurers.forEach(adventurer -> adventurerNames.add(adventurer.getAdventurerName()));
        ArrayList<String> creatureNames = new ArrayList<>();
        creatures.forEach(creature -> creatureNames.add(creature.getInstanceName()));
        tracker = new Tracker(adventurerNames, creatureNames);
        publisher.subscribe(tracker);
        // initialize treasures
        this.treasures = new ArrayList<Treasures>();
        for (int i = 0; i < 4; i++) {
            treasures.add(new Gem());
            treasures.add(new Sword());
            treasures.add(new Trap());
            treasures.add(new Potion());
            treasures.add(new Armor());
            treasures.add(new Portal());
        }
        //initialize board
        this.boardRenderer = new BoardRenderer();
        boardRenderer.initialiseBoardForGame(adventurers, creatures, treasures);
        this.dice = new Dice();
    }

    // Starts turn simulation, simulation will end if termination conditions are met
    public void startSimulation() {
        int turn = 0;
        while (true) {
            ++turn;
            // set logger for the turn and subscribe
            Logger logger = new Logger(turn);
            publisher.subscribe(logger);
            tracker.setTurnCount(turn);
            // perform turn for all adventurers who are alive
            adventurers.forEach(adventurer -> {
                if (adventurer.isAlive()) {
                    adventurer.performTurn(dice);
                }
            });
            //perform turn for all creatures who are alive;
            creatures.forEach(creature -> {
                if (creature.isAlive()) {
                    creature.performTurn(dice);
                }
            });
            // print status at the end of every turn
            boardRenderer.printGameStatus();
            tracker.printStatus();
            logger.stopSubscription();
            //check if termination conditions are met and stop
            Pair<Boolean,String> result = checkTermination();
            if (result.getValue0()) {
                return result.getValue1();
            }
        }
        tracker.stopSubscription();
    }

    // performs checks of game termination consitions are met
    public Pair<Boolean,String> checkTermination() {
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

        //Game ends if Adventures found 10 treasures or Adventures killed all creatures or Creatures killed all adventurers
        if (totalTreasure >= TREASURES_WINNING_NUMBER) {
            System.out.println("Adventurers win by finding " + totalTreasure + " treasures");
            return new Pair(true,"Adventurers win by finding " + totalTreasure + " treasures");
        } else if (isAdventurerAlive && !isCreatureAlive) {
            System.out.println("Adventurers win by eliminating all creatures");
            return new Pair(true,"Adventurers win by eliminating all creatures");
        } else if (isCreatureAlive && !isAdventurerAlive) {
            System.out.println("Creatures win by eliminating all adventurers");
            return new Pair(true,"Creatures win by eliminating all adventurers");
        } else {
            return new Pair(false,"");
        }
    }

    private void resetLoggerFiles() {
        File files = new File("logger_files\\");
        for (File file : Objects.requireNonNull(files.listFiles())) {
            file.delete();
        }
    }
}
