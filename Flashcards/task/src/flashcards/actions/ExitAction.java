package flashcards.actions;

public class ExitAction extends AbstractAction {

    private static final String MSG = "Bye bye!";
    private static final String SUCCESSFUL_MSG = "%d cards have been saved.%n";

    @Override
    public void execute() {
        writer.println(MSG);
        String filename = actionModifier.getExportToFile();
        if (filename != null) {
            serializer.write(filename);
            writer.printf(SUCCESSFUL_MSG, cardStore.size());
        }
        System.exit(0);
    }

    @Override
    public String name() {
        return "exit";
    }
}