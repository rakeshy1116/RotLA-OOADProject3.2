package RotLA.Events;

public interface Event {

    String getMsg();

    record GameObjectEnters(String objName, String roomName) implements Event {
        @Override
        public String getMsg() {
            return objName + " enters room " + roomName;
        }
    }

    record GameObjectCombat(String objectName, String result) implements Event {
        @Override
        public String getMsg() {
            return objectName + " " + result + " a combat";
        }
    }

    record AdventurerCelebrates(String advName, String msg) implements Event {
        @Override
        public String getMsg() {
            String[] celebrations = msg.split(":");
            String msg = advName + " celebrates : ";
            if (celebrations.length > 1) {
                msg = msg + celebrations[1];
            } else {
                msg = msg + "No celebration";
            }
            return msg;
        }
    }

    record AdventurerDamage(String advName, String damage) implements Event {
        @Override
        public String getMsg() {
            return advName + " has suffered " + damage + " Damage(s)";
        }
    }

    record GameObjectDefeated(String objectName) implements Event {
        @Override
        public String getMsg() {
            return objectName + " removed from game";
        }
    }

    record AdventurerFindsTreasure(String advName, String treasureName) implements Event {
        @Override
        public String getMsg() {
            return advName + " finds " + treasureName;
        }
    }
}
