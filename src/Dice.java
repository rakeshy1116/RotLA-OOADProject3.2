public class Dice {

    public int getRandoms() {
        int min = 2;
        int max = 12;
        return (int)Math.floor(Math.random()*(max-min+1)+min);
    }

}
