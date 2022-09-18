
public class Adventurer {
    private int damage;
    private int noOfDamages;
    private Room room;


    Adventurer(Room room, int damage, int noOfDamages){
        this.damage=damage;
        this.room=room;
        this.noOfDamages=noOfDamages;
    }

    public void move() {

    }
}


class Brawler extends Adventurer {

    Brawler(Room room, int damage, int noOfDamages) {
        super(room,damage,noOfDamages);
    }

    public void move() {

    }

}
