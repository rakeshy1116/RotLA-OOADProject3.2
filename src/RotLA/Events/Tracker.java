package RotLA.Events;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Flow;

// CONCEPT: Observer pattern
// Tracker is a subsriber from Java Flow. It subscribes to the SubmissionPublisher in Game Engine
// SubmissionPublisher notifies all subscribers through events of type Event
// Flow.Subscription is a broker linking Flow.Publisher and Flow.Subsriber
// Tracker maintains a map structure to keep track of game status and prints them after every turn
// Tracker is decoupled from game logic and does not query any game objects, it updates its map structure based on the type of events and their data
public class Tracker implements Flow.Subscriber<Event> {

    private int turnCount;
    private final HashMap<String, GameObjectStatus> statusMap;
    private Flow.Subscription subscription;

    public Tracker(List<String> adventurers, List<String> creatures) {
        statusMap = new HashMap<>();
        adventurers.forEach(adventurerName -> statusMap.put(adventurerName, new GameObjectStatus.AdventurerStatusObject(adventurerName)));
        creatures.forEach(creatureName -> statusMap.put(creatureName, new GameObjectStatus.CreatureStatusObject(creatureName)));
    }

    public void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(Event item) {
        // check event type, get data passed in those events and update map structure
        if (item instanceof Event.GameObjectEnters) {
            GameObjectStatus status = statusMap.get(((Event.GameObjectEnters) item).objName());
            status.roomName = ((Event.GameObjectEnters) item).roomName();
        } else if (item instanceof Event.AdventurerDamage) {
            GameObjectStatus.AdventurerStatusObject status = (GameObjectStatus.AdventurerStatusObject) statusMap.get(((Event.AdventurerDamage) item).advName());
            status.damage = ((Event.AdventurerDamage) item).damage();
        } else if (item instanceof Event.AdventurerFindsTreasure) {
            GameObjectStatus.AdventurerStatusObject status = (GameObjectStatus.AdventurerStatusObject) statusMap.get(((Event.AdventurerFindsTreasure) item).advName());
            status.treasures.add(((Event.AdventurerFindsTreasure) item).treasureName());
        } else if (item instanceof Event.GameObjectDefeated) {
            GameObjectStatus status = statusMap.get(((Event.GameObjectDefeated) item).objectName());
            status.isAlive = false;
        }
        subscription.request(Long.MAX_VALUE);
    }

    public void printStatus() {
        int[] gameObjectCounts = new int[2];
        Arrays.fill(gameObjectCounts, 0);
        StringBuilder adventurerStatus = new StringBuilder();
        StringBuilder creatureStatus = new StringBuilder();
        statusMap.forEach(
                (objectName, objectStatus) -> {
                    if (objectStatus instanceof GameObjectStatus.AdventurerStatusObject) {
                        adventurerStatus.append(objectStatus.getStatus()).append("\n");
                        if (objectStatus.isAlive) gameObjectCounts[0]++;
                    } else {
                        if (objectStatus.isAlive) {
                            gameObjectCounts[1]++;
                            creatureStatus.append(objectStatus.getStatus()).append("\n");
                        }
                    }
                }
        );
        System.out.println("Tracker: Turn " + turnCount);
        System.out.println("Active Adventurers: " + gameObjectCounts[0]);
        System.out.println("Adventurers\tRoom\tDamage\tTreasures");
        System.out.println(adventurerStatus);
        System.out.println("Active Creatures: " + gameObjectCounts[1]);
        System.out.println("Creatures\t\t\tRoom");
        System.out.println(creatureStatus);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Tracker Error: " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        subscription.cancel();
    }

    public void stopSubscription() {
        subscription.cancel();
    }

}
