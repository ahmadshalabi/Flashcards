package flashcards.serialization;

import flashcards.Card;
import flashcards.CardStore;
import flashcards.FileIOUtil;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collector;

public class Deserializer {
    private static Deserializer instance;

    private Deserializer() {
    }

    public static Deserializer getInstance() {
        if (instance == null) {
            synchronized (Serializer.class) {
                if (instance == null) {
                    instance = new Deserializer();
                }
            }
        }
        return instance;
    }

    public Set<Card> read(String filename) {
        return (Set<Card>) FileIOUtil.read(filename, CardCollector.collector());
    }

    private static class CardCollector {
        private final CardStore cardStore;
        private final Set<Card> cards;
        private String definition;
        private String mistakes;

        private CardCollector() {
            cardStore = CardStore.getInstance();
            cards = new HashSet<>();
        }

        private static Collector<String, ?, Collection<?>> collector() {
            return Collector.of(CardCollector::new, CardCollector::accept, CardCollector::combine, CardCollector::finish);
        }

        private void accept(String str) {
            String term = definition;
            definition = mistakes;
            mistakes = str;

            if (term != null && definition != null && mistakes != null) {
                String existDefinition = cardStore.getDefinitionOf(term);
                if (!Objects.equals(definition, existDefinition)) {
                    cards.add(new Card(term, definition, Integer.parseInt(mistakes)));
                }
                definition = null;
                mistakes = null;
            }
        }

        private CardCollector combine(CardCollector other) {
            throw new UnsupportedOperationException("Parallel Stream not supported");
        }

        private Set<Card> finish() {
            return cards;
        }
    }
}