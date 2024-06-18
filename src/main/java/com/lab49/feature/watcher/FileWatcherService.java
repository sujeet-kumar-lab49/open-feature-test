package com.lab49.feature.watcher;

import com.lab49.feature.services.DynamicFeature;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;

import static java.nio.file.StandardWatchEventKinds.*;

@Service
public class FileWatcherService {

    private static final String DIRECTORY_TO_WATCH = "/app";
    private volatile boolean keepRunning = true;
    private Thread fileWatcherThread;

    @Autowired
    private DynamicFeature dynamicFeature;

    @PostConstruct
    public void init() {
        // Start the file watcher in a new thread
        fileWatcherThread = new Thread(this::watchDirectory);
        fileWatcherThread.start();
    }

    @PreDestroy
    public void shutdown() {
        // Stop the file watcher thread gracefully
        keepRunning = false;
        if (fileWatcherThread != null) {
            fileWatcherThread.interrupt();
        }
    }

    private void watchDirectory() {
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            Path path = Paths.get(DIRECTORY_TO_WATCH);
            path.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

            System.out.println("Monitoring directory for changes...");

            while (keepRunning) {
                WatchKey key;
                try {
                    key = watchService.poll(1, TimeUnit.SECONDS); // Use poll with timeout to check the flag regularly
                    if (key == null) {
                        continue;
                    }
                } catch (InterruptedException ex) {
                    return;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path fileName = ev.context();

                    if(!fileName.toString().endsWith("~")) {
                        logEntry(kind, fileName);
                        dynamicFeature.readFile();
                    }

                }

                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void logEntry(WatchEvent.Kind<?> kind, Path fileName) {
        if (kind == ENTRY_CREATE) {
            System.out.println("File created: " + fileName);
        } else if (kind == ENTRY_DELETE) {
            System.out.println("File deleted: " + fileName);
        } else if (kind == ENTRY_MODIFY) {
            System.out.println("File modified: " + fileName);
        }
    }

}
