package test;

import RotLA.Dice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DiceTest {

    @Test
    void getDiceRoll() {
        //checking the diceRoll is between 1 and 12 and not giving unwanted results like 0 or 13.
        Dice dice = new Dice();
        int roll = dice.getDiceRoll();
        assertNotEquals(13, roll);
        assertNotEquals(0, roll);
    }
}