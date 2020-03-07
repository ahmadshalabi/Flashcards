package flashcards.actions;

import java.util.List;
import java.util.stream.Collectors;

public class HardestCardAction extends AbstractAction {
    private static final String MSG = "The hardest %s %s. You have %d errors answering %s.%n";
    private static final String NO_CARD_ERRORS_MSG = "There are no cards with errors.";

    @Override
    public void execute() {
        List<String> hardestCards = cardStore.getHardestCards();
        switch (hardestCards.size()) {
            case 0:
                writer.println(NO_CARD_ERRORS_MSG);
                break;
            case 1:
                printHardestCards(hardestCards, "card is", "it");
                break;
            default:
                printHardestCards(hardestCards, "cards are", "them");
                break;

        }
    }

    private void printHardestCards(List<String> hardestCards, String s, String them) {
        writer.printf(MSG, s,
                hardestCards.stream()
                        .map(card -> String.format("\"%s\"", card))
                        .collect(Collectors.joining(", ")),
                cardStore.getGreatestMistakes(),
                them);
    }

    @Override
    public String name() {
        return "hardest card";
    }
}
