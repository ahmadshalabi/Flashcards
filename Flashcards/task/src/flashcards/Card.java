package flashcards;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.StringJoiner;

public class Card implements Comparable<Card> {
    private final String term;
    private final String definition;
    private int mistakes;

    public Card(String term, String definition) {
        this.term = term;
        this.definition = definition;
    }

    public Card(String definition) {
        term = "";
        this.definition = definition;
    }

    public Card(String term, String definition, int mistakes) {
        this.term = term;
        this.definition = definition;
        this.mistakes = mistakes;
    }

    public String getTerm() {
        return term;
    }

    public String getDefinition() {
        return definition;
    }

    public int getMistakes() {
        return mistakes;
    }

    public void updateMistakes() {
        mistakes++;
    }

    public void resetMistakes() {
        mistakes = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return definition.equals(card.definition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(definition);
    }

    /**
     * "Note: This class has a natural ordering that is inconsistent with equals."
     */
    @Override
    public int compareTo(@NotNull Card o) {
        return this.mistakes - o.mistakes;
    }

    @Override
    public String toString() {
        return new StringJoiner("\n")
                .add(term)
                .add(definition)
                .add(String.valueOf(mistakes))
                .add("") // to handle the last newLine
                .toString();
    }
}