
abstract public class Adventurer {
    private int damage;
    private int noOfDamages;
    private Room room;

    public void performTurn(Dice dice) {
            move();
            fight(dice);
            findTreasure(dice);
        //TODO creature check to be added after room class created
    }

    public Room getRoom() {
        return room;
    }

    public boolean isAlive() {
        return noOfDamages<3;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    Adventurer(Room room, int damage, int noOfDamages){
        this.damage=damage;
        this.room=room;
        this.noOfDamages=noOfDamages;
    }

    public void move() {
       // TODO will add the method after room is added
    }

    abstract public int fight(Dice dice);
    public int findTreasure(Dice dice) {
        return dice.getRandoms();
    }
}


class Brawler extends Adventurer {

    Brawler(Room room, int damage, int noOfDamages) {
        super(room,damage,noOfDamages);
    }

    public int fight(Dice dice) {
        return 2 + dice.getRandoms();
    }

}

class Runner extends Adventurer {

    Runner(Room room, int damage, int noOfDamages) {
        super(room,damage,noOfDamages);
    }

    public int fight(Dice dice) {
        return 2 + dice.getRandoms();
    }

}

class Sneaker extends Adventurer {

    Sneaker(Room room, int damage, int noOfDamages) {
        super(room,damage,noOfDamages);
    }

    public int fight(Dice dice) {
        int min = 0;
        int max = 1;
        int chance= (int)Math.floor(Math.random()*(max-min+1)+min);
        return chance * dice.getRandoms();
    }

}

class Thieves extends Adventurer {

    Thieves(Room room, int damage, int noOfDamages) {
        super(room,damage,noOfDamages);
    }

    public int fight(Dice dice) {
        return 1 + dice.getRandoms();
    }

    public int findingTreasure(Dice dice) {
        return 1 + dice.getRandoms();
    }

}
