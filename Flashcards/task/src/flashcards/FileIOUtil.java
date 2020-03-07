package flashcards;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collector;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

public class FileIOUtil {

    public static void write(String filename, Collection<String> collection) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), CREATE, WRITE)) {
            collection.forEach(obj -> {
                try {
                    writer.write(obj);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Collection<?> read(String filename, Collector<String, ?, Collection<?>> collector) {
        try (BufferedReader fileReader = Files.newBufferedReader(Paths.get(filename))) {
            return fileReader.lines()
                    .collect(collector);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}