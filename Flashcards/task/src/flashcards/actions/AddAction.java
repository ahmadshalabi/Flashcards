package flashcards.actions;

import flashcards.Card;

public class AddAction extends AbstractAction {

    private static final String CARD_PROMPT_MSG = "The card:";
    private static final String CARD_ERR_MSG = "The card \"%s\" already exists.%n";
    private static final String DEFINITION_PROMPT_MSG = "The definition of the card:";
    private static final String DEFINITION_ERR_MSG = "The definition \"%s\" already exists.%n";
    private static final String SUCCESSFUL_MSG = "The pair (\"%s\":\"%s\") has been added.%n";

    @Override
    public void execute() {
        String term = getTerm();
        if (term == null) {
            return;
        }

        String definition = getDefinition();
        if (definition == null) {
            return;
        }
        Card card = new Card(term, definition);
        cardStore.add(term, card);
        writer.printf(SUCCESSFUL_MSG, term, definition);
    }

    @Override
    public String name() {
        return "add";
    }

    private String getTerm() {
        writer.println(CARD_PROMPT_MSG);
        String card = reader.nextLine();
        if (cardStore.isTermNotExist(card)) {
            return card;
        }
        writer.printf(CARD_ERR_MSG, card);
        return null;
    }

    private String getDefinition() {
        writer.println(DEFINITION_PROMPT_MSG);
        String definition = reader.nextLine();
        Card fakeCard = new Card(definition);
        if (cardStore.isDefinitionNotExist(fakeCard)) {
            return definition;
        }
        writer.printf(DEFINITION_ERR_MSG, definition);
        return null;
    }
}