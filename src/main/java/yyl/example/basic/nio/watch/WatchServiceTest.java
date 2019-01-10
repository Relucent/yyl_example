package yyl.example.basic.nio.watch;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class WatchServiceTest {
    public static void main(String[] args) throws IOException, InterruptedException {

        String directory = "D:/temp-" + System.currentTimeMillis();
        File folder = new File(directory);
        folder.mkdirs();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    File file = new File(directory + "/" + i);
                    file.createNewFile();
                    file.deleteOnExit();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                folder.delete();
            }
        }).start();

        Thread watch = new Thread(() -> {
            try {
                WatchService watcher = FileSystems.getDefault().newWatchService();
                FileSystems.getDefault().getPath(directory).register(watcher, StandardWatchEventKinds.ENTRY_DELETE);
                while (true) {
                    WatchKey key = watcher.take();
                    for (WatchEvent<?> event : key.pollEvents()) {
                        Object name = event.context();
                        System.out.println(name);
                    }
                    key.reset();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        watch.setDaemon(true);
        watch.start();
    }
}
