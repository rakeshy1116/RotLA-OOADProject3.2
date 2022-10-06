package RotLA.Events;

import java.util.ArrayList;
import java.util.List;

public abstract class GameObjectStatus {

    String gameObjectName;
    String roomName;

    Boolean isAlive;

    abstract String getStatus();

    static class AdventurerStatusObject extends GameObjectStatus {
        String damage;
        List<String> treasures;

        AdventurerStatusObject(String objName) {
            damage = "0";
            isAlive = true;
            gameObjectName = objName;
            treasures = new ArrayList<String>();
            roomName = "Not moved";
        }

        @Override
        String getStatus() {
            if (roomName == null) roomName = "Not moved";
            return gameObjectName + "\t\t" + roomName + "\t\t" + damage + "\t\t" + String.join(",", treasures);
        }
    }

    static class CreatureStatusObject extends GameObjectStatus {

        public CreatureStatusObject(String objName) {
            gameObjectName = objName;
            isAlive = true;
            roomName = "Not moved";
        }

        @Override
        String getStatus() {
            if (roomName == null) roomName = "Not moved";
            return gameObjectName + "\t\t" + roomName;
        }
    }

}
