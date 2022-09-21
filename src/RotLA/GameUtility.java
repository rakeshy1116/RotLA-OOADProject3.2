package RotLA;

public class GameUtility {
    public static int getRandomInRange(int min, int max) {

        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }
}
