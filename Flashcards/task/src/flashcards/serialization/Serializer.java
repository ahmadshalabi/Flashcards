package flashcards.serialization;

import flashcards.CardStore;
import flashcards.FileIOUtil;

public class Serializer {
    private static Serializer instance;
    private final CardStore cardStore;

    private Serializer() {
        cardStore = CardStore.getInstance();
    }

    public static Serializer getInstance() {
        if (instance == null) {
            synchronized (Serializer.class) {
                if (instance == null) {
                    instance = new Serializer();
                }
            }
        }
        return instance;
    }

    public void write(String filename) {
        FileIOUtil.write(filename, cardStore.getCards());
    }
}