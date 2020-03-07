package flashcards.actions;

import flashcards.serialization.Serializer;

public class ExportAction extends AbstractAction {

    private static final String PROMPT_MSG = "File name:";
    private static final String SUCCESSFUL_MSG = "%d cards have been saved.%n";

    private final Serializer serializer;

    public ExportAction() {
        super();
        serializer = Serializer.getInstance();
    }

    @Override
    public void execute() {
        String filename = getFilename();
        serializer.write(filename);
        postSerialize();
    }

    @Override
    public String name() {
        return "export";
    }

    private String getFilename() {
        writer.println(PROMPT_MSG);
        return reader.nextLine();
    }

    private void postSerialize() {
        writer.printf(SUCCESSFUL_MSG, cardStore.size());
        cardStore.reset();
    }
}