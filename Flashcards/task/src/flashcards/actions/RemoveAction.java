package flashcards.actions;

public class RemoveAction extends AbstractAction {

    private static final String PROMPT_MSG = "The card:";
    private static final String ERR_MSG = "Can't remove \"%s\": there is no such card.%n";
    private static final String SUCCESSFUL_MSG = "The card has been removed.";

    @Override
    public void execute() {
        String card = getCard();
        if (card == null) {
            return;
        }
        cardStore.remove(card);
        writer.println(SUCCESSFUL_MSG);
    }

    @Override
    public String name() {
        return "remove";
    }

    private String getCard() {
        writer.println(PROMPT_MSG);
        String card = reader.nextLine();
        if (cardStore.isTermNotExist(card)) {
            writer.printf(ERR_MSG, card);
            return null;
        }
        return card;
    }
}