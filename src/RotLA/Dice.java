package RotLA;

// A class that simulates rolls of 2 dice and returns the sum
//CONCEPT: COHESION - The class Dice performs only those behaviour that are very relevant to dice, like rolling them
//Hence class Dice is highly cohesive
public class Dice {
    public int getDiceRoll() {
        int die1Roll = GameUtility.getRandomInRange(1, 6);
        int die2Roll = GameUtility.getRandomInRange(1, 6);
        return die2Roll + die1Roll;
    }
}
