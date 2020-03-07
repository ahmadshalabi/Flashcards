package flashcards;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CardStore {

    private static CardStore instance;
    private Map<String, Card> cards;

    private CardStore() {
        cards = new LinkedHashMap<>();
    }

    public static CardStore getInstance() {
        if (instance == null) {
            synchronized (CardStore.class) {
                if (instance == null) {
                    instance = new CardStore();
                }
            }
        }
        return instance;
    }

    public void add(String term, Card card) {
        cards.put(term, card);
        sortByMistakes();
    }

    public void add(Set<Card> newCards) {
        cards.putAll((Map<? extends String, ? extends Card>) newCards.stream()
                .map(card -> Map.entry(card.getTerm(), card))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                )));
        sortByMistakes();
    }

    public Collection<String> getCards() {
        return cards.values()
                .stream()
                .map(Card::toString)
                .collect(Collectors.toList());
    }

    public String getDefinitionOf(String term) {
        if (isTermNotExist(term)) {
            return null;
        }
        return cards.get(term)
                .getDefinition();
    }

    public String getTermOfDefinition(String definition) {
        return cards.values()
                .stream()
                .filter(card -> Objects.equals(card.getDefinition(), definition))
                .findFirst().orElseThrow()
                .getTerm();
    }

    public Card getRandomCard() {
        return cards.values()
                .stream()
                .findAny().orElseThrow();
    }

    public List<String> getHardestCards() {
        int greatestMistakes = getGreatestMistakes();
        return cards.values()
                .stream()
                .filter(card -> card.getMistakes() == greatestMistakes)
                .map(Card::getTerm)
                .collect(Collectors.toList());
    }

    public int getGreatestMistakes() {
        return cards.values()
                .stream()
                .map(Card::getMistakes)
                .findFirst().orElse(-1);
    }

    public void remove(String card) {
        cards.remove(card);
    }

    public void reset() {
        cards.clear();
    }

    public void resetStatistics() {
        forEachCard(Card::resetMistakes);
    }

    public boolean isTermNotExist(String term) {
        return !cards.containsKey(term);
    }

    public boolean isDefinitionNotExist(Card card) {
        return !cards.containsValue(card);
    }

    public int size() {
        return cards.size();
    }

    private void forEachCard(Consumer<? super Card> action) {
        cards.values()
                .forEach(action);
    }

    private void sortByMistakes() {
        cards = cards.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
    }
}