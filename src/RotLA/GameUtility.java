package RotLA;

import java.util.Arrays;
import java.util.List;

//A utility class that holds static constants and methods that can be reused in code
public class GameUtility {

    public static final int WESTMOST_ROOM = 0;
    public static final int EASTMOST_ROOM = 2;
    public static final int NORTHMOST_ROOM = 0;
    public static final int SOUTHMOST_ROOM = 2;
    public static final int TOPMOST_ROOM = 1;
    public static final int BOTTOM_MOST_ROOM = 4;
    public static final int TREASURES_MIN_ROLL = 10;
    public static final int TREASURES_WINNING_NUMBER = 20;

    public static List<String> getAllCelebrations() {
        return Arrays.asList("Dance", "Jump", "Shout", "Spin");
    }

    public static int getRandomInRange(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }
}
