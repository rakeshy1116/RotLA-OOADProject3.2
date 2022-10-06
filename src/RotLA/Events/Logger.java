package RotLA.Events;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Flow;

// CONCEPT: Observer pattern
// Logger is a subsriber from Java Flow. It subscribes to the SubmissionPublisher in Game Engine
// SubmissionPublisher notifies all subscribers through events of type Event
// Flow.Subscription is a broker linking Flow.Publisher and Flow.Subsriber
// Throughout a turn logger prints all the events to logger files in logger-files/Logger-(TurnNo).txt
public class Logger implements Flow.Subscriber<Event> {

    private final String filePath;

    private String logs;
    private Flow.Subscription subscription;

    public Logger(int turn) {
        super();
        logs = "";
        filePath = "logger_files\\Logger-" + turn + ".txt";
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(Event item) {
        logs = logs + item.getMsg() + "\n";
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Logger Error: " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        subscription.cancel();
    }

    public void stopSubscription() {
        subscription.cancel();
        writeToFile();
    }

    private void writeToFile() {
        Path path = Paths.get(filePath);
        //create file
        try {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
        } catch (IOException e) {
            System.out.println("Logger: File io error");
        }
        //write to file
        try {
            FileWriter writer = new FileWriter(filePath, false);
            writer.write(logs);
            writer.close();
        } catch (IOException e) {
            System.out.println("Logger: File io exception");
        }
    }
}
