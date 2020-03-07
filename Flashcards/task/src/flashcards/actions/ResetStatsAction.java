package flashcards.actions;

public class ResetStatsAction extends AbstractAction {
    private static final String MSG = "Card statistics has been reset.";

    @Override
    public void execute() {
        cardStore.resetStatistics();
        writer.println(MSG);
    }

    @Override
    public String name() {
        return "reset stats";
    }
}