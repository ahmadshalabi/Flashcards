package flashcards;

import java.util.Scanner;

public class ReaderFactory {
    private static ReaderFactory instance;
    private final Scanner reader;
    private final IOBroadcaster broadcaster;

    private ReaderFactory() {
        broadcaster = IOBroadcaster.getInstance();
        reader = new Scanner(System.in);
    }

    public static ReaderFactory getInstance() {
        if (instance == null) {
            synchronized (ReaderFactory.class) {
                if (instance == null) {
                    instance = new ReaderFactory();
                }
            }
        }
        return instance;
    }

    public String nextLine() {
        String line = reader.nextLine();
        broadcaster.broadcast(line);
        broadcaster.broadcast(System.lineSeparator());
        return line;
    }

    public int nextInt() {
        int num = reader.nextInt();
        broadcaster.broadcast(num);
        reader.nextLine(); //clear \n
        broadcaster.broadcast(System.lineSeparator());
        return num;
    }
}