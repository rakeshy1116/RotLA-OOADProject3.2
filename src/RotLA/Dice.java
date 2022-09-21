package RotLA;

public class Dice {
    public int getDiceRoll() {
        int die1Roll = GameUtility.getRandomInRange(1, 6);
        int die2Roll = GameUtility.getRandomInRange(1, 6);
        return die2Roll + die1Roll;
    }


}
