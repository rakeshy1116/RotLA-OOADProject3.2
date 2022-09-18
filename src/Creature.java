public class Creature {

    private Room room;
    private boolean alive;

    Creature(Room room,boolean alive) {
        this.alive=true;
        this.room=room;
    }
    public void move() {
        // TODO will add the method after room is added
    }

    public void performTurn(Dice dice) {
        move();
        fight(dice);
    }

    public int fight(Dice dice) {
        return dice.getRandoms();
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}


class Orbiter extends Creature {

    Orbiter(Room room,boolean alive) {
        super(room,alive);
    }

    public void move() {
       //TODO after room
    }

}

class Blinker extends Creature {

    Blinker(Room room,boolean alive) {
        super(room,alive);
    }

    public void move() {
        //TODO after room
    }

}

class Seeker extends Creature {

    Seeker(Room room,boolean alive) {
        super(room,alive);
    }

    public void move() {
        //TODO after room
    }

}

